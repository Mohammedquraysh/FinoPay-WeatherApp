package com.weather.app.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.weather.app.MainActivity
import com.weather.app.R
import com.weather.app.core.utils.millisUntilNextHour
import com.weather.app.domain.repository.WeatherRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import java.util.concurrent.TimeUnit

@HiltWorker
class HourlyWeatherWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: WeatherRepository,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val favoriteCities = repository.getFavoriteCities().firstOrNull()
        val topFavorite = favoriteCities?.firstOrNull() ?: return Result.success()

        ensureNotificationChannel()

        val tapIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_CITY_ID, topFavorite.cityId)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_weather)
            .setContentTitle("${topFavorite.cityName}, ${topFavorite.countryCode}")
            .setContentText(
                "${topFavorite.temperatureRounded}°C · ${topFavorite.conditionDescription.replaceFirstChar { it.uppercase() }}"
            )
            .setSubText("Feels like ${topFavorite.feelsLikeRounded}°C")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)

        return Result.success()
    }

    private fun ensureNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = context.getString(R.string.notification_channel_desc)
        }
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "weather_hourly_channel"
        const val NOTIFICATION_ID = 1001
        const val WORK_NAME = "hourly_weather_notification"
        const val EXTRA_CITY_ID = "extra_city_id"

        fun schedule(workManager: WorkManager) {
            val initialDelay = millisUntilNextHour()
            val request = PeriodicWorkRequestBuilder<HourlyWeatherWorker>(1, TimeUnit.HOURS)
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build()

            workManager.enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request,
            )
        }
    }
}
