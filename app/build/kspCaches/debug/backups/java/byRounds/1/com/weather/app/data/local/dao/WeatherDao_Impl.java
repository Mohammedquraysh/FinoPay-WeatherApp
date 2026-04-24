package com.weather.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.weather.app.data.local.entity.WeatherEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WeatherDao_Impl implements WeatherDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WeatherEntity> __insertionAdapterOfWeatherEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFavoriteStatus;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public WeatherDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWeatherEntity = new EntityInsertionAdapter<WeatherEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `weather_cache` (`cityId`,`cityName`,`countryCode`,`temperatureCelsius`,`feelsLikeCelsius`,`tempMinCelsius`,`tempMaxCelsius`,`humidity`,`windSpeedMs`,`windDegrees`,`visibilityMeters`,`pressureHpa`,`conditionId`,`conditionMain`,`conditionDescription`,`conditionIcon`,`sunriseEpoch`,`sunsetEpoch`,`timezoneOffsetSeconds`,`lastUpdatedEpoch`,`isFavorite`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WeatherEntity entity) {
        statement.bindLong(1, entity.getCityId());
        statement.bindString(2, entity.getCityName());
        statement.bindString(3, entity.getCountryCode());
        statement.bindDouble(4, entity.getTemperatureCelsius());
        statement.bindDouble(5, entity.getFeelsLikeCelsius());
        statement.bindDouble(6, entity.getTempMinCelsius());
        statement.bindDouble(7, entity.getTempMaxCelsius());
        statement.bindLong(8, entity.getHumidity());
        statement.bindDouble(9, entity.getWindSpeedMs());
        statement.bindLong(10, entity.getWindDegrees());
        statement.bindLong(11, entity.getVisibilityMeters());
        statement.bindLong(12, entity.getPressureHpa());
        statement.bindLong(13, entity.getConditionId());
        statement.bindString(14, entity.getConditionMain());
        statement.bindString(15, entity.getConditionDescription());
        statement.bindString(16, entity.getConditionIcon());
        statement.bindLong(17, entity.getSunriseEpoch());
        statement.bindLong(18, entity.getSunsetEpoch());
        statement.bindLong(19, entity.getTimezoneOffsetSeconds());
        statement.bindLong(20, entity.getLastUpdatedEpoch());
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(21, _tmp);
      }
    };
    this.__preparedStmtOfUpdateFavoriteStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE weather_cache SET isFavorite = ? WHERE cityId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM weather_cache";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final WeatherEntity entity, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWeatherEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertAll(final List<WeatherEntity> entities,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWeatherEntity.insert(entities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertPreservingFavorite(final WeatherEntity entity,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> WeatherDao.DefaultImpls.upsertPreservingFavorite(WeatherDao_Impl.this, entity, __cont), $completion);
  }

  @Override
  public Object upsertAllPreservingFavorite(final List<WeatherEntity> entities,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> WeatherDao.DefaultImpls.upsertAllPreservingFavorite(WeatherDao_Impl.this, entities, __cont), $completion);
  }

  @Override
  public Object updateFavoriteStatus(final long cityId, final boolean isFavorite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFavoriteStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, cityId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateFavoriteStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WeatherEntity>> observeAllCities() {
    final String _sql = "\n"
            + "        SELECT * FROM weather_cache\n"
            + "        ORDER BY isFavorite DESC, cityName ASC\n"
            + "        ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"weather_cache"}, new Callable<List<WeatherEntity>>() {
      @Override
      @NonNull
      public List<WeatherEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCityId = CursorUtil.getColumnIndexOrThrow(_cursor, "cityId");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCode");
          final int _cursorIndexOfTemperatureCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatureCelsius");
          final int _cursorIndexOfFeelsLikeCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "feelsLikeCelsius");
          final int _cursorIndexOfTempMinCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMinCelsius");
          final int _cursorIndexOfTempMaxCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMaxCelsius");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfWindSpeedMs = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeedMs");
          final int _cursorIndexOfWindDegrees = CursorUtil.getColumnIndexOrThrow(_cursor, "windDegrees");
          final int _cursorIndexOfVisibilityMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "visibilityMeters");
          final int _cursorIndexOfPressureHpa = CursorUtil.getColumnIndexOrThrow(_cursor, "pressureHpa");
          final int _cursorIndexOfConditionId = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionId");
          final int _cursorIndexOfConditionMain = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionMain");
          final int _cursorIndexOfConditionDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionDescription");
          final int _cursorIndexOfConditionIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionIcon");
          final int _cursorIndexOfSunriseEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "sunriseEpoch");
          final int _cursorIndexOfSunsetEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "sunsetEpoch");
          final int _cursorIndexOfTimezoneOffsetSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "timezoneOffsetSeconds");
          final int _cursorIndexOfLastUpdatedEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdatedEpoch");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<WeatherEntity> _result = new ArrayList<WeatherEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WeatherEntity _item;
            final long _tmpCityId;
            _tmpCityId = _cursor.getLong(_cursorIndexOfCityId);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final String _tmpCountryCode;
            _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            final double _tmpTemperatureCelsius;
            _tmpTemperatureCelsius = _cursor.getDouble(_cursorIndexOfTemperatureCelsius);
            final double _tmpFeelsLikeCelsius;
            _tmpFeelsLikeCelsius = _cursor.getDouble(_cursorIndexOfFeelsLikeCelsius);
            final double _tmpTempMinCelsius;
            _tmpTempMinCelsius = _cursor.getDouble(_cursorIndexOfTempMinCelsius);
            final double _tmpTempMaxCelsius;
            _tmpTempMaxCelsius = _cursor.getDouble(_cursorIndexOfTempMaxCelsius);
            final int _tmpHumidity;
            _tmpHumidity = _cursor.getInt(_cursorIndexOfHumidity);
            final double _tmpWindSpeedMs;
            _tmpWindSpeedMs = _cursor.getDouble(_cursorIndexOfWindSpeedMs);
            final int _tmpWindDegrees;
            _tmpWindDegrees = _cursor.getInt(_cursorIndexOfWindDegrees);
            final int _tmpVisibilityMeters;
            _tmpVisibilityMeters = _cursor.getInt(_cursorIndexOfVisibilityMeters);
            final int _tmpPressureHpa;
            _tmpPressureHpa = _cursor.getInt(_cursorIndexOfPressureHpa);
            final int _tmpConditionId;
            _tmpConditionId = _cursor.getInt(_cursorIndexOfConditionId);
            final String _tmpConditionMain;
            _tmpConditionMain = _cursor.getString(_cursorIndexOfConditionMain);
            final String _tmpConditionDescription;
            _tmpConditionDescription = _cursor.getString(_cursorIndexOfConditionDescription);
            final String _tmpConditionIcon;
            _tmpConditionIcon = _cursor.getString(_cursorIndexOfConditionIcon);
            final long _tmpSunriseEpoch;
            _tmpSunriseEpoch = _cursor.getLong(_cursorIndexOfSunriseEpoch);
            final long _tmpSunsetEpoch;
            _tmpSunsetEpoch = _cursor.getLong(_cursorIndexOfSunsetEpoch);
            final int _tmpTimezoneOffsetSeconds;
            _tmpTimezoneOffsetSeconds = _cursor.getInt(_cursorIndexOfTimezoneOffsetSeconds);
            final long _tmpLastUpdatedEpoch;
            _tmpLastUpdatedEpoch = _cursor.getLong(_cursorIndexOfLastUpdatedEpoch);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new WeatherEntity(_tmpCityId,_tmpCityName,_tmpCountryCode,_tmpTemperatureCelsius,_tmpFeelsLikeCelsius,_tmpTempMinCelsius,_tmpTempMaxCelsius,_tmpHumidity,_tmpWindSpeedMs,_tmpWindDegrees,_tmpVisibilityMeters,_tmpPressureHpa,_tmpConditionId,_tmpConditionMain,_tmpConditionDescription,_tmpConditionIcon,_tmpSunriseEpoch,_tmpSunsetEpoch,_tmpTimezoneOffsetSeconds,_tmpLastUpdatedEpoch,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<WeatherEntity> observeCity(final long cityId) {
    final String _sql = "SELECT * FROM weather_cache WHERE cityId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cityId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"weather_cache"}, new Callable<WeatherEntity>() {
      @Override
      @Nullable
      public WeatherEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCityId = CursorUtil.getColumnIndexOrThrow(_cursor, "cityId");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCode");
          final int _cursorIndexOfTemperatureCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatureCelsius");
          final int _cursorIndexOfFeelsLikeCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "feelsLikeCelsius");
          final int _cursorIndexOfTempMinCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMinCelsius");
          final int _cursorIndexOfTempMaxCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMaxCelsius");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfWindSpeedMs = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeedMs");
          final int _cursorIndexOfWindDegrees = CursorUtil.getColumnIndexOrThrow(_cursor, "windDegrees");
          final int _cursorIndexOfVisibilityMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "visibilityMeters");
          final int _cursorIndexOfPressureHpa = CursorUtil.getColumnIndexOrThrow(_cursor, "pressureHpa");
          final int _cursorIndexOfConditionId = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionId");
          final int _cursorIndexOfConditionMain = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionMain");
          final int _cursorIndexOfConditionDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionDescription");
          final int _cursorIndexOfConditionIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionIcon");
          final int _cursorIndexOfSunriseEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "sunriseEpoch");
          final int _cursorIndexOfSunsetEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "sunsetEpoch");
          final int _cursorIndexOfTimezoneOffsetSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "timezoneOffsetSeconds");
          final int _cursorIndexOfLastUpdatedEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdatedEpoch");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final WeatherEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpCityId;
            _tmpCityId = _cursor.getLong(_cursorIndexOfCityId);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final String _tmpCountryCode;
            _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            final double _tmpTemperatureCelsius;
            _tmpTemperatureCelsius = _cursor.getDouble(_cursorIndexOfTemperatureCelsius);
            final double _tmpFeelsLikeCelsius;
            _tmpFeelsLikeCelsius = _cursor.getDouble(_cursorIndexOfFeelsLikeCelsius);
            final double _tmpTempMinCelsius;
            _tmpTempMinCelsius = _cursor.getDouble(_cursorIndexOfTempMinCelsius);
            final double _tmpTempMaxCelsius;
            _tmpTempMaxCelsius = _cursor.getDouble(_cursorIndexOfTempMaxCelsius);
            final int _tmpHumidity;
            _tmpHumidity = _cursor.getInt(_cursorIndexOfHumidity);
            final double _tmpWindSpeedMs;
            _tmpWindSpeedMs = _cursor.getDouble(_cursorIndexOfWindSpeedMs);
            final int _tmpWindDegrees;
            _tmpWindDegrees = _cursor.getInt(_cursorIndexOfWindDegrees);
            final int _tmpVisibilityMeters;
            _tmpVisibilityMeters = _cursor.getInt(_cursorIndexOfVisibilityMeters);
            final int _tmpPressureHpa;
            _tmpPressureHpa = _cursor.getInt(_cursorIndexOfPressureHpa);
            final int _tmpConditionId;
            _tmpConditionId = _cursor.getInt(_cursorIndexOfConditionId);
            final String _tmpConditionMain;
            _tmpConditionMain = _cursor.getString(_cursorIndexOfConditionMain);
            final String _tmpConditionDescription;
            _tmpConditionDescription = _cursor.getString(_cursorIndexOfConditionDescription);
            final String _tmpConditionIcon;
            _tmpConditionIcon = _cursor.getString(_cursorIndexOfConditionIcon);
            final long _tmpSunriseEpoch;
            _tmpSunriseEpoch = _cursor.getLong(_cursorIndexOfSunriseEpoch);
            final long _tmpSunsetEpoch;
            _tmpSunsetEpoch = _cursor.getLong(_cursorIndexOfSunsetEpoch);
            final int _tmpTimezoneOffsetSeconds;
            _tmpTimezoneOffsetSeconds = _cursor.getInt(_cursorIndexOfTimezoneOffsetSeconds);
            final long _tmpLastUpdatedEpoch;
            _tmpLastUpdatedEpoch = _cursor.getLong(_cursorIndexOfLastUpdatedEpoch);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _result = new WeatherEntity(_tmpCityId,_tmpCityName,_tmpCountryCode,_tmpTemperatureCelsius,_tmpFeelsLikeCelsius,_tmpTempMinCelsius,_tmpTempMaxCelsius,_tmpHumidity,_tmpWindSpeedMs,_tmpWindDegrees,_tmpVisibilityMeters,_tmpPressureHpa,_tmpConditionId,_tmpConditionMain,_tmpConditionDescription,_tmpConditionIcon,_tmpSunriseEpoch,_tmpSunsetEpoch,_tmpTimezoneOffsetSeconds,_tmpLastUpdatedEpoch,_tmpIsFavorite);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<WeatherEntity>> observeFavoriteCities() {
    final String _sql = "SELECT * FROM weather_cache WHERE isFavorite = 1 ORDER BY cityName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"weather_cache"}, new Callable<List<WeatherEntity>>() {
      @Override
      @NonNull
      public List<WeatherEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCityId = CursorUtil.getColumnIndexOrThrow(_cursor, "cityId");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCode");
          final int _cursorIndexOfTemperatureCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatureCelsius");
          final int _cursorIndexOfFeelsLikeCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "feelsLikeCelsius");
          final int _cursorIndexOfTempMinCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMinCelsius");
          final int _cursorIndexOfTempMaxCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMaxCelsius");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfWindSpeedMs = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeedMs");
          final int _cursorIndexOfWindDegrees = CursorUtil.getColumnIndexOrThrow(_cursor, "windDegrees");
          final int _cursorIndexOfVisibilityMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "visibilityMeters");
          final int _cursorIndexOfPressureHpa = CursorUtil.getColumnIndexOrThrow(_cursor, "pressureHpa");
          final int _cursorIndexOfConditionId = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionId");
          final int _cursorIndexOfConditionMain = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionMain");
          final int _cursorIndexOfConditionDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionDescription");
          final int _cursorIndexOfConditionIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionIcon");
          final int _cursorIndexOfSunriseEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "sunriseEpoch");
          final int _cursorIndexOfSunsetEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "sunsetEpoch");
          final int _cursorIndexOfTimezoneOffsetSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "timezoneOffsetSeconds");
          final int _cursorIndexOfLastUpdatedEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdatedEpoch");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final List<WeatherEntity> _result = new ArrayList<WeatherEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WeatherEntity _item;
            final long _tmpCityId;
            _tmpCityId = _cursor.getLong(_cursorIndexOfCityId);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final String _tmpCountryCode;
            _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            final double _tmpTemperatureCelsius;
            _tmpTemperatureCelsius = _cursor.getDouble(_cursorIndexOfTemperatureCelsius);
            final double _tmpFeelsLikeCelsius;
            _tmpFeelsLikeCelsius = _cursor.getDouble(_cursorIndexOfFeelsLikeCelsius);
            final double _tmpTempMinCelsius;
            _tmpTempMinCelsius = _cursor.getDouble(_cursorIndexOfTempMinCelsius);
            final double _tmpTempMaxCelsius;
            _tmpTempMaxCelsius = _cursor.getDouble(_cursorIndexOfTempMaxCelsius);
            final int _tmpHumidity;
            _tmpHumidity = _cursor.getInt(_cursorIndexOfHumidity);
            final double _tmpWindSpeedMs;
            _tmpWindSpeedMs = _cursor.getDouble(_cursorIndexOfWindSpeedMs);
            final int _tmpWindDegrees;
            _tmpWindDegrees = _cursor.getInt(_cursorIndexOfWindDegrees);
            final int _tmpVisibilityMeters;
            _tmpVisibilityMeters = _cursor.getInt(_cursorIndexOfVisibilityMeters);
            final int _tmpPressureHpa;
            _tmpPressureHpa = _cursor.getInt(_cursorIndexOfPressureHpa);
            final int _tmpConditionId;
            _tmpConditionId = _cursor.getInt(_cursorIndexOfConditionId);
            final String _tmpConditionMain;
            _tmpConditionMain = _cursor.getString(_cursorIndexOfConditionMain);
            final String _tmpConditionDescription;
            _tmpConditionDescription = _cursor.getString(_cursorIndexOfConditionDescription);
            final String _tmpConditionIcon;
            _tmpConditionIcon = _cursor.getString(_cursorIndexOfConditionIcon);
            final long _tmpSunriseEpoch;
            _tmpSunriseEpoch = _cursor.getLong(_cursorIndexOfSunriseEpoch);
            final long _tmpSunsetEpoch;
            _tmpSunsetEpoch = _cursor.getLong(_cursorIndexOfSunsetEpoch);
            final int _tmpTimezoneOffsetSeconds;
            _tmpTimezoneOffsetSeconds = _cursor.getInt(_cursorIndexOfTimezoneOffsetSeconds);
            final long _tmpLastUpdatedEpoch;
            _tmpLastUpdatedEpoch = _cursor.getLong(_cursorIndexOfLastUpdatedEpoch);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _item = new WeatherEntity(_tmpCityId,_tmpCityName,_tmpCountryCode,_tmpTemperatureCelsius,_tmpFeelsLikeCelsius,_tmpTempMinCelsius,_tmpTempMaxCelsius,_tmpHumidity,_tmpWindSpeedMs,_tmpWindDegrees,_tmpVisibilityMeters,_tmpPressureHpa,_tmpConditionId,_tmpConditionMain,_tmpConditionDescription,_tmpConditionIcon,_tmpSunriseEpoch,_tmpSunsetEpoch,_tmpTimezoneOffsetSeconds,_tmpLastUpdatedEpoch,_tmpIsFavorite);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllCityIds(final Continuation<? super List<Long>> $completion) {
    final String _sql = "SELECT cityId FROM weather_cache";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<Long> _result = new ArrayList<Long>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Long _item;
            _item = _cursor.getLong(0);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCitySnapshot(final long cityId,
      final Continuation<? super WeatherEntity> $completion) {
    final String _sql = "SELECT * FROM weather_cache WHERE cityId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, cityId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WeatherEntity>() {
      @Override
      @Nullable
      public WeatherEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCityId = CursorUtil.getColumnIndexOrThrow(_cursor, "cityId");
          final int _cursorIndexOfCityName = CursorUtil.getColumnIndexOrThrow(_cursor, "cityName");
          final int _cursorIndexOfCountryCode = CursorUtil.getColumnIndexOrThrow(_cursor, "countryCode");
          final int _cursorIndexOfTemperatureCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "temperatureCelsius");
          final int _cursorIndexOfFeelsLikeCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "feelsLikeCelsius");
          final int _cursorIndexOfTempMinCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMinCelsius");
          final int _cursorIndexOfTempMaxCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMaxCelsius");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfWindSpeedMs = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeedMs");
          final int _cursorIndexOfWindDegrees = CursorUtil.getColumnIndexOrThrow(_cursor, "windDegrees");
          final int _cursorIndexOfVisibilityMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "visibilityMeters");
          final int _cursorIndexOfPressureHpa = CursorUtil.getColumnIndexOrThrow(_cursor, "pressureHpa");
          final int _cursorIndexOfConditionId = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionId");
          final int _cursorIndexOfConditionMain = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionMain");
          final int _cursorIndexOfConditionDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionDescription");
          final int _cursorIndexOfConditionIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "conditionIcon");
          final int _cursorIndexOfSunriseEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "sunriseEpoch");
          final int _cursorIndexOfSunsetEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "sunsetEpoch");
          final int _cursorIndexOfTimezoneOffsetSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "timezoneOffsetSeconds");
          final int _cursorIndexOfLastUpdatedEpoch = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdatedEpoch");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final WeatherEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpCityId;
            _tmpCityId = _cursor.getLong(_cursorIndexOfCityId);
            final String _tmpCityName;
            _tmpCityName = _cursor.getString(_cursorIndexOfCityName);
            final String _tmpCountryCode;
            _tmpCountryCode = _cursor.getString(_cursorIndexOfCountryCode);
            final double _tmpTemperatureCelsius;
            _tmpTemperatureCelsius = _cursor.getDouble(_cursorIndexOfTemperatureCelsius);
            final double _tmpFeelsLikeCelsius;
            _tmpFeelsLikeCelsius = _cursor.getDouble(_cursorIndexOfFeelsLikeCelsius);
            final double _tmpTempMinCelsius;
            _tmpTempMinCelsius = _cursor.getDouble(_cursorIndexOfTempMinCelsius);
            final double _tmpTempMaxCelsius;
            _tmpTempMaxCelsius = _cursor.getDouble(_cursorIndexOfTempMaxCelsius);
            final int _tmpHumidity;
            _tmpHumidity = _cursor.getInt(_cursorIndexOfHumidity);
            final double _tmpWindSpeedMs;
            _tmpWindSpeedMs = _cursor.getDouble(_cursorIndexOfWindSpeedMs);
            final int _tmpWindDegrees;
            _tmpWindDegrees = _cursor.getInt(_cursorIndexOfWindDegrees);
            final int _tmpVisibilityMeters;
            _tmpVisibilityMeters = _cursor.getInt(_cursorIndexOfVisibilityMeters);
            final int _tmpPressureHpa;
            _tmpPressureHpa = _cursor.getInt(_cursorIndexOfPressureHpa);
            final int _tmpConditionId;
            _tmpConditionId = _cursor.getInt(_cursorIndexOfConditionId);
            final String _tmpConditionMain;
            _tmpConditionMain = _cursor.getString(_cursorIndexOfConditionMain);
            final String _tmpConditionDescription;
            _tmpConditionDescription = _cursor.getString(_cursorIndexOfConditionDescription);
            final String _tmpConditionIcon;
            _tmpConditionIcon = _cursor.getString(_cursorIndexOfConditionIcon);
            final long _tmpSunriseEpoch;
            _tmpSunriseEpoch = _cursor.getLong(_cursorIndexOfSunriseEpoch);
            final long _tmpSunsetEpoch;
            _tmpSunsetEpoch = _cursor.getLong(_cursorIndexOfSunsetEpoch);
            final int _tmpTimezoneOffsetSeconds;
            _tmpTimezoneOffsetSeconds = _cursor.getInt(_cursorIndexOfTimezoneOffsetSeconds);
            final long _tmpLastUpdatedEpoch;
            _tmpLastUpdatedEpoch = _cursor.getLong(_cursorIndexOfLastUpdatedEpoch);
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            _result = new WeatherEntity(_tmpCityId,_tmpCityName,_tmpCountryCode,_tmpTemperatureCelsius,_tmpFeelsLikeCelsius,_tmpTempMinCelsius,_tmpTempMaxCelsius,_tmpHumidity,_tmpWindSpeedMs,_tmpWindDegrees,_tmpVisibilityMeters,_tmpPressureHpa,_tmpConditionId,_tmpConditionMain,_tmpConditionDescription,_tmpConditionIcon,_tmpSunriseEpoch,_tmpSunsetEpoch,_tmpTimezoneOffsetSeconds,_tmpLastUpdatedEpoch,_tmpIsFavorite);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
