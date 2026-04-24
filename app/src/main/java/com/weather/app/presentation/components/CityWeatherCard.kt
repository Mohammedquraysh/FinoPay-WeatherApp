package com.weather.app.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.weather.app.core.utils.toWeatherGradient
import com.weather.app.domain.model.Weather

@Composable
fun CityWeatherCard(
    weather: Weather,
    onCardClick: () -> Unit,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (colorStart, colorEnd) = weather.conditionId.toWeatherGradient()
    val gradientStart = Color(colorStart)
    val gradientEnd = Color(colorEnd)

    val borderColor by animateColorAsState(
        targetValue = if (weather.isFavorite) Color(0xFFFFB300) else Color.Transparent,
        animationSpec = tween(300),
        label = "border_color",
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onCardClick),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 2.dp,
        shadowElevation = if (weather.isFavorite) 8.dp else 2.dp,
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(listOf(gradientStart, gradientEnd)),
                )
                .then(
                    if (weather.isFavorite) {
                        Modifier.background(
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xFFFFB300).copy(alpha = 0.08f),
                                    Color.Transparent,
                                ),
                            ),
                        )
                    } else Modifier,
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                /** Left: city icon and condition icon **/
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = weather.iconUrl,
                        contentDescription = weather.conditionDescription,
                        modifier = Modifier.size(40.dp),
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                /** Center: city name and condition **/
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = weather.cityName,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "${weather.countryCode} · ${weather.conditionDescription.replaceFirstChar { it.uppercase() }}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.75f),
                    )
                    Text(
                        text = "H:${weather.tempMaxRounded}° L:${weather.tempMinRounded}°",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.6f),
                    )
                }

                /** Right: temperature and favorite **/
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "${weather.temperatureRounded}°",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                    )
                    FavoriteButton(
                        isFavorite = weather.isFavorite,
                        onToggle = onFavoriteToggle,
                    )
                }
            }
        }
    }
}
