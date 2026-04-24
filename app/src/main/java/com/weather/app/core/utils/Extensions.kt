package com.weather.app.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun Long.toFormattedTime(timezoneOffsetSeconds: Int = 0, pattern: String = "HH:mm"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    val adjustedMillis = (this + timezoneOffsetSeconds) * 1000L
    return sdf.format(Date(adjustedMillis))
}

fun Long.toRelativeTime(): String {
    val now = System.currentTimeMillis()
    val diffMs = now - this
    return when {
        diffMs < TimeUnit.MINUTES.toMillis(1) -> "Just now"
        diffMs < TimeUnit.HOURS.toMillis(1) -> {
            val mins = TimeUnit.MILLISECONDS.toMinutes(diffMs)
            "${mins}m ago"
        }
        diffMs < TimeUnit.HOURS.toMillis(24) -> {
            val hours = TimeUnit.MILLISECONDS.toHours(diffMs)
            "${hours}h ago"
        }
        else -> {
            val days = TimeUnit.MILLISECONDS.toDays(diffMs)
            "${days}d ago"
        }
    }
}

fun Int.toWindDirectionLabel(): String = when (this) {
    in 0..22 -> "N"
    in 23..67 -> "NE"
    in 68..112 -> "E"
    in 113..157 -> "SE"
    in 158..202 -> "S"
    in 203..247 -> "SW"
    in 248..292 -> "W"
    in 293..337 -> "NW"
    else -> "N"
}

fun Int.toWeatherGradient(): Pair<Long, Long> = when (this) {
    in 200..232 -> 0xFF1A1A2E.toLong() to 0xFF16213E.toLong()   // Thunderstorm - dark navy
    in 300..321 -> 0xFF2C3E50.toLong() to 0xFF4CA1AF.toLong()   // Drizzle - steel teal
    in 500..531 -> 0xFF1F3A5F.toLong() to 0xFF4A6FA5.toLong()   // Rain - deep blue
    in 600..622 -> 0xFF8EAEBF.toLong() to 0xFFD8E8F0.toLong()   // Snow - icy blue
    in 700..781 -> 0xFF7B8A8B.toLong() to 0xFFBDC3C7.toLong()   // Atmosphere - grey
    800 -> 0xFF0D1B2A.toLong() to 0xFF1B6CA8.toLong()            // Clear - deep sky
    in 801..804 -> 0xFF2C3E50.toLong() to 0xFF3498DB.toLong()   // Cloudy - grey blue
    else -> 0xFF0D1B2A.toLong() to 0xFF1B6CA8.toLong()
}

/** Milliseconds until the next top-of-hour tick. */
fun millisUntilNextHour(): Long {
    val now = System.currentTimeMillis()
    val msPerHour = TimeUnit.HOURS.toMillis(1)
    return msPerHour - (now % msPerHour)
}
