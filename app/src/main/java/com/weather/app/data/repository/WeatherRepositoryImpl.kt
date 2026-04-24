package com.weather.app.data.repository

import com.weather.app.BuildConfig
import com.weather.app.core.network.NetworkMonitor
import com.weather.app.data.local.dao.WeatherDao
import com.weather.app.data.remote.api.WeatherApiService
import com.weather.app.domain.model.Resource
import com.weather.app.domain.model.Weather
import com.weather.app.domain.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService,
    private val dao: WeatherDao,
    private val networkMonitor: NetworkMonitor,
) : WeatherRepository {

    override fun getCitiesWeather(): Flow<Resource<List<Weather>>> =
        dao.observeAllCities()
            .map<_, Resource<List<Weather>>> { entities ->
                Resource.Success(entities.toDomain())
            }
            .onStart {
                /** to Show loading only when cache is cold (first install / cleared data) **/
                val cached = dao.getAllCityIds()
                if (cached.isEmpty()) emit(Resource.Loading)

                /**To always try a background refresh when online**/
                if (networkMonitor.isCurrentlyOnline()) {
                    refreshAllCities()
                }
            }
            .catch { throwable ->
                emit(Resource.Error(throwable.localizedMessage ?: "Unknown error", throwable))
            }

    override fun getCityWeather(cityId: Long): Flow<Resource<Weather>> =
        dao.observeCity(cityId)
            .map<_, Resource<Weather>> { entity ->
                if (entity != null) Resource.Success(entity.toDomain())
                else Resource.Error("City not found in cache")
            }
            .onStart {
                emit(Resource.Loading)
                if (networkMonitor.isCurrentlyOnline()) refreshCity(cityId)
            }
            .catch { throwable ->
                emit(Resource.Error(throwable.localizedMessage ?: "Unknown error", throwable))
            }

    override suspend fun toggleFavorite(cityId: Long, isFavorite: Boolean) {
        dao.updateFavoriteStatus(cityId, isFavorite)
    }

    override fun getFavoriteCities(): Flow<List<Weather>> =
        dao.observeFavoriteCities().map { it.toDomain() }

    /**
     * Fetches all 15 cities in parallel using individual/weather calls (free tier).
     * Uses coroutineScope plus async/awaitAll so all requests fire concurrently and we
     * wait for all of them before writing to Room, fastest possible refresh.
     * Individual failures are swallowed intentionally: if one city's call fails
     * (e.g. network blip), the rest still land in the cache.
     */
    override suspend fun refreshAllCities(): Result<Unit> = runCatching {
        coroutineScope {
            DefaultCities.CITY_IDS
                .map { cityId ->
                    async {
                        runCatching {
                            val dto = api.getCurrentWeather(
                                cityId = cityId,
                                apiKey = BuildConfig.WEATHER_API_KEY,
                            )
                            dao.upsertPreservingFavorite(dto.toEntity())
                        }
                        // Per-city failures are swallowed, stale cache is better than crash
                    }
                }
                .awaitAll()
        }
    }

    private suspend fun refreshCity(cityId: Long): Result<Unit> = runCatching {
        val dto = api.getCurrentWeather(
            cityId = cityId,
            apiKey = BuildConfig.WEATHER_API_KEY,
        )
        dao.upsertPreservingFavorite(dto.toEntity())
    }
}
