package com.weather.app.core.di;

import com.weather.app.data.local.WeatherDatabase;
import com.weather.app.data.local.dao.WeatherDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideWeatherDaoFactory implements Factory<WeatherDao> {
  private final Provider<WeatherDatabase> databaseProvider;

  public DatabaseModule_ProvideWeatherDaoFactory(Provider<WeatherDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WeatherDao get() {
    return provideWeatherDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideWeatherDaoFactory create(
      javax.inject.Provider<WeatherDatabase> databaseProvider) {
    return new DatabaseModule_ProvideWeatherDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static DatabaseModule_ProvideWeatherDaoFactory create(
      Provider<WeatherDatabase> databaseProvider) {
    return new DatabaseModule_ProvideWeatherDaoFactory(databaseProvider);
  }

  public static WeatherDao provideWeatherDao(WeatherDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWeatherDao(database));
  }
}
