package com.weather.app.domain.usecase;

import com.weather.app.domain.repository.WeatherRepository;
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
public final class GetCityWeatherUseCase_Factory implements Factory<GetCityWeatherUseCase> {
  private final Provider<WeatherRepository> repositoryProvider;

  public GetCityWeatherUseCase_Factory(Provider<WeatherRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetCityWeatherUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetCityWeatherUseCase_Factory create(
      javax.inject.Provider<WeatherRepository> repositoryProvider) {
    return new GetCityWeatherUseCase_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static GetCityWeatherUseCase_Factory create(
      Provider<WeatherRepository> repositoryProvider) {
    return new GetCityWeatherUseCase_Factory(repositoryProvider);
  }

  public static GetCityWeatherUseCase newInstance(WeatherRepository repository) {
    return new GetCityWeatherUseCase(repository);
  }
}
