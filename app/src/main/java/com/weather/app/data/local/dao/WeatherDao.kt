package com.weather.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.weather.app.data.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    /**
     * Observe all cities: favorites first, then alphabetical by city name.
     */
    @Query(
        """
        SELECT * FROM weather_cache
        ORDER BY isFavorite DESC, cityName ASC
        """
    )
    fun observeAllCities(): Flow<List<WeatherEntity>>

    /**
     * Observe a single city by its OpenWeatherMap city ID.
     */
    @Query("SELECT * FROM weather_cache WHERE cityId = :cityId")
    fun observeCity(cityId: Long): Flow<WeatherEntity?>

    /**
     * Observe only favorite cities.
     */
    @Query("SELECT * FROM weather_cache WHERE isFavorite = 1 ORDER BY cityName ASC")
    fun observeFavoriteCities(): Flow<List<WeatherEntity>>

    /**
     * Get a snapshot of all cached city IDs (used for deciding what to refresh).
     */
    @Query("SELECT cityId FROM weather_cache")
    suspend fun getAllCityIds(): List<Long>

    /**
     * Upsert weather data. Preserves the existing isFavorite flag using a
     * transaction so a network refresh never resets user preferences.
     */
    @Transaction
    suspend fun upsertPreservingFavorite(entity: WeatherEntity) {
        val existing = getCitySnapshot(entity.cityId)
        val toInsert = if (existing != null) {
            entity.copy(isFavorite = existing.isFavorite)
        } else {
            entity
        }
        upsert(toInsert)
    }

    @Transaction
    suspend fun upsertAllPreservingFavorite(entities: List<WeatherEntity>) {
        entities.forEach { upsertPreservingFavorite(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(entities: List<WeatherEntity>)

    @Query("SELECT * FROM weather_cache WHERE cityId = :cityId")
    suspend fun getCitySnapshot(cityId: Long): WeatherEntity?

    @Query("UPDATE weather_cache SET isFavorite = :isFavorite WHERE cityId = :cityId")
    suspend fun updateFavoriteStatus(cityId: Long, isFavorite: Boolean)

    @Query("DELETE FROM weather_cache")
    suspend fun clearAll()
}
