package com.weather.app.domain.usecase

import com.weather.app.domain.repository.WeatherRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {
    suspend operator fun invoke(cityId: Long, isFavorite: Boolean) =
        repository.toggleFavorite(cityId, isFavorite)
}
