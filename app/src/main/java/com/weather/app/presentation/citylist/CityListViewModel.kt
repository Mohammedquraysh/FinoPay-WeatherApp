package com.weather.app.presentation.citylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.app.core.network.NetworkMonitor
import com.weather.app.domain.model.Resource
import com.weather.app.domain.model.Weather
import com.weather.app.domain.usecase.GetCitiesWeatherUseCase
import com.weather.app.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CityListViewModel @Inject constructor(
    private val getCitiesWeatherUseCase: GetCitiesWeatherUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val networkMonitor: NetworkMonitor,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _weatherResource = getCitiesWeatherUseCase()
        .catch { emit(Resource.Error(it.localizedMessage ?: "Unexpected error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Resource.Loading,
        )

    val uiState: StateFlow<CityListUiState> = combine(
        _weatherResource,
        _searchQuery,           /** No debounce, filtering is instant on a 15 item in-memory list **/
        networkMonitor.isOnline,
    ) { resource, query, isOnline ->
        when (resource) {
            is Resource.Loading -> CityListUiState(isLoading = true, isOnline = isOnline)
            is Resource.Error -> CityListUiState(error = resource.message, isOnline = isOnline)
            is Resource.Success -> {
                val cities = resource.data
                val trimmed = query.trim()
                val isSearchActive = trimmed.length >= SEARCH_MIN_CHARS
                val filtered = filterAndSort(cities, trimmed, isSearchActive)

                CityListUiState(
                    isLoading = false,
                    cities = cities,
                    filteredCities = filtered,
                    searchQuery = query,
                    isSearchActive = isSearchActive,
                    /** This guide the user: only show hint when they've typed exactly 1 char **/
                    searchHint = if (trimmed.length == 1) {
                        "Type ${SEARCH_MIN_CHARS - trimmed.length} more letter to search…"
                    } else null,
                    isOnline = isOnline,
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CityListUiState(isLoading = true),
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.update { query }
    }

    fun onToggleFavorite(cityId: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(cityId, isFavorite)
        }
    }

    fun onClearSearch() {
        _searchQuery.update { "" }
    }

    /**
     * Filtering rules:
     *  Query blank OR below [SEARCH_MIN_CHARS]: show all cities (no filter applied)
     *  Query ≥ [SEARCH_MIN_CHARS]: case insensitive substring match on city name
     *
     * Sort rules which applied always:
     * - Favourites first
     * - Then alphabetical by city name
     */
    internal fun filterAndSort(
        cities: List<Weather>,
        trimmedQuery: String,
        isSearchActive: Boolean,
    ): List<Weather> {
        val baseList = if (isSearchActive) {
            cities.filter { it.cityName.contains(trimmedQuery, ignoreCase = true) }
        } else {
            cities
        }
        return baseList.sortedWith(
            compareByDescending<Weather> { it.isFavorite }
                .thenBy { it.cityName }
        )
    }
}
