package com.weather.app.presentation.citydetail

import com.weather.app.domain.model.Weather

data class CityDetailUiState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: String? = null,
)
