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
public final class ToggleFavoriteUseCase_Factory implements Factory<ToggleFavoriteUseCase> {
  private final Provider<WeatherRepository> repositoryProvider;

  public ToggleFavoriteUseCase_Factory(Provider<WeatherRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ToggleFavoriteUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ToggleFavoriteUseCase_Factory create(
      javax.inject.Provider<WeatherRepository> repositoryProvider) {
    return new ToggleFavoriteUseCase_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static ToggleFavoriteUseCase_Factory create(
      Provider<WeatherRepository> repositoryProvider) {
    return new ToggleFavoriteUseCase_Factory(repositoryProvider);
  }

  public static ToggleFavoriteUseCase newInstance(WeatherRepository repository) {
    return new ToggleFavoriteUseCase(repository);
  }
}
