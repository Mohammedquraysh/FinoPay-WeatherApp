package com.weather.app;

import androidx.hilt.work.HiltWorkerFactory;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class WeatherApplication_MembersInjector implements MembersInjector<WeatherApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public WeatherApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<WeatherApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new WeatherApplication_MembersInjector(workerFactoryProvider);
  }

  public static MembersInjector<WeatherApplication> create(
      javax.inject.Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new WeatherApplication_MembersInjector(Providers.asDaggerProvider(workerFactoryProvider));
  }

  @Override
  public void injectMembers(WeatherApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.weather.app.WeatherApplication.workerFactory")
  public static void injectWorkerFactory(WeatherApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
