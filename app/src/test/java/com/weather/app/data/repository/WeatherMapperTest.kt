package com.weather.app.data.repository

import com.google.common.truth.Truth.assertThat
import com.weather.app.core.utils.millisUntilNextHour
import com.weather.app.core.utils.toWindDirectionLabel
import com.weather.app.data.local.entity.WeatherEntity
import com.weather.app.data.remote.dto.MainDto
import com.weather.app.data.remote.dto.SysDto
import com.weather.app.data.remote.dto.WeatherConditionDto
import com.weather.app.data.remote.dto.WeatherResponseDto
import com.weather.app.data.remote.dto.WindDto
import org.junit.Test
import java.util.concurrent.TimeUnit

class WeatherMapperTest {

    private val dto = WeatherResponseDto(
        id = 2643743L,
        name = "London",
        sys = SysDto(country = "GB", sunrise = 1700000000L, sunset = 1700040000L),
        main = MainDto(
            temp = 15.5,
            feelsLike = 13.2,
            tempMin = 10.0,
            tempMax = 18.0,
            humidity = 82,
            pressure = 1011,
        ),
        weather = listOf(WeatherConditionDto(800, "Clear", "clear sky", "01d")),
        wind = WindDto(speed = 4.5, deg = 270),
        visibility = 9000,
        dt = 1700020000L,
        timezone = 0,
    )

    @Test
    fun `toEntity maps all fields correctly`() {
        val entity = dto.toEntity()
        assertThat(entity.cityId).isEqualTo(2643743L)
        assertThat(entity.cityName).isEqualTo("London")
        assertThat(entity.countryCode).isEqualTo("GB")
        assertThat(entity.temperatureCelsius).isEqualTo(15.5)
        assertThat(entity.feelsLikeCelsius).isEqualTo(13.2)
        assertThat(entity.humidity).isEqualTo(82)
        assertThat(entity.conditionMain).isEqualTo("Clear")
        assertThat(entity.conditionIcon).isEqualTo("01d")
        assertThat(entity.windSpeedMs).isEqualTo(4.5)
        assertThat(entity.windDegrees).isEqualTo(270)
        assertThat(entity.visibilityMeters).isEqualTo(9000)
        assertThat(entity.isFavorite).isFalse()
    }

    @Test
    fun `toEntity uses first weather condition when multiple exist`() {
        val dtoWithMultiple = dto.copy(
            weather = listOf(
                WeatherConditionDto(800, "Clear", "clear sky", "01d"),
                WeatherConditionDto(801, "Clouds", "few clouds", "02d"),
            ),
        )
        val entity = dtoWithMultiple.toEntity()
        assertThat(entity.conditionId).isEqualTo(800)
        assertThat(entity.conditionIcon).isEqualTo("01d")
    }

    @Test
    fun `toEntity handles empty weather list gracefully`() {
        val dtoNoWeather = dto.copy(weather = emptyList())
        val entity = dtoNoWeather.toEntity()
        assertThat(entity.conditionId).isEqualTo(800)
        assertThat(entity.conditionMain).isEqualTo("Clear")
    }

    @Test
    fun `toDomain maps entity to domain model correctly`() {
        val entity = WeatherEntity(
            cityId = 1L, cityName = "Paris", countryCode = "FR",
            temperatureCelsius = 20.0, feelsLikeCelsius = 19.0,
            tempMinCelsius = 15.0, tempMaxCelsius = 24.0,
            humidity = 65, windSpeedMs = 3.0, windDegrees = 90,
            visibilityMeters = 10000, pressureHpa = 1015,
            conditionId = 801, conditionMain = "Clouds",
            conditionDescription = "few clouds", conditionIcon = "02d",
            sunriseEpoch = 0L, sunsetEpoch = 0L,
            timezoneOffsetSeconds = 7200, lastUpdatedEpoch = 0L,
            isFavorite = true,
        )
        val domain = entity.toDomain()
        assertThat(domain.cityId).isEqualTo(1L)
        assertThat(domain.cityName).isEqualTo("Paris")
        assertThat(domain.isFavorite).isTrue()
        assertThat(domain.temperatureRounded).isEqualTo(20)
        assertThat(domain.iconUrl).isEqualTo("https://openweathermap.org/img/wn/02d@2x.png")
        assertThat(domain.windSpeedKmh).isWithin(0.01).of(10.8)
    }

    @Test
    fun `toDomain list maps all entities`() {
        val entities = listOf(
            dto.toEntity(),
            dto.copy(id = 2L, name = "Paris").toEntity(),
        )
        val domains = entities.toDomain()
        assertThat(domains).hasSize(2)
        assertThat(domains.map { it.cityName }).containsExactly("London", "Paris")
    }
}

class ExtensionsTest {

    @Test
    fun `toWindDirectionLabel returns correct cardinal directions`() {
        assertThat(0.toWindDirectionLabel()).isEqualTo("N")
        assertThat(90.toWindDirectionLabel()).isEqualTo("E")
        assertThat(180.toWindDirectionLabel()).isEqualTo("S")
        assertThat(270.toWindDirectionLabel()).isEqualTo("W")
        assertThat(45.toWindDirectionLabel()).isEqualTo("NE")
        assertThat(135.toWindDirectionLabel()).isEqualTo("SE")
        assertThat(225.toWindDirectionLabel()).isEqualTo("SW")
        assertThat(315.toWindDirectionLabel()).isEqualTo("NW")
    }

    @Test
    fun `millisUntilNextHour returns value between 0 and one hour`() {
        val delay = millisUntilNextHour()
        assertThat(delay).isGreaterThan(0L)
        assertThat(delay).isAtMost(TimeUnit.HOURS.toMillis(1))
    }
}
