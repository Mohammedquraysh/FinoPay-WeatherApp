package com.weather.app.presentation.citylist

import com.weather.app.domain.model.Weather

/** Minimum characters the user must type before city filtering activates. */
const val SEARCH_MIN_CHARS = 2

data class CityListUiState(
    val isLoading: Boolean = false,
    val cities: List<Weather> = emptyList(),
    val filteredCities: List<Weather> = emptyList(),
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val searchHint: String? = null,
    val error: String? = null,
    val isOnline: Boolean = true,
)
