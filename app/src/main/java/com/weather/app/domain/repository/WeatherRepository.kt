package com.weather.app.domain.repository

import com.weather.app.domain.model.Resource
import com.weather.app.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    /**
     * Emits the full city list (favorites first), serving cached data immediately
     * while refreshing from the network in the background.
     */
    fun getCitiesWeather(): Flow<Resource<List<Weather>>>

    /**
     * Emits a single city's weather by [cityId], from cache then network.
     */
    fun getCityWeather(cityId: Long): Flow<Resource<Weather>>

    /**
     * Toggles the favourite status of a city locally.
     */
    suspend fun toggleFavorite(cityId: Long, isFavorite: Boolean)

    /**
     * Returns only cities that are marked as favourite.
     */
    fun getFavoriteCities(): Flow<List<Weather>>

    /**
     * Force-refreshes all city weather data from the network.
     */
    suspend fun refreshAllCities(): Result<Unit>
}
