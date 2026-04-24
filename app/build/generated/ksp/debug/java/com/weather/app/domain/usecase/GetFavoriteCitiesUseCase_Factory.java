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
public final class GetFavoriteCitiesUseCase_Factory implements Factory<GetFavoriteCitiesUseCase> {
  private final Provider<WeatherRepository> repositoryProvider;

  public GetFavoriteCitiesUseCase_Factory(Provider<WeatherRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetFavoriteCitiesUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetFavoriteCitiesUseCase_Factory create(
      javax.inject.Provider<WeatherRepository> repositoryProvider) {
    return new GetFavoriteCitiesUseCase_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static GetFavoriteCitiesUseCase_Factory create(
      Provider<WeatherRepository> repositoryProvider) {
    return new GetFavoriteCitiesUseCase_Factory(repositoryProvider);
  }

  public static GetFavoriteCitiesUseCase newInstance(WeatherRepository repository) {
    return new GetFavoriteCitiesUseCase(repository);
  }
}
