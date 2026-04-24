package com.weather.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.app.data.local.dao.WeatherDao
import com.weather.app.data.local.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
