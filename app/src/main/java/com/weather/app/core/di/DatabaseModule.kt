package com.weather.app.core.di

import android.content.Context
import androidx.room.Room
import com.weather.app.data.local.WeatherDatabase
import com.weather.app.data.local.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database",
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao =
        database.weatherDao()
}
