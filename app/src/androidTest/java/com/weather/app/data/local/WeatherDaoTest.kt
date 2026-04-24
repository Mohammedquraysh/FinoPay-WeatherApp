package com.weather.app.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.weather.app.data.local.dao.WeatherDao
import com.weather.app.data.local.entity.WeatherEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {

    private lateinit var database: WeatherDatabase
    private lateinit var dao: WeatherDao

    private fun buildEntity(
        id: Long,
        name: String,
        isFavorite: Boolean = false,
    ) = WeatherEntity(
        cityId = id,
        cityName = name,
        countryCode = "GB",
        temperatureCelsius = 15.0,
        feelsLikeCelsius = 13.0,
        tempMinCelsius = 10.0,
        tempMaxCelsius = 18.0,
        humidity = 80,
        windSpeedMs = 5.0,
        windDegrees = 270,
        visibilityMeters = 10000,
        pressureHpa = 1013,
        conditionId = 800,
        conditionMain = "Clear",
        conditionDescription = "clear sky",
        conditionIcon = "01d",
        sunriseEpoch = 0L,
        sunsetEpoch = 0L,
        timezoneOffsetSeconds = 0,
        lastUpdatedEpoch = System.currentTimeMillis(),
        isFavorite = isFavorite,
    )

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java,
        ).allowMainThreadQueries().build()
        dao = database.weatherDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndObserveAllCities() = runTest {
        dao.upsertAll(listOf(buildEntity(1L, "London"), buildEntity(2L, "Paris")))

        dao.observeAllCities().test {
            val cities = awaitItem()
            assertThat(cities).hasSize(2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun observeAllCities_favoritesSortedFirst() = runTest {
        dao.upsertAll(
            listOf(
                buildEntity(1L, "London", isFavorite = false),
                buildEntity(2L, "Tokyo", isFavorite = true),
                buildEntity(3L, "Paris", isFavorite = false),
            ),
        )

        dao.observeAllCities().test {
            val cities = awaitItem()
            assertThat(cities.first().cityName).isEqualTo("Tokyo")
            assertThat(cities.first().isFavorite).isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun upsertPreservingFavorite_doesNotResetFavoriteFlag() = runTest {
        // Insert with isFavorite = true
        dao.upsert(buildEntity(1L, "London", isFavorite = true))

        // Upsert fresh network data (isFavorite = false by default)
        dao.upsertPreservingFavorite(buildEntity(1L, "London", isFavorite = false))

        dao.observeCity(1L).test {
            val entity = awaitItem()
            assertThat(entity).isNotNull()
            assertThat(entity!!.isFavorite).isTrue() // preserved
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun updateFavoriteStatus_changesOnlyTargetCity() = runTest {
        dao.upsertAll(
            listOf(
                buildEntity(1L, "London", isFavorite = false),
                buildEntity(2L, "Paris", isFavorite = false),
            ),
        )

        dao.updateFavoriteStatus(cityId = 1L, isFavorite = true)

        dao.observeAllCities().test {
            val cities = awaitItem()
            val london = cities.first { it.cityId == 1L }
            val paris = cities.first { it.cityId == 2L }
            assertThat(london.isFavorite).isTrue()
            assertThat(paris.isFavorite).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun observeFavoriteCities_returnsOnlyFavorites() = runTest {
        dao.upsertAll(
            listOf(
                buildEntity(1L, "London", isFavorite = true),
                buildEntity(2L, "Paris", isFavorite = false),
                buildEntity(3L, "Tokyo", isFavorite = true),
            ),
        )

        dao.observeFavoriteCities().test {
            val favorites = awaitItem()
            assertThat(favorites).hasSize(2)
            assertThat(favorites.map { it.cityName }).containsExactly("London", "Tokyo")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getAllCityIds_returnsAllIds() = runTest {
        dao.upsertAll(listOf(buildEntity(10L, "A"), buildEntity(20L, "B"), buildEntity(30L, "C")))
        val ids = dao.getAllCityIds()
        assertThat(ids).containsExactly(10L, 20L, 30L)
    }

    @Test
    fun clearAll_removesAllEntries() = runTest {
        dao.upsertAll(listOf(buildEntity(1L, "X"), buildEntity(2L, "Y")))
        dao.clearAll()

        dao.observeAllCities().test {
            assertThat(awaitItem()).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun upsert_updatesExistingCityData() = runTest {
        dao.upsert(buildEntity(1L, "London").copy(temperatureCelsius = 10.0))
        dao.upsert(buildEntity(1L, "London").copy(temperatureCelsius = 22.0))

        dao.observeCity(1L).test {
            val entity = awaitItem()
            assertThat(entity!!.temperatureCelsius).isEqualTo(22.0)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
