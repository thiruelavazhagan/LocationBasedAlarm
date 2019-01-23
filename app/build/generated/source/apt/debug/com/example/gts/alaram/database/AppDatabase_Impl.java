package com.example.gts.alaram.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.gts.alaram.database.dao.LocationDao;
import com.example.gts.alaram.database.dao.LocationDao_Impl;
import com.example.gts.alaram.database.dao.TaskDao;
import com.example.gts.alaram.database.dao.TaskDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class AppDatabase_Impl extends AppDatabase {
  private volatile TaskDao _taskDao;

  private volatile LocationDao _locationDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tasks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `task_name` TEXT, `location_id` INTEGER NOT NULL, `image_uri` TEXT, `is_done` INTEGER NOT NULL, `is_alarm_set` INTEGER NOT NULL, `reminder_range` INTEGER NOT NULL, `note` TEXT, `start_time` TEXT, `end_time` TEXT, `start_date` TEXT, `end_date` TEXT, `next_start_date` TEXT, `repeat_type` INTEGER NOT NULL, `movement_type` INTEGER NOT NULL, `activity_type` INTEGER NOT NULL, `last_distance` REAL NOT NULL, `last_triggered` TEXT, `snoozed_at` INTEGER, `date_added` TEXT, `repeat_code` INTEGER NOT NULL, FOREIGN KEY(`location_id`) REFERENCES `locations`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `locations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `place_name` TEXT, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `use_count` INTEGER NOT NULL, `is_hidden` INTEGER NOT NULL, `date_added` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"c4fedef76edc7f9627a158c7e42ba99e\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `tasks`");
        _db.execSQL("DROP TABLE IF EXISTS `locations`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTasks = new HashMap<String, TableInfo.Column>(21);
        _columnsTasks.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsTasks.put("task_name", new TableInfo.Column("task_name", "TEXT", false, 0));
        _columnsTasks.put("location_id", new TableInfo.Column("location_id", "INTEGER", true, 0));
        _columnsTasks.put("image_uri", new TableInfo.Column("image_uri", "TEXT", false, 0));
        _columnsTasks.put("is_done", new TableInfo.Column("is_done", "INTEGER", true, 0));
        _columnsTasks.put("is_alarm_set", new TableInfo.Column("is_alarm_set", "INTEGER", true, 0));
        _columnsTasks.put("reminder_range", new TableInfo.Column("reminder_range", "INTEGER", true, 0));
        _columnsTasks.put("note", new TableInfo.Column("note", "TEXT", false, 0));
        _columnsTasks.put("start_time", new TableInfo.Column("start_time", "TEXT", false, 0));
        _columnsTasks.put("end_time", new TableInfo.Column("end_time", "TEXT", false, 0));
        _columnsTasks.put("start_date", new TableInfo.Column("start_date", "TEXT", false, 0));
        _columnsTasks.put("end_date", new TableInfo.Column("end_date", "TEXT", false, 0));
        _columnsTasks.put("next_start_date", new TableInfo.Column("next_start_date", "TEXT", false, 0));
        _columnsTasks.put("repeat_type", new TableInfo.Column("repeat_type", "INTEGER", true, 0));
        _columnsTasks.put("movement_type", new TableInfo.Column("movement_type", "INTEGER", true, 0));
        _columnsTasks.put("activity_type", new TableInfo.Column("activity_type", "INTEGER", true, 0));
        _columnsTasks.put("last_distance", new TableInfo.Column("last_distance", "REAL", true, 0));
        _columnsTasks.put("last_triggered", new TableInfo.Column("last_triggered", "TEXT", false, 0));
        _columnsTasks.put("snoozed_at", new TableInfo.Column("snoozed_at", "INTEGER", false, 0));
        _columnsTasks.put("date_added", new TableInfo.Column("date_added", "TEXT", false, 0));
        _columnsTasks.put("repeat_code", new TableInfo.Column("repeat_code", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTasks = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTasks.add(new TableInfo.ForeignKey("locations", "NO ACTION", "NO ACTION",Arrays.asList("location_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTasks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTasks = new TableInfo("tasks", _columnsTasks, _foreignKeysTasks, _indicesTasks);
        final TableInfo _existingTasks = TableInfo.read(_db, "tasks");
        if (! _infoTasks.equals(_existingTasks)) {
          throw new IllegalStateException("Migration didn't properly handle tasks(com.example.gts.alaram.models.TaskModel).\n"
                  + " Expected:\n" + _infoTasks + "\n"
                  + " Found:\n" + _existingTasks);
        }
        final HashMap<String, TableInfo.Column> _columnsLocations = new HashMap<String, TableInfo.Column>(7);
        _columnsLocations.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsLocations.put("place_name", new TableInfo.Column("place_name", "TEXT", false, 0));
        _columnsLocations.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0));
        _columnsLocations.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0));
        _columnsLocations.put("use_count", new TableInfo.Column("use_count", "INTEGER", true, 0));
        _columnsLocations.put("is_hidden", new TableInfo.Column("is_hidden", "INTEGER", true, 0));
        _columnsLocations.put("date_added", new TableInfo.Column("date_added", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLocations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLocations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLocations = new TableInfo("locations", _columnsLocations, _foreignKeysLocations, _indicesLocations);
        final TableInfo _existingLocations = TableInfo.read(_db, "locations");
        if (! _infoLocations.equals(_existingLocations)) {
          throw new IllegalStateException("Migration didn't properly handle locations(com.example.gts.alaram.models.LocationModel).\n"
                  + " Expected:\n" + _infoLocations + "\n"
                  + " Found:\n" + _existingLocations);
        }
      }
    }, "c4fedef76edc7f9627a158c7e42ba99e", "4c79755eabd052d850dd8eed5b8e9d54");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "tasks","locations");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `tasks`");
      _db.execSQL("DELETE FROM `locations`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public TaskDao taskDao() {
    if (_taskDao != null) {
      return _taskDao;
    } else {
      synchronized(this) {
        if(_taskDao == null) {
          _taskDao = new TaskDao_Impl(this);
        }
        return _taskDao;
      }
    }
  }

  @Override
  public LocationDao locationDao() {
    if (_locationDao != null) {
      return _locationDao;
    } else {
      synchronized(this) {
        if(_locationDao == null) {
          _locationDao = new LocationDao_Impl(this);
        }
        return _locationDao;
      }
    }
  }
}
