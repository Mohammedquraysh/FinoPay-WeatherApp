package com.weather.app.core.di;

import com.weather.app.data.remote.api.WeatherApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideWeatherApiServiceFactory implements Factory<WeatherApiService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideWeatherApiServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public WeatherApiService get() {
    return provideWeatherApiService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideWeatherApiServiceFactory create(
      javax.inject.Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideWeatherApiServiceFactory(Providers.asDaggerProvider(retrofitProvider));
  }

  public static NetworkModule_ProvideWeatherApiServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideWeatherApiServiceFactory(retrofitProvider);
  }

  public static WeatherApiService provideWeatherApiService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideWeatherApiService(retrofit));
  }
}
