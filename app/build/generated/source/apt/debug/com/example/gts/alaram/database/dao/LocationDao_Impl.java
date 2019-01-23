package com.example.gts.alaram.database.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.gts.alaram.database.converters.DateConverter;
import com.example.gts.alaram.models.LocationModel;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;

@SuppressWarnings("unchecked")
public final class LocationDao_Impl implements LocationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfLocationModel;

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfLocationModel;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfLocationModel;

  public LocationDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLocationModel = new EntityInsertionAdapter<LocationModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `locations`(`id`,`place_name`,`latitude`,`longitude`,`use_count`,`is_hidden`,`date_added`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocationModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getPlaceName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPlaceName());
        }
        stmt.bindDouble(3, value.getLatitude());
        stmt.bindDouble(4, value.getLongitude());
        stmt.bindLong(5, value.getUseCount());
        stmt.bindLong(6, value.getIsHidden());
        final String _tmp;
        _tmp = __dateConverter.dateToString(value.getDateAdded());
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp);
        }
      }
    };
    this.__deletionAdapterOfLocationModel = new EntityDeletionOrUpdateAdapter<LocationModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `locations` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocationModel value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfLocationModel = new EntityDeletionOrUpdateAdapter<LocationModel>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `locations` SET `id` = ?,`place_name` = ?,`latitude` = ?,`longitude` = ?,`use_count` = ?,`is_hidden` = ?,`date_added` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocationModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getPlaceName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPlaceName());
        }
        stmt.bindDouble(3, value.getLatitude());
        stmt.bindDouble(4, value.getLongitude());
        stmt.bindLong(5, value.getUseCount());
        stmt.bindLong(6, value.getIsHidden());
        final String _tmp;
        _tmp = __dateConverter.dateToString(value.getDateAdded());
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp);
        }
        stmt.bindLong(8, value.getId());
      }
    };
  }

  @Override
  public long insertLocation(LocationModel locationModel) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfLocationModel.insertAndReturnId(locationModel);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Long> insertLocations(List<LocationModel> locations) {
    __db.beginTransaction();
    try {
      List<Long> _result = __insertionAdapterOfLocationModel.insertAndReturnIdsList(locations);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteLocation(LocationModel locationModel) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfLocationModel.handle(locationModel);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateLocation(LocationModel locationModel) {
    __db.beginTransaction();
    try {
      __updateAdapterOfLocationModel.handle(locationModel);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<LocationModel> getAllLocations() {
    final String _sql = "SELECT * FROM locations WHERE is_hidden = 0 ORDER BY use_count DESC, place_name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfPlaceName = _cursor.getColumnIndexOrThrow("place_name");
      final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
      final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
      final int _cursorIndexOfUseCount = _cursor.getColumnIndexOrThrow("use_count");
      final int _cursorIndexOfIsHidden = _cursor.getColumnIndexOrThrow("is_hidden");
      final int _cursorIndexOfDateAdded = _cursor.getColumnIndexOrThrow("date_added");
      final List<LocationModel> _result = new ArrayList<LocationModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final LocationModel _item;
        final String _tmpPlaceName;
        _tmpPlaceName = _cursor.getString(_cursorIndexOfPlaceName);
        final double _tmpLatitude;
        _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
        final double _tmpLongitude;
        _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
        final int _tmpUseCount;
        _tmpUseCount = _cursor.getInt(_cursorIndexOfUseCount);
        final int _tmpIsHidden;
        _tmpIsHidden = _cursor.getInt(_cursorIndexOfIsHidden);
        final LocalDate _tmpDateAdded;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDateAdded);
        _tmpDateAdded = __dateConverter.stringToDate(_tmp);
        _item = new LocationModel(_tmpPlaceName,_tmpLatitude,_tmpLongitude,_tmpUseCount,_tmpIsHidden,_tmpDateAdded);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LocationModel getLocationWithId(long locationId) {
    final String _sql = "SELECT * FROM locations WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, locationId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfPlaceName = _cursor.getColumnIndexOrThrow("place_name");
      final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
      final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
      final int _cursorIndexOfUseCount = _cursor.getColumnIndexOrThrow("use_count");
      final int _cursorIndexOfIsHidden = _cursor.getColumnIndexOrThrow("is_hidden");
      final int _cursorIndexOfDateAdded = _cursor.getColumnIndexOrThrow("date_added");
      final LocationModel _result;
      if(_cursor.moveToFirst()) {
        final String _tmpPlaceName;
        _tmpPlaceName = _cursor.getString(_cursorIndexOfPlaceName);
        final double _tmpLatitude;
        _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
        final double _tmpLongitude;
        _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
        final int _tmpUseCount;
        _tmpUseCount = _cursor.getInt(_cursorIndexOfUseCount);
        final int _tmpIsHidden;
        _tmpIsHidden = _cursor.getInt(_cursorIndexOfIsHidden);
        final LocalDate _tmpDateAdded;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDateAdded);
        _tmpDateAdded = __dateConverter.stringToDate(_tmp);
        _result = new LocationModel(_tmpPlaceName,_tmpLatitude,_tmpLongitude,_tmpUseCount,_tmpIsHidden,_tmpDateAdded);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
