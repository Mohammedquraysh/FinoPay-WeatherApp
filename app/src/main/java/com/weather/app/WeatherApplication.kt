package com.weather.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.weather.app.worker.HourlyWeatherWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WeatherApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    /**
     * Returning our custom config here tells WorkManager to use [HiltWorkerFactory],
     * which enables @AssistedInject in [HourlyWeatherWorker].
     * WorkManager reads this lazily on first [WorkManager.getInstance] call.
     */
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    override fun onCreate() {
        super.onCreate()
        HourlyWeatherWorker.schedule(WorkManager.getInstance(this))
    }
}
