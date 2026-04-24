package com.weather.app.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = SkyBlue,
    onPrimary = DeepNavy,
    primaryContainer = SteelBlue,
    onPrimaryContainer = CloudWhite,
    secondary = AccentGold,
    onSecondary = DeepNavy,
    background = DeepNavy,
    onBackground = CloudWhite,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = MidnightBlue,
    onSurfaceVariant = FrostWhite,
    error = AccentCoral,
    outline = SteelBlue.copy(alpha = 0.5f),
)

private val LightColorScheme = lightColorScheme(
    primary = SteelBlue,
    onPrimary = Color.White,
    primaryContainer = SkyBlue.copy(alpha = 0.2f),
    onPrimaryContainer = DeepNavy,
    secondary = AccentGold,
    onSecondary = DeepNavy,
    background = CloudWhite,
    onBackground = OnSurfaceLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = FrostWhite,
    onSurfaceVariant = MidnightBlue,
    error = AccentCoral,
    outline = SteelBlue.copy(alpha = 0.3f),
)

@Composable
fun WeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = WeatherTypography,
        content = content,
    )
}
