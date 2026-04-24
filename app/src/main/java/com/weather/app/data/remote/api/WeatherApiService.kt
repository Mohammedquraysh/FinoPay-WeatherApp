package com.weather.app.data.remote.api

import com.weather.app.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    /**
     * Free-tier endpoint. Fetches current weather for a single city by its
     * OpenWeatherMap city ID. We call this in parallel for all 15 cities.
     */
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("id") cityId: Long,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String,
    ): WeatherResponseDto
}
