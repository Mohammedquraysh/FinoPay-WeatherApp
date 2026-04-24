package com.weather.app.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.weather.app.domain.model.Resource
import com.weather.app.domain.model.Weather
import com.weather.app.domain.repository.WeatherRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCitiesWeatherUseCaseTest {

    private val repository: WeatherRepository = mockk()
    private lateinit var useCase: GetCitiesWeatherUseCase

    private val fakeWeather = Weather(
        cityId = 1L, cityName = "London", countryCode = "GB",
        temperatureCelsius = 15.0, feelsLikeCelsius = 13.0,
        tempMinCelsius = 10.0, tempMaxCelsius = 18.0,
        humidity = 80, windSpeedMs = 5.0, windDegrees = 270,
        visibilityMeters = 10000, pressureHpa = 1013,
        conditionId = 800, conditionMain = "Clear",
        conditionDescription = "clear sky", conditionIcon = "01d",
        sunriseEpoch = 0L, sunsetEpoch = 0L,
        timezoneOffsetSeconds = 0, lastUpdatedEpoch = 0L,
    )

    @Before
    fun setUp() {
        useCase = GetCitiesWeatherUseCase(repository)
    }

    @Test
    fun `invoke delegates to repository getCitiesWeather`() = runTest {
        every { repository.getCitiesWeather() } returns
                flowOf(Resource.Success(listOf(fakeWeather)))

        useCase().test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(Resource.Success::class.java)
            assertThat((item as Resource.Success).data).containsExactly(fakeWeather)
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits Loading when repository emits Loading`() = runTest {
        every { repository.getCitiesWeather() } returns flowOf(Resource.Loading)

        useCase().test {
            assertThat(awaitItem()).isInstanceOf(Resource.Loading::class.java)
            awaitComplete()
        }
    }

    @Test
    fun `invoke emits Error when repository emits Error`() = runTest {
        every { repository.getCitiesWeather() } returns
                flowOf(Resource.Error("Server error"))

        useCase().test {
            val item = awaitItem()
            assertThat(item).isInstanceOf(Resource.Error::class.java)
            assertThat((item as Resource.Error).message).isEqualTo("Server error")
            awaitComplete()
        }
    }
}

class ToggleFavoriteUseCaseTest {

    private val repository: WeatherRepository = mockk(relaxed = true)
    private lateinit var useCase: ToggleFavoriteUseCase

    @Before
    fun setUp() {
        useCase = ToggleFavoriteUseCase(repository)
    }

    @Test
    fun `invoke calls repository toggleFavorite with correct arguments`() = runTest {
        useCase(cityId = 42L, isFavorite = true)
        coVerify { repository.toggleFavorite(42L, true) }
    }

    @Test
    fun `invoke with isFavorite=false calls repository with false`() = runTest {
        useCase(cityId = 7L, isFavorite = false)
        coVerify { repository.toggleFavorite(7L, false) }
    }
}

class GetFavoriteCitiesUseCaseTest {

    private val repository: WeatherRepository = mockk()
    private lateinit var useCase: GetFavoriteCitiesUseCase

    private val favCity = Weather(
        cityId = 5L, cityName = "Tokyo", countryCode = "JP",
        temperatureCelsius = 22.0, feelsLikeCelsius = 21.0,
        tempMinCelsius = 18.0, tempMaxCelsius = 26.0,
        humidity = 70, windSpeedMs = 2.0, windDegrees = 90,
        visibilityMeters = 10000, pressureHpa = 1015,
        conditionId = 801, conditionMain = "Clouds",
        conditionDescription = "few clouds", conditionIcon = "02d",
        sunriseEpoch = 0L, sunsetEpoch = 0L,
        timezoneOffsetSeconds = 32400, lastUpdatedEpoch = 0L,
        isFavorite = true,
    )

    @Before
    fun setUp() {
        useCase = GetFavoriteCitiesUseCase(repository)
    }

    @Test
    fun `invoke returns flow of favorite cities`() = runTest {
        every { repository.getFavoriteCities() } returns flowOf(listOf(favCity))

        useCase().test {
            val result = awaitItem()
            assertThat(result).hasSize(1)
            assertThat(result.first().cityName).isEqualTo("Tokyo")
            assertThat(result.first().isFavorite).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns empty list when no favorites`() = runTest {
        every { repository.getFavoriteCities() } returns flowOf(emptyList())

        useCase().test {
            assertThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }
}
