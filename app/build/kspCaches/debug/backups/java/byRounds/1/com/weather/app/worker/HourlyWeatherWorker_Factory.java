package com.weather.app.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.weather.app.domain.repository.WeatherRepository;
import dagger.internal.DaggerGenerated;
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
public final class HourlyWeatherWorker_Factory {
  private final Provider<WeatherRepository> repositoryProvider;

  public HourlyWeatherWorker_Factory(Provider<WeatherRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public HourlyWeatherWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, repositoryProvider.get());
  }

  public static HourlyWeatherWorker_Factory create(
      javax.inject.Provider<WeatherRepository> repositoryProvider) {
    return new HourlyWeatherWorker_Factory(Providers.asDaggerProvider(repositoryProvider));
  }

  public static HourlyWeatherWorker_Factory create(Provider<WeatherRepository> repositoryProvider) {
    return new HourlyWeatherWorker_Factory(repositoryProvider);
  }

  public static HourlyWeatherWorker newInstance(Context context, WorkerParameters workerParams,
      WeatherRepository repository) {
    return new HourlyWeatherWorker(context, workerParams, repository);
  }
}
