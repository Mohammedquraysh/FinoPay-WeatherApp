package com.weather.app.domain.usecase

import com.weather.app.domain.model.Resource
import com.weather.app.domain.model.Weather
import com.weather.app.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {
    operator fun invoke(cityId: Long): Flow<Resource<Weather>> =
        repository.getCityWeather(cityId)
}
