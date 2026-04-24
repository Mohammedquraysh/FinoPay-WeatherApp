package com.weather.app.presentation.citydetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.weather.app.R
import com.weather.app.core.utils.toFormattedTime
import com.weather.app.core.utils.toRelativeTime
import com.weather.app.core.utils.toWeatherGradient
import com.weather.app.core.utils.toWindDirectionLabel
import com.weather.app.domain.model.Weather
import com.weather.app.presentation.components.ErrorView
import com.weather.app.presentation.components.FavoriteButton
import com.weather.app.presentation.components.LoadingView
import com.weather.app.presentation.components.WeatherStatCard

@Composable
fun CityDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: CityDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> LoadingView()
        uiState.error != null -> ErrorView(
            message = uiState.error!!,
            onRetry = {  },
        )
        uiState.weather != null -> DetailContent(
            weather = uiState.weather!!,
            onNavigateBack = onNavigateBack,
            onFavoriteToggle = viewModel::onToggleFavorite,
        )
    }
}

@Composable
private fun DetailContent(
    weather: Weather,
    onNavigateBack: () -> Unit,
    onFavoriteToggle: () -> Unit,
) {
    val (colorStart, colorEnd) = weather.conditionId.toWeatherGradient()
    val gradientColors = listOf(Color(colorStart), Color(colorEnd), Color(0xFF0A0F1E))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(gradientColors)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState()),
        ) {
            /** Top bar **/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                    )
                }
                FavoriteButton(
                    isFavorite = weather.isFavorite,
                    onToggle = onFavoriteToggle,
                )
            }

            /** Hero section **/
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = weather.cityName,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = weather.countryCode,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.7f),
                )

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = weather.iconUrl,
                    contentDescription = weather.conditionDescription,
                    modifier = Modifier.size(100.dp),
                )

                Text(
                    text = "${weather.temperatureRounded}°C",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                )

                Text(
                    text = weather.conditionDescription.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White.copy(alpha = 0.85f),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "H: ${weather.tempMaxRounded}°  ·  L: ${weather.tempMinRounded}°",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.7f),
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${stringResource(R.string.last_updated)}: ${weather.lastUpdatedEpoch.toRelativeTime()}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.5f),
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            /** Stats grid **/
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    WeatherStatCard(
                        label = stringResource(R.string.feels_like),
                        value = "${weather.feelsLikeRounded}°C",
                        icon = {
                            Icon(
                                Icons.Default.Thermostat,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        modifier = Modifier.weight(1f),
                    )
                    WeatherStatCard(
                        label = stringResource(R.string.humidity),
                        value = "${weather.humidity}%",
                        icon = {
                            Icon(
                                Icons.Default.WaterDrop,
                                contentDescription = null,
                                tint = Color(0xFF4FC3F7),
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    WeatherStatCard(
                        label = stringResource(R.string.wind),
                        value = "${"%.1f".format(weather.windSpeedKmh)} km/h ${weather.windDegrees.toWindDirectionLabel()}",
                        icon = {
                            Icon(
                                Icons.Default.Air,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        modifier = Modifier.weight(1f),
                    )
                    WeatherStatCard(
                        label = stringResource(R.string.pressure),
                        value = "${weather.pressureHpa} hPa",
                        icon = {
                            Icon(
                                Icons.Default.Compress,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    WeatherStatCard(
                        label = stringResource(R.string.visibility),
                        value = "${weather.visibilityMeters / 1000} km",
                        icon = {
                            Icon(
                                Icons.Default.Visibility,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        modifier = Modifier.weight(1f),
                    )
                    WeatherStatCard(
                        label = stringResource(R.string.sunrise),
                        value = weather.sunriseEpoch.toFormattedTime(weather.timezoneOffsetSeconds),
                        icon = {
                            Icon(
                                Icons.Default.WbSunny,
                                contentDescription = null,
                                tint = Color(0xFFFFB300),
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        modifier = Modifier.weight(1f),
                    )
                }

                /** Sunset standalone row **/
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    WeatherStatCard(
                        label = stringResource(R.string.sunset),
                        value = weather.sunsetEpoch.toFormattedTime(weather.timezoneOffsetSeconds),
                        icon = {
                            Icon(
                                Icons.Default.WbTwilight,
                                contentDescription = null,
                                tint = Color(0xFFFF6B6B),
                                modifier = Modifier.size(20.dp),
                            )
                        },
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            /** Condition pill **/
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(50.dp),
                        )
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = Color(0xFFFFB300),
                            modifier = Modifier.size(15.dp).padding(top = 4.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = " ${weather.conditionMain}  ·  ${weather.cityName}, ${weather.countryCode}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.85f),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
