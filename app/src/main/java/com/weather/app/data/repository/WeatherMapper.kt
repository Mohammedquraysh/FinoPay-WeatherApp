package com.weather.app.data.repository

import com.weather.app.data.local.entity.WeatherEntity
import com.weather.app.data.remote.dto.WeatherResponseDto
import com.weather.app.domain.model.Weather

/**
 *  DTO Entity
 */

fun WeatherResponseDto.toEntity(): WeatherEntity {
    val condition = weather.firstOrNull()
    return WeatherEntity(
        cityId = id,
        cityName = name,
        countryCode = sys.country,
        temperatureCelsius = main.temp,
        feelsLikeCelsius = main.feelsLike,
        tempMinCelsius = main.tempMin,
        tempMaxCelsius = main.tempMax,
        humidity = main.humidity,
        windSpeedMs = wind.speed,
        windDegrees = wind.deg,
        visibilityMeters = visibility,
        pressureHpa = main.pressure,
        conditionId = condition?.id ?: 800,
        conditionMain = condition?.main ?: "Clear",
        conditionDescription = condition?.description ?: "clear sky",
        conditionIcon = condition?.icon ?: "01d",
        sunriseEpoch = sys.sunrise,
        sunsetEpoch = sys.sunset,
        timezoneOffsetSeconds = timezone,
        lastUpdatedEpoch = System.currentTimeMillis(),
    )
}

/** Entity Domain **/

fun WeatherEntity.toDomain(): Weather = Weather(
    cityId = cityId,
    cityName = cityName,
    countryCode = countryCode,
    temperatureCelsius = temperatureCelsius,
    feelsLikeCelsius = feelsLikeCelsius,
    tempMinCelsius = tempMinCelsius,
    tempMaxCelsius = tempMaxCelsius,
    humidity = humidity,
    windSpeedMs = windSpeedMs,
    windDegrees = windDegrees,
    visibilityMeters = visibilityMeters,
    pressureHpa = pressureHpa,
    conditionId = conditionId,
    conditionMain = conditionMain,
    conditionDescription = conditionDescription,
    conditionIcon = conditionIcon,
    sunriseEpoch = sunriseEpoch,
    sunsetEpoch = sunsetEpoch,
    timezoneOffsetSeconds = timezoneOffsetSeconds,
    lastUpdatedEpoch = lastUpdatedEpoch,
    isFavorite = isFavorite,
)

fun List<WeatherEntity>.toDomain(): List<Weather> = map { it.toDomain() }
