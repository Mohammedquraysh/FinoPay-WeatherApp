package com.weather.app.domain.usecase

import com.weather.app.domain.model.Resource
import com.weather.app.domain.model.Weather
import com.weather.app.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCitiesWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {
    operator fun invoke(): Flow<Resource<List<Weather>>> =
        repository.getCitiesWeather()
}
