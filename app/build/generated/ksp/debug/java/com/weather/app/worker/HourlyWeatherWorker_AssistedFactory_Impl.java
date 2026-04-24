package com.weather.app.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class HourlyWeatherWorker_AssistedFactory_Impl implements HourlyWeatherWorker_AssistedFactory {
  private final HourlyWeatherWorker_Factory delegateFactory;

  HourlyWeatherWorker_AssistedFactory_Impl(HourlyWeatherWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public HourlyWeatherWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<HourlyWeatherWorker_AssistedFactory> create(
      HourlyWeatherWorker_Factory delegateFactory) {
    return InstanceFactory.create(new HourlyWeatherWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<HourlyWeatherWorker_AssistedFactory> createFactoryProvider(
      HourlyWeatherWorker_Factory delegateFactory) {
    return InstanceFactory.create(new HourlyWeatherWorker_AssistedFactory_Impl(delegateFactory));
  }
}
