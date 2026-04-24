package com.weather.app.domain.model

data class Weather(
    val cityId: Long,
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
) {
    val temperatureRounded: Int get() = temperatureCelsius.toInt()
    val feelsLikeRounded: Int get() = feelsLikeCelsius.toInt()
    val tempMinRounded: Int get() = tempMinCelsius.toInt()
    val tempMaxRounded: Int get() = tempMaxCelsius.toInt()
    val iconUrl: String get() = "https://openweathermap.org/img/wn/${conditionIcon}@2x.png"
    val windSpeedKmh: Double get() = windSpeedMs * 3.6
}
