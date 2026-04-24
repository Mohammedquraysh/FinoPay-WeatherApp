package com.weather.app.presentation.citylist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
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
import com.weather.app.R
import com.weather.app.presentation.components.CityWeatherCard
import com.weather.app.presentation.components.ErrorView
import com.weather.app.presentation.components.LoadingView
import com.weather.app.presentation.components.OfflineBanner
import com.weather.app.presentation.components.WeatherSearchBar
import com.weather.app.presentation.theme.DeepNavy
import com.weather.app.presentation.theme.MidnightBlue
import com.weather.app.presentation.theme.SteelBlue

@Composable
fun CityListScreen(
    onCityClick: (Long) -> Unit,
    viewModel: CityListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(DeepNavy, MidnightBlue, Color(0xFF162230)),
                ),
            ),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            /** Status bar spacer + offline banner **/
            Box(modifier = Modifier.statusBarsPadding()) {
                OfflineBanner(
                    isVisible = !uiState.isOnline,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            /** Header **/
            CityListHeader(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp))

            /** Search bar **/
            WeatherSearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onClear = viewModel::onClearSearch,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            /** Hint shown when user has typed exactly 1 character (below 2-char minimum) **/
            AnimatedVisibility(
                visible = uiState.searchHint != null,
                enter = fadeIn(tween(150)),
                exit = fadeOut(tween(150)),
            ) {
                Text(
                    text = uiState.searchHint ?: "",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            /** Content **/
            when {
                uiState.isLoading -> LoadingView(modifier = Modifier.weight(1f))
                uiState.error != null && uiState.cities.isEmpty() -> ErrorView(
                    message = uiState.error ?: stringResource(R.string.something_went_wrong),
                    onRetry = { },
                    modifier = Modifier.weight(1f),
                )
                else -> CitiesList(
                    uiState = uiState,
                    onCityClick = onCityClick,
                    onFavoriteToggle = { cityId, isFavorite ->
                        viewModel.onToggleFavorite(cityId, isFavorite)
                    },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun CityListHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = "WeatherNow",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Live conditions worldwide",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.6f),
            )
        }
        Icon(
            imageVector = Icons.Default.WbSunny,
            contentDescription = null,
            tint = Color(0xFFFFB300),
            modifier = Modifier.size(36.dp),
        )
    }
}

@Composable
private fun CitiesList(
    uiState: CityListUiState,
    onCityClick: (Long) -> Unit,
    onFavoriteToggle: (Long, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val cities = uiState.filteredCities

    if (cities.isEmpty() && uiState.isSearchActive) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = null,
                    tint = Color(0xFFFFB300),
                    modifier = Modifier.size(56.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.no_cities_found),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.7f),
                )
            }
        }
        return
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        /** Favorites section header **/
        val favorites = cities.filter { it.isFavorite }
        if (favorites.isNotEmpty() && uiState.searchQuery.isBlank()) {
            item(key = "favorites_header") {
                SectionHeader(title = stringResource(R.string.favorites))
            }
            itemsIndexed(
                items = favorites,
                key = { _, item -> "fav_${item.cityId}" },
            ) { index, weather ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(tween(300, delayMillis = index * 50)) +
                            slideInVertically(tween(300, delayMillis = index * 50)) { it / 4 },
                ) {
                    CityWeatherCard(
                        weather = weather,
                        onCardClick = { onCityClick(weather.cityId) },
                        onFavoriteToggle = { onFavoriteToggle(weather.cityId, !weather.isFavorite) },
                    )
                }
            }
            item(key = "all_cities_header") {
                Spacer(modifier = Modifier.height(4.dp))
                SectionHeader(title = stringResource(R.string.all_cities))
            }
        }

        /** When searching, it show all matches (favorites included inline).
         * when browsing, show only non-favorites here and favorites already rendered above.
         * **/
        val nonFavoritesToShow = if (uiState.searchQuery.isBlank()) {
            cities.filter { !it.isFavorite }
        } else {
            cities
        }
        itemsIndexed(
            items = nonFavoritesToShow,
            key = { _, item -> "city_${item.cityId}" },
        ) { index, weather ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(tween(300, delayMillis = index * 40)) +
                        slideInVertically(tween(300, delayMillis = index * 40)) { it / 4 },
            ) {
                CityWeatherCard(
                    weather = weather,
                    onCardClick = { onCityClick(weather.cityId) },
                    onFavoriteToggle = { onFavoriteToggle(weather.cityId, !weather.isFavorite) },
                )
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun SectionHeader(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        color = SteelBlue,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(horizontal = 4.dp, vertical = 8.dp),
    )
}
