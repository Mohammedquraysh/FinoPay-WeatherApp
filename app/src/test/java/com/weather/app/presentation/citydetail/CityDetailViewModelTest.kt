package com.weather.app.presentation.citydetail

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.weather.app.domain.model.Resource
import com.weather.app.domain.model.Weather
import com.weather.app.domain.usecase.GetCityWeatherUseCase
import com.weather.app.domain.usecase.ToggleFavoriteUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import androidx.lifecycle.SavedStateHandle
import com.weather.app.presentation.navigation.NavArgs
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CityDetailViewModelTest {

    private val getCityWeatherUseCase: GetCityWeatherUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CityDetailViewModel

    private val fakeWeather = Weather(
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
        visibilityMeters = 9000,
        pressureHpa = 1011,
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

    private fun buildViewModel(cityId: Long = 2643743L): CityDetailViewModel {
        val savedStateHandle = SavedStateHandle(mapOf(NavArgs.CITY_ID to cityId))
        return CityDetailViewModel(savedStateHandle, getCityWeatherUseCase, toggleFavoriteUseCase)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState emits loading initially`() = runTest {
        every { getCityWeatherUseCase(any()) } returns flowOf(Resource.Loading)
        viewModel = buildViewModel()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isTrue()
            assertThat(state.weather).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState emits weather on success`() = runTest {
        every { getCityWeatherUseCase(2643743L) } returns flowOf(Resource.Success(fakeWeather))
        viewModel = buildViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.weather).isNotNull()
            assertThat(state.weather!!.cityName).isEqualTo("London")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState emits error on failure`() = runTest {
        every { getCityWeatherUseCase(any()) } returns flowOf(Resource.Error("Not found"))
        viewModel = buildViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.error).isEqualTo("Not found")
            assertThat(state.weather).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onToggleFavorite calls use case with toggled value`() = runTest {
        every { getCityWeatherUseCase(any()) } returns flowOf(Resource.Success(fakeWeather))
        viewModel = buildViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        /**  Wait for weather to be available in state **/
        viewModel.uiState.test {
            awaitItem() // consume loading or success
            cancelAndIgnoreRemainingEvents()
        }

        viewModel.onToggleFavorite()
        testDispatcher.scheduler.advanceUntilIdle()

        /** fakeWeather.isFavorite = false, so toggling should call with true **/
        coVerify { toggleFavoriteUseCase(2643743L, true) }
    }

    @Test
    fun `onToggleFavorite is a no-op when weather is null`() = runTest {
        every { getCityWeatherUseCase(any()) } returns flowOf(Resource.Loading)
        viewModel = buildViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onToggleFavorite() // weather is null, should not crash
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 0) { toggleFavoriteUseCase(any(), any()) }
    }

    @Test
    fun `cityId is correctly read from SavedStateHandle`() = runTest {
        every { getCityWeatherUseCase(1850147L) } returns flowOf(Resource.Success(fakeWeather.copy(cityId = 1850147L, cityName = "Tokyo")))
        viewModel = buildViewModel(cityId = 1850147L)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.weather?.cityName).isEqualTo("Tokyo")
            cancelAndIgnoreRemainingEvents()
        }
    }
}
