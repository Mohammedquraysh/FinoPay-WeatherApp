package com.weather.app.presentation.citylist;

import com.weather.app.core.network.NetworkMonitor;
import com.weather.app.domain.usecase.GetCitiesWeatherUseCase;
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
public final class CityListViewModel_Factory implements Factory<CityListViewModel> {
  private final Provider<GetCitiesWeatherUseCase> getCitiesWeatherUseCaseProvider;

  private final Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  public CityListViewModel_Factory(
      Provider<GetCitiesWeatherUseCase> getCitiesWeatherUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider,
      Provider<NetworkMonitor> networkMonitorProvider) {
    this.getCitiesWeatherUseCaseProvider = getCitiesWeatherUseCaseProvider;
    this.toggleFavoriteUseCaseProvider = toggleFavoriteUseCaseProvider;
    this.networkMonitorProvider = networkMonitorProvider;
  }

  @Override
  public CityListViewModel get() {
    return newInstance(getCitiesWeatherUseCaseProvider.get(), toggleFavoriteUseCaseProvider.get(), networkMonitorProvider.get());
  }

  public static CityListViewModel_Factory create(
      javax.inject.Provider<GetCitiesWeatherUseCase> getCitiesWeatherUseCaseProvider,
      javax.inject.Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider,
      javax.inject.Provider<NetworkMonitor> networkMonitorProvider) {
    return new CityListViewModel_Factory(Providers.asDaggerProvider(getCitiesWeatherUseCaseProvider), Providers.asDaggerProvider(toggleFavoriteUseCaseProvider), Providers.asDaggerProvider(networkMonitorProvider));
  }

  public static CityListViewModel_Factory create(
      Provider<GetCitiesWeatherUseCase> getCitiesWeatherUseCaseProvider,
      Provider<ToggleFavoriteUseCase> toggleFavoriteUseCaseProvider,
      Provider<NetworkMonitor> networkMonitorProvider) {
    return new CityListViewModel_Factory(getCitiesWeatherUseCaseProvider, toggleFavoriteUseCaseProvider, networkMonitorProvider);
  }

  public static CityListViewModel newInstance(GetCitiesWeatherUseCase getCitiesWeatherUseCase,
      ToggleFavoriteUseCase toggleFavoriteUseCase, NetworkMonitor networkMonitor) {
    return new CityListViewModel(getCitiesWeatherUseCase, toggleFavoriteUseCase, networkMonitor);
  }
}
