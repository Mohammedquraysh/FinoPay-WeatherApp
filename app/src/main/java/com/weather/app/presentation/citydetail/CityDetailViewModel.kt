package com.weather.app.presentation.citydetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.app.domain.model.Resource
import com.weather.app.domain.model.Weather
import com.weather.app.domain.usecase.GetCityWeatherUseCase
import com.weather.app.domain.usecase.ToggleFavoriteUseCase
import com.weather.app.presentation.navigation.NavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class CityDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getCityWeatherUseCase: GetCityWeatherUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
) : ViewModel() {

    private val cityId: Long = checkNotNull(savedStateHandle[NavArgs.CITY_ID])

    val uiState: StateFlow<CityDetailUiState> =
        getCityWeatherUseCase(cityId)
            .map { resource ->
                when (resource) {
                    is Resource.Loading -> CityDetailUiState(isLoading = true)
                    is Resource.Error -> CityDetailUiState(error = resource.message)
                    is Resource.Success -> CityDetailUiState(weather = resource.data)
                }
            }
            .catch { throwable ->
                emit(CityDetailUiState(error = throwable.localizedMessage ?: "Unexpected error"))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CityDetailUiState(isLoading = true),
            )

    fun onToggleFavorite() {
        val current = uiState.value.weather ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(current.cityId, !current.isFavorite)
        }
    }
}
