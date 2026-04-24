package com.weather.app.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.weather.app.core.network.NetworkMonitor
import com.weather.app.data.local.dao.WeatherDao
import com.weather.app.data.local.entity.WeatherEntity
import com.weather.app.data.remote.api.WeatherApiService
import com.weather.app.data.remote.dto.MainDto
import com.weather.app.data.remote.dto.SysDto
import com.weather.app.data.remote.dto.WeatherConditionDto
import com.weather.app.data.remote.dto.WeatherResponseDto
import com.weather.app.data.remote.dto.WindDto
import com.weather.app.domain.model.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private val api: WeatherApiService = mockk()
    private val dao: WeatherDao = mockk(relaxed = true)
    private val networkMonitor: NetworkMonitor = mockk()

    private lateinit var repository: WeatherRepositoryImpl

    private val fakeEntity = WeatherEntity(
        cityId = 2643743L,
        cityName = "London",
        countryCode = "GB",
        temperatureCelsius = 15.0,
        feelsLikeCelsius = 13.0,
        tempMinCelsius = 10.0,
        tempMaxCelsius = 18.0,
        humidity = 80,
        windSpeedMs = 5.0,
        windDegrees = 270,
        visibilityMeters = 10000,
        pressureHpa = 1013,
        conditionId = 800,
        conditionMain = "Clear",
        conditionDescription = "clear sky",
        conditionIcon = "01d",
        sunriseEpoch = 1700000000L,
        sunsetEpoch = 1700040000L,
        timezoneOffsetSeconds = 0,
        lastUpdatedEpoch = System.currentTimeMillis(),
        isFavorite = false,
    )

    private val fakeDto = WeatherResponseDto(
        id = 2643743L,
        name = "London",
        sys = SysDto(country = "GB", sunrise = 1700000000L, sunset = 1700040000L),
        main = MainDto(
            temp = 15.0, feelsLike = 13.0, tempMin = 10.0,
            tempMax = 18.0, humidity = 80, pressure = 1013,
        ),
        weather = listOf(WeatherConditionDto(800, "Clear", "clear sky", "01d")),
        wind = WindDto(speed = 5.0, deg = 270),
        visibility = 10000,
        dt = System.currentTimeMillis() / 1000,
        timezone = 0,
    )

    @Before
    fun setUp() {
        every { networkMonitor.isOnline } returns flowOf(true)
        every { networkMonitor.isCurrentlyOnline() } returns true
        every { dao.observeAllCities() } returns flowOf(listOf(fakeEntity))
        every { dao.observeFavoriteCities() } returns flowOf(emptyList())
        coEvery { dao.getAllCityIds() } returns listOf(2643743L)
        /** All 15 city IDs return a valid DTO (relaxed mock) **/
        coEvery { api.getCurrentWeather(any(), any(), any()) } returns fakeDto
        repository = WeatherRepositoryImpl(api, dao, networkMonitor)
    }

    @Test
    fun `getCitiesWeather emits Success with mapped domain models`() = runTest {
        repository.getCitiesWeather().test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(Resource.Success::class.java)
            val cities = (item as Resource.Success).data
            assertThat(cities).hasSize(1)
            assertThat(cities.first().cityName).isEqualTo("London")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCitiesWeather fires parallel individual API calls when online`() = runTest {
        repository.getCitiesWeather().test {
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }
        /** Should call getCurrentWeather once per city in DefaultCities (15 cities) **/
        coVerify(exactly = DefaultCities.CITY_IDS.size) {
            api.getCurrentWeather(any(), any(), any())
        }
    }

    @Test
    fun `getCitiesWeather does NOT call API when offline`() = runTest {
        every { networkMonitor.isCurrentlyOnline() } returns false

        repository.getCitiesWeather().test {
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 0) { api.getCurrentWeather(any(), any(), any()) }
    }

    @Test
    fun `refreshAllCities succeeds even when some individual city calls fail`() = runTest {
        /** 14 succeed, 1 throws — overall result should still be success **/
        var callCount = 0
        coEvery { api.getCurrentWeather(any(), any(), any()) } answers {
            callCount++
            if (callCount == 3) throw RuntimeException("Transient error on city 3")
            fakeDto
        }
        val result = repository.refreshAllCities()
        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `toggleFavorite calls dao updateFavoriteStatus`() = runTest {
        repository.toggleFavorite(cityId = 2643743L, isFavorite = true)
        coVerify { dao.updateFavoriteStatus(2643743L, true) }
    }

    @Test
    fun `getFavoriteCities emits empty list when no favorites`() = runTest {
        repository.getFavoriteCities().test {
            assertThat(awaitItem()).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getFavoriteCities emits mapped favorites`() = runTest {
        val favoriteEntity = fakeEntity.copy(isFavorite = true)
        every { dao.observeFavoriteCities() } returns flowOf(listOf(favoriteEntity))

        repository.getFavoriteCities().test {
            val favorites = awaitItem()
            assertThat(favorites).hasSize(1)
            assertThat(favorites.first().isFavorite).isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCityWeather emits loading then success from cache`() = runTest {
        every { dao.observeCity(2643743L) } returns flowOf(fakeEntity)

        repository.getCityWeather(2643743L).test {
            val first = awaitItem()
            /** onStart emits Loading before the flow from dao kicks in **/
            assertThat(first).isInstanceOf(Resource.Loading::class.java)
            val second = awaitItem()
            assertThat(second).isInstanceOf(Resource.Success::class.java)
            assertThat((second as Resource.Success).data.cityName).isEqualTo("London")
            cancelAndIgnoreRemainingEvents()
        }
    }
}
