package com.weather.app.data.repository;

import com.weather.app.core.network.NetworkMonitor;
import com.weather.app.data.local.dao.WeatherDao;
import com.weather.app.data.remote.api.WeatherApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class WeatherRepositoryImpl_Factory implements Factory<WeatherRepositoryImpl> {
  private final Provider<WeatherApiService> apiProvider;

  private final Provider<WeatherDao> daoProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  public WeatherRepositoryImpl_Factory(Provider<WeatherApiService> apiProvider,
      Provider<WeatherDao> daoProvider, Provider<NetworkMonitor> networkMonitorProvider) {
    this.apiProvider = apiProvider;
    this.daoProvider = daoProvider;
    this.networkMonitorProvider = networkMonitorProvider;
  }

  @Override
  public WeatherRepositoryImpl get() {
    return newInstance(apiProvider.get(), daoProvider.get(), networkMonitorProvider.get());
  }

  public static WeatherRepositoryImpl_Factory create(
      javax.inject.Provider<WeatherApiService> apiProvider,
      javax.inject.Provider<WeatherDao> daoProvider,
      javax.inject.Provider<NetworkMonitor> networkMonitorProvider) {
    return new WeatherRepositoryImpl_Factory(Providers.asDaggerProvider(apiProvider), Providers.asDaggerProvider(daoProvider), Providers.asDaggerProvider(networkMonitorProvider));
  }

  public static WeatherRepositoryImpl_Factory create(Provider<WeatherApiService> apiProvider,
      Provider<WeatherDao> daoProvider, Provider<NetworkMonitor> networkMonitorProvider) {
    return new WeatherRepositoryImpl_Factory(apiProvider, daoProvider, networkMonitorProvider);
  }

  public static WeatherRepositoryImpl newInstance(WeatherApiService api, WeatherDao dao,
      NetworkMonitor networkMonitor) {
    return new WeatherRepositoryImpl(api, dao, networkMonitor);
  }
}
