package com.weather.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherEntity(
    @PrimaryKey val cityId: Long,
    val cityName: String,
    val countryCode: String,
    val temperatureCelsius: Double,
    val feelsLikeCelsius: Double,
    val tempMinCelsius: Double,
    val tempMaxCelsius: Double,
    val humidity: Int,
    val windSpeedMs: Double,
    val windDegrees: Int,
    val visibilityMeters: Int,
    val pressureHpa: Int,
    val conditionId: Int,
    val conditionMain: String,
    val conditionDescription: String,
    val conditionIcon: String,
    val sunriseEpoch: Long,
    val sunsetEpoch: Long,
    val timezoneOffsetSeconds: Int,
    val lastUpdatedEpoch: Long,
    val isFavorite: Boolean = false,
)
