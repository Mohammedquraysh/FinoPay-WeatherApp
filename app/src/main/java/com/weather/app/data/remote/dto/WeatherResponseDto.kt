package com.weather.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("sys")
    val sys: SysDto,
    @SerializedName("main")
    val main: MainDto,
    @SerializedName("weather")
    val weather: List<WeatherConditionDto>,
    @SerializedName("wind")
    val wind: WindDto,
    @SerializedName("visibility")
    val visibility: Int = 10000,
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("timezone")
    val timezone: Int = 0,
)

data class SysDto(
    @SerializedName("country")
    val country: String,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
)

data class MainDto(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pressure")
    val pressure: Int,
)

data class WeatherConditionDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
)

data class WindDto(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val deg: Int = 0,
)
