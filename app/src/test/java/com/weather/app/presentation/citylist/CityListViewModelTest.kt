package com.weather.app.presentation.citylist

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.weather.app.core.network.NetworkMonitor
import com.weather.app.domain.model.Resource
import com.weather.app.domain.model.Weather
import com.weather.app.domain.usecase.GetCitiesWeatherUseCase
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
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CityListViewModelTest {

    private val getCitiesWeatherUseCase: GetCitiesWeatherUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk(relaxed = true)
    private val networkMonitor: NetworkMonitor = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CityListViewModel

    private fun buildWeather(id: Long, name: String, isFavorite: Boolean = false) = Weather(
        cityId = id, cityName = name, countryCode = "GB",
        temperatureCelsius = 20.0, feelsLikeCelsius = 18.0,
        tempMinCelsius = 15.0, tempMaxCelsius = 25.0,
        humidity = 60, windSpeedMs = 3.0, windDegrees = 180,
        visibilityMeters = 10000, pressureHpa = 1013,
        conditionId = 800, conditionMain = "Clear",
        conditionDescription = "clear sky", conditionIcon = "01d",
        sunriseEpoch = 0L, sunsetEpoch = 0L,
        timezoneOffsetSeconds = 0, lastUpdatedEpoch = System.currentTimeMillis(),
        isFavorite = isFavorite,
    )

    private val cities = listOf(
        buildWeather(1L, "London"),
        buildWeather(2L, "Paris"),
        buildWeather(3L, "Tokyo", isFavorite = true),
        buildWeather(4L, "Amsterdam"),
        buildWeather(5L, "Nairobi"),
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { getCitiesWeatherUseCase() } returns flowOf(Resource.Success(cities))
        every { networkMonitor.isOnline } returns flowOf(true)
        viewModel = CityListViewModel(getCitiesWeatherUseCase, toggleFavoriteUseCase, networkMonitor)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /** Sorting **/

    @Test
    fun `filteredCities shows favourite city first when no search active`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.filteredCities.first().cityName).isEqualTo("Tokyo")
            assertThat(state.filteredCities.first().isFavorite).isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }

    /** 2-char minimum **/

    @Test
    fun `single character query does NOT filter cities`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onSearchQueryChange("L")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            // isSearchActive must be false,  list unchanged
            assertThat(state.isSearchActive).isFalse()
            assertThat(state.filteredCities).hasSize(cities.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `single character query shows searchHint`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onSearchQueryChange("L")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.searchHint).isNotNull()
            assertThat(state.searchHint).contains("1 more letter")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `two character query activates search and filters`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onSearchQueryChange("Lo")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isSearchActive).isTrue()
            assertThat(state.searchHint).isNull()
            assertThat(state.filteredCities.all {
                it.cityName.contains("Lo", ignoreCase = true)
            }).isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `search is case insensitive from two characters`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onSearchQueryChange("lo") // lowercase
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.filteredCities.map { it.cityName }).contains("London")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `empty query clears filter and shows all cities`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onSearchQueryChange("Lo")
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onClearSearch()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.searchQuery).isEmpty()
            assertThat(state.isSearchActive).isFalse()
            assertThat(state.filteredCities).hasSize(cities.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `no results when query matches no city names`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onSearchQueryChange("ZZ")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isSearchActive).isTrue()
            assertThat(state.filteredCities).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }

    /** filterAndSort unit tests (pure function, no coroutines needed) **/

    @Test
    fun `filterAndSort returns all cities when isSearchActive is false`() {
        val result = viewModel.filterAndSort(cities, "L", isSearchActive = false)
        assertThat(result).hasSize(cities.size)
    }

    @Test
    fun `filterAndSort applies filter when isSearchActive is true`() {
        val result = viewModel.filterAndSort(cities, "Am", isSearchActive = true)
        assertThat(result).hasSize(1)
        assertThat(result.first().cityName).isEqualTo("Amsterdam")
    }

    @Test
    fun `filterAndSort always puts favourites first`() {
        val result = viewModel.filterAndSort(cities, "", isSearchActive = false)
        assertThat(result.first().isFavorite).isTrue()
    }

    @Test
    fun `filterAndSort sorts non-favourites alphabetically`() {
        val result = viewModel.filterAndSort(cities, "", isSearchActive = false)
        val nonFavs = result.filter { !it.isFavorite }
        assertThat(nonFavs.map { it.cityName })
            .isInOrder(Comparator.naturalOrder<String>())
    }

    /** Toggle & network **/

    @Test
    fun `onToggleFavorite calls use case with correct params`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onToggleFavorite(cityId = 1L, isFavorite = true)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { toggleFavoriteUseCase(1L, true) }
    }

    @Test
    fun `isOnline false reflects in uiState`() = runTest {
        every { networkMonitor.isOnline } returns flowOf(false)
        viewModel = CityListViewModel(getCitiesWeatherUseCase, toggleFavoriteUseCase, networkMonitor)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            assertThat(awaitItem().isOnline).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `error state propagated when use case emits Error`() = runTest {
        every { getCitiesWeatherUseCase() } returns flowOf(Resource.Error("Network error"))
        viewModel = CityListViewModel(getCitiesWeatherUseCase, toggleFavoriteUseCase, networkMonitor)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.error).isEqualTo("Network error")
            cancelAndIgnoreRemainingEvents()
        }
    }
}
