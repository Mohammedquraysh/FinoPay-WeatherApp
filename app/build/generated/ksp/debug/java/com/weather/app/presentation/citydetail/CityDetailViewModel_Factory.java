package com.weather.app.presentation.citydetail;

import androidx.lifecycle.SavedStateHandle;
import com.weather.app.domain.usecase.GetCityWeatherUseCase;
import com.weather.app.domain.usecase.ToggleFavoriteUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class CityDetailViewModel_Factory implements Factory<CityDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<GetCityWeatherUseCase> getCityWeatherUseCaseProvider;

  private final Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider;

  public CityDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetCityWeatherUseCase> getCityWeatherUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.getCityWeatherUseCaseProvider = getCityWeatherUseCaseProvider;
    this.toggleFavoriteUseCaseProvider = toggleFavoriteUseCaseProvider;
  }

  @Override
  public CityDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), getCityWeatherUseCaseProvider.get(), toggleFavoriteUseCaseProvider.get());
  }

  public static CityDetailViewModel_Factory create(
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider,
      javax.inject.Provider<GetCityWeatherUseCase> getCityWeatherUseCaseProvider,
      javax.inject.Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider) {
    return new CityDetailViewModel_Factory(Providers.asDaggerProvider(savedStateHandleProvider), Providers.asDaggerProvider(getCityWeatherUseCaseProvider), Providers.asDaggerProvider(toggleFavoriteUseCaseProvider));
  }

  public static CityDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetCityWeatherUseCase> getCityWeatherUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider) {
    return new CityDetailViewModel_Factory(savedStateHandleProvider, getCityWeatherUseCaseProvider, toggleFavoriteUseCaseProvider);
  }

  public static CityDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      GetCityWeatherUseCase getCityWeatherUseCase, ToggleFavoriteUseCase toggleFavoriteUseCase) {
    return new CityDetailViewModel(savedStateHandle, getCityWeatherUseCase, toggleFavoriteUseCase);
  }
}
