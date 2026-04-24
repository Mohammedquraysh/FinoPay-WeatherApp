package com.weather.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.weather.app.data.local.dao.WeatherDao;
import com.weather.app.data.local.dao.WeatherDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WeatherDatabase_Impl extends WeatherDatabase {
  private volatile WeatherDao _weatherDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `weather_cache` (`cityId` INTEGER NOT NULL, `cityName` TEXT NOT NULL, `countryCode` TEXT NOT NULL, `temperatureCelsius` REAL NOT NULL, `feelsLikeCelsius` REAL NOT NULL, `tempMinCelsius` REAL NOT NULL, `tempMaxCelsius` REAL NOT NULL, `humidity` INTEGER NOT NULL, `windSpeedMs` REAL NOT NULL, `windDegrees` INTEGER NOT NULL, `visibilityMeters` INTEGER NOT NULL, `pressureHpa` INTEGER NOT NULL, `conditionId` INTEGER NOT NULL, `conditionMain` TEXT NOT NULL, `conditionDescription` TEXT NOT NULL, `conditionIcon` TEXT NOT NULL, `sunriseEpoch` INTEGER NOT NULL, `sunsetEpoch` INTEGER NOT NULL, `timezoneOffsetSeconds` INTEGER NOT NULL, `lastUpdatedEpoch` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, PRIMARY KEY(`cityId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '038356eb2fd5dba730256e667fcd9802')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `weather_cache`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsWeatherCache = new HashMap<String, TableInfo.Column>(21);
        _columnsWeatherCache.put("cityId", new TableInfo.Column("cityId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("cityName", new TableInfo.Column("cityName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("countryCode", new TableInfo.Column("countryCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("temperatureCelsius", new TableInfo.Column("temperatureCelsius", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("feelsLikeCelsius", new TableInfo.Column("feelsLikeCelsius", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("tempMinCelsius", new TableInfo.Column("tempMinCelsius", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("tempMaxCelsius", new TableInfo.Column("tempMaxCelsius", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("humidity", new TableInfo.Column("humidity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("windSpeedMs", new TableInfo.Column("windSpeedMs", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("windDegrees", new TableInfo.Column("windDegrees", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("visibilityMeters", new TableInfo.Column("visibilityMeters", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("pressureHpa", new TableInfo.Column("pressureHpa", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("conditionId", new TableInfo.Column("conditionId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("conditionMain", new TableInfo.Column("conditionMain", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("conditionDescription", new TableInfo.Column("conditionDescription", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("conditionIcon", new TableInfo.Column("conditionIcon", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("sunriseEpoch", new TableInfo.Column("sunriseEpoch", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("sunsetEpoch", new TableInfo.Column("sunsetEpoch", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("timezoneOffsetSeconds", new TableInfo.Column("timezoneOffsetSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("lastUpdatedEpoch", new TableInfo.Column("lastUpdatedEpoch", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherCache.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWeatherCache = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWeatherCache = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWeatherCache = new TableInfo("weather_cache", _columnsWeatherCache, _foreignKeysWeatherCache, _indicesWeatherCache);
        final TableInfo _existingWeatherCache = TableInfo.read(db, "weather_cache");
        if (!_infoWeatherCache.equals(_existingWeatherCache)) {
          return new RoomOpenHelper.ValidationResult(false, "weather_cache(com.weather.app.data.local.entity.WeatherEntity).\n"
                  + " Expected:\n" + _infoWeatherCache + "\n"
                  + " Found:\n" + _existingWeatherCache);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "038356eb2fd5dba730256e667fcd9802", "8714a8a2b7b3c6e6509fb42aef68ea4e");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "weather_cache");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `weather_cache`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(WeatherDao.class, WeatherDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public WeatherDao weatherDao() {
    if (_weatherDao != null) {
      return _weatherDao;
    } else {
      synchronized(this) {
        if(_weatherDao == null) {
          _weatherDao = new WeatherDao_Impl(this);
        }
        return _weatherDao;
      }
    }
  }
}
