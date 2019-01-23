package com.example.gts.alaram.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.gts.alaram.database.converters.DateConverter;
import com.example.gts.alaram.database.converters.TimeConverter;
import com.example.gts.alaram.models.TaskModel;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

@SuppressWarnings("unchecked")
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTaskModel;

  private final TimeConverter __timeConverter = new TimeConverter();

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfTaskModel;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfTaskModel;

  public TaskDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTaskModel = new EntityInsertionAdapter<TaskModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `tasks`(`id`,`task_name`,`location_id`,`image_uri`,`is_done`,`is_alarm_set`,`reminder_range`,`note`,`start_time`,`end_time`,`start_date`,`end_date`,`next_start_date`,`repeat_type`,`movement_type`,`activity_type`,`last_distance`,`last_triggered`,`snoozed_at`,`date_added`,`repeat_code`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getTaskName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTaskName());
        }
        stmt.bindLong(3, value.getLocationId());
        if (value.getImageUri() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getImageUri());
        }
        stmt.bindLong(5, value.getIsDone());
        stmt.bindLong(6, value.getIsAlarmSet());
        stmt.bindLong(7, value.getReminderRange());
        if (value.getNote() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getNote());
        }
        final String _tmp;
        _tmp = __timeConverter.localTimeToString(value.getStartTime());
        if (_tmp == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = __timeConverter.localTimeToString(value.getEndTime());
        if (_tmp_1 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = __dateConverter.dateToString(value.getStartDate());
        if (_tmp_2 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, _tmp_2);
        }
        final String _tmp_3;
        _tmp_3 = __dateConverter.dateToString(value.getEndDate());
        if (_tmp_3 == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, _tmp_3);
        }
        final String _tmp_4;
        _tmp_4 = __dateConverter.dateToString(value.getNextStartDate());
        if (_tmp_4 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, _tmp_4);
        }
        stmt.bindLong(14, value.getRepeatType());
        stmt.bindLong(15, value.getMovementType());
        stmt.bindLong(16, value.getActivityType());
        stmt.bindDouble(17, value.getLastDistance());
        final String _tmp_5;
        _tmp_5 = __dateConverter.dateToString(value.getLastTriggered());
        if (_tmp_5 == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, _tmp_5);
        }
        if (value.getSnoozedAt() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindLong(19, value.getSnoozedAt());
        }
        final String _tmp_6;
        _tmp_6 = __dateConverter.dateToString(value.getDateAdded());
        if (_tmp_6 == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, _tmp_6);
        }
        stmt.bindLong(21, value.getRepeatCode());
      }
    };
    this.__deletionAdapterOfTaskModel = new EntityDeletionOrUpdateAdapter<TaskModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `tasks` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskModel value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfTaskModel = new EntityDeletionOrUpdateAdapter<TaskModel>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `tasks` SET `id` = ?,`task_name` = ?,`location_id` = ?,`image_uri` = ?,`is_done` = ?,`is_alarm_set` = ?,`reminder_range` = ?,`note` = ?,`start_time` = ?,`end_time` = ?,`start_date` = ?,`end_date` = ?,`next_start_date` = ?,`repeat_type` = ?,`movement_type` = ?,`activity_type` = ?,`last_distance` = ?,`last_triggered` = ?,`snoozed_at` = ?,`date_added` = ?,`repeat_code` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TaskModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getTaskName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTaskName());
        }
        stmt.bindLong(3, value.getLocationId());
        if (value.getImageUri() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getImageUri());
        }
        stmt.bindLong(5, value.getIsDone());
        stmt.bindLong(6, value.getIsAlarmSet());
        stmt.bindLong(7, value.getReminderRange());
        if (value.getNote() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getNote());
        }
        final String _tmp;
        _tmp = __timeConverter.localTimeToString(value.getStartTime());
        if (_tmp == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = __timeConverter.localTimeToString(value.getEndTime());
        if (_tmp_1 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = __dateConverter.dateToString(value.getStartDate());
        if (_tmp_2 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, _tmp_2);
        }
        final String _tmp_3;
        _tmp_3 = __dateConverter.dateToString(value.getEndDate());
        if (_tmp_3 == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, _tmp_3);
        }
        final String _tmp_4;
        _tmp_4 = __dateConverter.dateToString(value.getNextStartDate());
        if (_tmp_4 == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, _tmp_4);
        }
        stmt.bindLong(14, value.getRepeatType());
        stmt.bindLong(15, value.getMovementType());
        stmt.bindLong(16, value.getActivityType());
        stmt.bindDouble(17, value.getLastDistance());
        final String _tmp_5;
        _tmp_5 = __dateConverter.dateToString(value.getLastTriggered());
        if (_tmp_5 == null) {
          stmt.bindNull(18);
        } else {
          stmt.bindString(18, _tmp_5);
        }
        if (value.getSnoozedAt() == null) {
          stmt.bindNull(19);
        } else {
          stmt.bindLong(19, value.getSnoozedAt());
        }
        final String _tmp_6;
        _tmp_6 = __dateConverter.dateToString(value.getDateAdded());
        if (_tmp_6 == null) {
          stmt.bindNull(20);
        } else {
          stmt.bindString(20, _tmp_6);
        }
        stmt.bindLong(21, value.getRepeatCode());
        stmt.bindLong(22, value.getId());
      }
    };
  }

  @Override
  public long insertTask(TaskModel task) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfTaskModel.insertAndReturnId(task);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Long> insertTasks(List<TaskModel> tasks) {
    __db.beginTransaction();
    try {
      List<Long> _result = __insertionAdapterOfTaskModel.insertAndReturnIdsList(tasks);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteTask(TaskModel task) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfTaskModel.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateTask(TaskModel task) {
    __db.beginTransaction();
    try {
      __updateAdapterOfTaskModel.handle(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateTasks(List<TaskModel> tasks) {
    __db.beginTransaction();
    try {
      __updateAdapterOfTaskModel.handleMultiple(tasks);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<TaskModel> getAllTasks() {
    final String _sql = "SELECT * FROM tasks";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTaskName = _cursor.getColumnIndexOrThrow("task_name");
      final int _cursorIndexOfLocationId = _cursor.getColumnIndexOrThrow("location_id");
      final int _cursorIndexOfImageUri = _cursor.getColumnIndexOrThrow("image_uri");
      final int _cursorIndexOfIsDone = _cursor.getColumnIndexOrThrow("is_done");
      final int _cursorIndexOfIsAlarmSet = _cursor.getColumnIndexOrThrow("is_alarm_set");
      final int _cursorIndexOfReminderRange = _cursor.getColumnIndexOrThrow("reminder_range");
      final int _cursorIndexOfNote = _cursor.getColumnIndexOrThrow("note");
      final int _cursorIndexOfStartTime = _cursor.getColumnIndexOrThrow("start_time");
      final int _cursorIndexOfEndTime = _cursor.getColumnIndexOrThrow("end_time");
      final int _cursorIndexOfStartDate = _cursor.getColumnIndexOrThrow("start_date");
      final int _cursorIndexOfEndDate = _cursor.getColumnIndexOrThrow("end_date");
      final int _cursorIndexOfNextStartDate = _cursor.getColumnIndexOrThrow("next_start_date");
      final int _cursorIndexOfRepeatType = _cursor.getColumnIndexOrThrow("repeat_type");
      final int _cursorIndexOfMovementType = _cursor.getColumnIndexOrThrow("movement_type");
      final int _cursorIndexOfActivityType = _cursor.getColumnIndexOrThrow("activity_type");
      final int _cursorIndexOfLastDistance = _cursor.getColumnIndexOrThrow("last_distance");
      final int _cursorIndexOfLastTriggered = _cursor.getColumnIndexOrThrow("last_triggered");
      final int _cursorIndexOfSnoozedAt = _cursor.getColumnIndexOrThrow("snoozed_at");
      final int _cursorIndexOfDateAdded = _cursor.getColumnIndexOrThrow("date_added");
      final int _cursorIndexOfRepeatCode = _cursor.getColumnIndexOrThrow("repeat_code");
      final List<TaskModel> _result = new ArrayList<TaskModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TaskModel _item;
        _item = new TaskModel();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTaskName;
        _tmpTaskName = _cursor.getString(_cursorIndexOfTaskName);
        _item.setTaskName(_tmpTaskName);
        final long _tmpLocationId;
        _tmpLocationId = _cursor.getLong(_cursorIndexOfLocationId);
        _item.setLocationId(_tmpLocationId);
        final String _tmpImageUri;
        _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
        _item.setImageUri(_tmpImageUri);
        final int _tmpIsDone;
        _tmpIsDone = _cursor.getInt(_cursorIndexOfIsDone);
        _item.setIsDone(_tmpIsDone);
        final int _tmpIsAlarmSet;
        _tmpIsAlarmSet = _cursor.getInt(_cursorIndexOfIsAlarmSet);
        _item.setIsAlarmSet(_tmpIsAlarmSet);
        final int _tmpReminderRange;
        _tmpReminderRange = _cursor.getInt(_cursorIndexOfReminderRange);
        _item.setReminderRange(_tmpReminderRange);
        final String _tmpNote;
        _tmpNote = _cursor.getString(_cursorIndexOfNote);
        _item.setNote(_tmpNote);
        final LocalTime _tmpStartTime;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfStartTime);
        _tmpStartTime = __timeConverter.stringToLocalTime(_tmp);
        _item.setStartTime(_tmpStartTime);
        final LocalTime _tmpEndTime;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfEndTime);
        _tmpEndTime = __timeConverter.stringToLocalTime(_tmp_1);
        _item.setEndTime(_tmpEndTime);
        final LocalDate _tmpStartDate;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfStartDate);
        _tmpStartDate = __dateConverter.stringToDate(_tmp_2);
        _item.setStartDate(_tmpStartDate);
        final LocalDate _tmpEndDate;
        final String _tmp_3;
        _tmp_3 = _cursor.getString(_cursorIndexOfEndDate);
        _tmpEndDate = __dateConverter.stringToDate(_tmp_3);
        _item.setEndDate(_tmpEndDate);
        final LocalDate _tmpNextStartDate;
        final String _tmp_4;
        _tmp_4 = _cursor.getString(_cursorIndexOfNextStartDate);
        _tmpNextStartDate = __dateConverter.stringToDate(_tmp_4);
        _item.setNextStartDate(_tmpNextStartDate);
        final int _tmpRepeatType;
        _tmpRepeatType = _cursor.getInt(_cursorIndexOfRepeatType);
        _item.setRepeatType(_tmpRepeatType);
        final int _tmpMovementType;
        _tmpMovementType = _cursor.getInt(_cursorIndexOfMovementType);
        _item.setMovementType(_tmpMovementType);
        final int _tmpActivityType;
        _tmpActivityType = _cursor.getInt(_cursorIndexOfActivityType);
        _item.setActivityType(_tmpActivityType);
        final float _tmpLastDistance;
        _tmpLastDistance = _cursor.getFloat(_cursorIndexOfLastDistance);
        _item.setLastDistance(_tmpLastDistance);
        final LocalDate _tmpLastTriggered;
        final String _tmp_5;
        _tmp_5 = _cursor.getString(_cursorIndexOfLastTriggered);
        _tmpLastTriggered = __dateConverter.stringToDate(_tmp_5);
        _item.setLastTriggered(_tmpLastTriggered);
        final Long _tmpSnoozedAt;
        if (_cursor.isNull(_cursorIndexOfSnoozedAt)) {
          _tmpSnoozedAt = null;
        } else {
          _tmpSnoozedAt = _cursor.getLong(_cursorIndexOfSnoozedAt);
        }
        _item.setSnoozedAt(_tmpSnoozedAt);
        final LocalDate _tmpDateAdded;
        final String _tmp_6;
        _tmp_6 = _cursor.getString(_cursorIndexOfDateAdded);
        _tmpDateAdded = __dateConverter.stringToDate(_tmp_6);
        _item.setDateAdded(_tmpDateAdded);
        final int _tmpRepeatCode;
        _tmpRepeatCode = _cursor.getInt(_cursorIndexOfRepeatCode);
        _item.setRepeatCode(_tmpRepeatCode);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public TaskModel getTaskWithId(long taskId) {
    final String _sql = "SELECT * FROM  tasks WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, taskId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTaskName = _cursor.getColumnIndexOrThrow("task_name");
      final int _cursorIndexOfLocationId = _cursor.getColumnIndexOrThrow("location_id");
      final int _cursorIndexOfImageUri = _cursor.getColumnIndexOrThrow("image_uri");
      final int _cursorIndexOfIsDone = _cursor.getColumnIndexOrThrow("is_done");
      final int _cursorIndexOfIsAlarmSet = _cursor.getColumnIndexOrThrow("is_alarm_set");
      final int _cursorIndexOfReminderRange = _cursor.getColumnIndexOrThrow("reminder_range");
      final int _cursorIndexOfNote = _cursor.getColumnIndexOrThrow("note");
      final int _cursorIndexOfStartTime = _cursor.getColumnIndexOrThrow("start_time");
      final int _cursorIndexOfEndTime = _cursor.getColumnIndexOrThrow("end_time");
      final int _cursorIndexOfStartDate = _cursor.getColumnIndexOrThrow("start_date");
      final int _cursorIndexOfEndDate = _cursor.getColumnIndexOrThrow("end_date");
      final int _cursorIndexOfNextStartDate = _cursor.getColumnIndexOrThrow("next_start_date");
      final int _cursorIndexOfRepeatType = _cursor.getColumnIndexOrThrow("repeat_type");
      final int _cursorIndexOfMovementType = _cursor.getColumnIndexOrThrow("movement_type");
      final int _cursorIndexOfActivityType = _cursor.getColumnIndexOrThrow("activity_type");
      final int _cursorIndexOfLastDistance = _cursor.getColumnIndexOrThrow("last_distance");
      final int _cursorIndexOfLastTriggered = _cursor.getColumnIndexOrThrow("last_triggered");
      final int _cursorIndexOfSnoozedAt = _cursor.getColumnIndexOrThrow("snoozed_at");
      final int _cursorIndexOfDateAdded = _cursor.getColumnIndexOrThrow("date_added");
      final int _cursorIndexOfRepeatCode = _cursor.getColumnIndexOrThrow("repeat_code");
      final TaskModel _result;
      if(_cursor.moveToFirst()) {
        _result = new TaskModel();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpTaskName;
        _tmpTaskName = _cursor.getString(_cursorIndexOfTaskName);
        _result.setTaskName(_tmpTaskName);
        final long _tmpLocationId;
        _tmpLocationId = _cursor.getLong(_cursorIndexOfLocationId);
        _result.setLocationId(_tmpLocationId);
        final String _tmpImageUri;
        _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
        _result.setImageUri(_tmpImageUri);
        final int _tmpIsDone;
        _tmpIsDone = _cursor.getInt(_cursorIndexOfIsDone);
        _result.setIsDone(_tmpIsDone);
        final int _tmpIsAlarmSet;
        _tmpIsAlarmSet = _cursor.getInt(_cursorIndexOfIsAlarmSet);
        _result.setIsAlarmSet(_tmpIsAlarmSet);
        final int _tmpReminderRange;
        _tmpReminderRange = _cursor.getInt(_cursorIndexOfReminderRange);
        _result.setReminderRange(_tmpReminderRange);
        final String _tmpNote;
        _tmpNote = _cursor.getString(_cursorIndexOfNote);
        _result.setNote(_tmpNote);
        final LocalTime _tmpStartTime;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfStartTime);
        _tmpStartTime = __timeConverter.stringToLocalTime(_tmp);
        _result.setStartTime(_tmpStartTime);
        final LocalTime _tmpEndTime;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfEndTime);
        _tmpEndTime = __timeConverter.stringToLocalTime(_tmp_1);
        _result.setEndTime(_tmpEndTime);
        final LocalDate _tmpStartDate;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfStartDate);
        _tmpStartDate = __dateConverter.stringToDate(_tmp_2);
        _result.setStartDate(_tmpStartDate);
        final LocalDate _tmpEndDate;
        final String _tmp_3;
        _tmp_3 = _cursor.getString(_cursorIndexOfEndDate);
        _tmpEndDate = __dateConverter.stringToDate(_tmp_3);
        _result.setEndDate(_tmpEndDate);
        final LocalDate _tmpNextStartDate;
        final String _tmp_4;
        _tmp_4 = _cursor.getString(_cursorIndexOfNextStartDate);
        _tmpNextStartDate = __dateConverter.stringToDate(_tmp_4);
        _result.setNextStartDate(_tmpNextStartDate);
        final int _tmpRepeatType;
        _tmpRepeatType = _cursor.getInt(_cursorIndexOfRepeatType);
        _result.setRepeatType(_tmpRepeatType);
        final int _tmpMovementType;
        _tmpMovementType = _cursor.getInt(_cursorIndexOfMovementType);
        _result.setMovementType(_tmpMovementType);
        final int _tmpActivityType;
        _tmpActivityType = _cursor.getInt(_cursorIndexOfActivityType);
        _result.setActivityType(_tmpActivityType);
        final float _tmpLastDistance;
        _tmpLastDistance = _cursor.getFloat(_cursorIndexOfLastDistance);
        _result.setLastDistance(_tmpLastDistance);
        final LocalDate _tmpLastTriggered;
        final String _tmp_5;
        _tmp_5 = _cursor.getString(_cursorIndexOfLastTriggered);
        _tmpLastTriggered = __dateConverter.stringToDate(_tmp_5);
        _result.setLastTriggered(_tmpLastTriggered);
        final Long _tmpSnoozedAt;
        if (_cursor.isNull(_cursorIndexOfSnoozedAt)) {
          _tmpSnoozedAt = null;
        } else {
          _tmpSnoozedAt = _cursor.getLong(_cursorIndexOfSnoozedAt);
        }
        _result.setSnoozedAt(_tmpSnoozedAt);
        final LocalDate _tmpDateAdded;
        final String _tmp_6;
        _tmp_6 = _cursor.getString(_cursorIndexOfDateAdded);
        _tmpDateAdded = __dateConverter.stringToDate(_tmp_6);
        _result.setDateAdded(_tmpDateAdded);
        final int _tmpRepeatCode;
        _tmpRepeatCode = _cursor.getInt(_cursorIndexOfRepeatCode);
        _result.setRepeatCode(_tmpRepeatCode);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<TaskModel> getNotDoneTasksForToday(String today) {
    final String _sql = "SELECT * FROM tasks WHERE is_done = 0 AND (end_date IS NULL OR end_date >= date(?)) AND start_date <= date(?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (today == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, today);
    }
    _argIndex = 2;
    if (today == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, today);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTaskName = _cursor.getColumnIndexOrThrow("task_name");
      final int _cursorIndexOfLocationId = _cursor.getColumnIndexOrThrow("location_id");
      final int _cursorIndexOfImageUri = _cursor.getColumnIndexOrThrow("image_uri");
      final int _cursorIndexOfIsDone = _cursor.getColumnIndexOrThrow("is_done");
      final int _cursorIndexOfIsAlarmSet = _cursor.getColumnIndexOrThrow("is_alarm_set");
      final int _cursorIndexOfReminderRange = _cursor.getColumnIndexOrThrow("reminder_range");
      final int _cursorIndexOfNote = _cursor.getColumnIndexOrThrow("note");
      final int _cursorIndexOfStartTime = _cursor.getColumnIndexOrThrow("start_time");
      final int _cursorIndexOfEndTime = _cursor.getColumnIndexOrThrow("end_time");
      final int _cursorIndexOfStartDate = _cursor.getColumnIndexOrThrow("start_date");
      final int _cursorIndexOfEndDate = _cursor.getColumnIndexOrThrow("end_date");
      final int _cursorIndexOfNextStartDate = _cursor.getColumnIndexOrThrow("next_start_date");
      final int _cursorIndexOfRepeatType = _cursor.getColumnIndexOrThrow("repeat_type");
      final int _cursorIndexOfMovementType = _cursor.getColumnIndexOrThrow("movement_type");
      final int _cursorIndexOfActivityType = _cursor.getColumnIndexOrThrow("activity_type");
      final int _cursorIndexOfLastDistance = _cursor.getColumnIndexOrThrow("last_distance");
      final int _cursorIndexOfLastTriggered = _cursor.getColumnIndexOrThrow("last_triggered");
      final int _cursorIndexOfSnoozedAt = _cursor.getColumnIndexOrThrow("snoozed_at");
      final int _cursorIndexOfDateAdded = _cursor.getColumnIndexOrThrow("date_added");
      final int _cursorIndexOfRepeatCode = _cursor.getColumnIndexOrThrow("repeat_code");
      final List<TaskModel> _result = new ArrayList<TaskModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TaskModel _item;
        _item = new TaskModel();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTaskName;
        _tmpTaskName = _cursor.getString(_cursorIndexOfTaskName);
        _item.setTaskName(_tmpTaskName);
        final long _tmpLocationId;
        _tmpLocationId = _cursor.getLong(_cursorIndexOfLocationId);
        _item.setLocationId(_tmpLocationId);
        final String _tmpImageUri;
        _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
        _item.setImageUri(_tmpImageUri);
        final int _tmpIsDone;
        _tmpIsDone = _cursor.getInt(_cursorIndexOfIsDone);
        _item.setIsDone(_tmpIsDone);
        final int _tmpIsAlarmSet;
        _tmpIsAlarmSet = _cursor.getInt(_cursorIndexOfIsAlarmSet);
        _item.setIsAlarmSet(_tmpIsAlarmSet);
        final int _tmpReminderRange;
        _tmpReminderRange = _cursor.getInt(_cursorIndexOfReminderRange);
        _item.setReminderRange(_tmpReminderRange);
        final String _tmpNote;
        _tmpNote = _cursor.getString(_cursorIndexOfNote);
        _item.setNote(_tmpNote);
        final LocalTime _tmpStartTime;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfStartTime);
        _tmpStartTime = __timeConverter.stringToLocalTime(_tmp);
        _item.setStartTime(_tmpStartTime);
        final LocalTime _tmpEndTime;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfEndTime);
        _tmpEndTime = __timeConverter.stringToLocalTime(_tmp_1);
        _item.setEndTime(_tmpEndTime);
        final LocalDate _tmpStartDate;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfStartDate);
        _tmpStartDate = __dateConverter.stringToDate(_tmp_2);
        _item.setStartDate(_tmpStartDate);
        final LocalDate _tmpEndDate;
        final String _tmp_3;
        _tmp_3 = _cursor.getString(_cursorIndexOfEndDate);
        _tmpEndDate = __dateConverter.stringToDate(_tmp_3);
        _item.setEndDate(_tmpEndDate);
        final LocalDate _tmpNextStartDate;
        final String _tmp_4;
        _tmp_4 = _cursor.getString(_cursorIndexOfNextStartDate);
        _tmpNextStartDate = __dateConverter.stringToDate(_tmp_4);
        _item.setNextStartDate(_tmpNextStartDate);
        final int _tmpRepeatType;
        _tmpRepeatType = _cursor.getInt(_cursorIndexOfRepeatType);
        _item.setRepeatType(_tmpRepeatType);
        final int _tmpMovementType;
        _tmpMovementType = _cursor.getInt(_cursorIndexOfMovementType);
        _item.setMovementType(_tmpMovementType);
        final int _tmpActivityType;
        _tmpActivityType = _cursor.getInt(_cursorIndexOfActivityType);
        _item.setActivityType(_tmpActivityType);
        final float _tmpLastDistance;
        _tmpLastDistance = _cursor.getFloat(_cursorIndexOfLastDistance);
        _item.setLastDistance(_tmpLastDistance);
        final LocalDate _tmpLastTriggered;
        final String _tmp_5;
        _tmp_5 = _cursor.getString(_cursorIndexOfLastTriggered);
        _tmpLastTriggered = __dateConverter.stringToDate(_tmp_5);
        _item.setLastTriggered(_tmpLastTriggered);
        final Long _tmpSnoozedAt;
        if (_cursor.isNull(_cursorIndexOfSnoozedAt)) {
          _tmpSnoozedAt = null;
        } else {
          _tmpSnoozedAt = _cursor.getLong(_cursorIndexOfSnoozedAt);
        }
        _item.setSnoozedAt(_tmpSnoozedAt);
        final LocalDate _tmpDateAdded;
        final String _tmp_6;
        _tmp_6 = _cursor.getString(_cursorIndexOfDateAdded);
        _tmpDateAdded = __dateConverter.stringToDate(_tmp_6);
        _item.setDateAdded(_tmpDateAdded);
        final int _tmpRepeatCode;
        _tmpRepeatCode = _cursor.getInt(_cursorIndexOfRepeatCode);
        _item.setRepeatCode(_tmpRepeatCode);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<TaskModel>> getAllTasksWithUpdates() {
    final String _sql = "SELECT * FROM tasks";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<TaskModel>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<TaskModel> compute() {
        if (_observer == null) {
          _observer = new Observer("tasks") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTaskName = _cursor.getColumnIndexOrThrow("task_name");
          final int _cursorIndexOfLocationId = _cursor.getColumnIndexOrThrow("location_id");
          final int _cursorIndexOfImageUri = _cursor.getColumnIndexOrThrow("image_uri");
          final int _cursorIndexOfIsDone = _cursor.getColumnIndexOrThrow("is_done");
          final int _cursorIndexOfIsAlarmSet = _cursor.getColumnIndexOrThrow("is_alarm_set");
          final int _cursorIndexOfReminderRange = _cursor.getColumnIndexOrThrow("reminder_range");
          final int _cursorIndexOfNote = _cursor.getColumnIndexOrThrow("note");
          final int _cursorIndexOfStartTime = _cursor.getColumnIndexOrThrow("start_time");
          final int _cursorIndexOfEndTime = _cursor.getColumnIndexOrThrow("end_time");
          final int _cursorIndexOfStartDate = _cursor.getColumnIndexOrThrow("start_date");
          final int _cursorIndexOfEndDate = _cursor.getColumnIndexOrThrow("end_date");
          final int _cursorIndexOfNextStartDate = _cursor.getColumnIndexOrThrow("next_start_date");
          final int _cursorIndexOfRepeatType = _cursor.getColumnIndexOrThrow("repeat_type");
          final int _cursorIndexOfMovementType = _cursor.getColumnIndexOrThrow("movement_type");
          final int _cursorIndexOfActivityType = _cursor.getColumnIndexOrThrow("activity_type");
          final int _cursorIndexOfLastDistance = _cursor.getColumnIndexOrThrow("last_distance");
          final int _cursorIndexOfLastTriggered = _cursor.getColumnIndexOrThrow("last_triggered");
          final int _cursorIndexOfSnoozedAt = _cursor.getColumnIndexOrThrow("snoozed_at");
          final int _cursorIndexOfDateAdded = _cursor.getColumnIndexOrThrow("date_added");
          final int _cursorIndexOfRepeatCode = _cursor.getColumnIndexOrThrow("repeat_code");
          final List<TaskModel> _result = new ArrayList<TaskModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TaskModel _item;
            _item = new TaskModel();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTaskName;
            _tmpTaskName = _cursor.getString(_cursorIndexOfTaskName);
            _item.setTaskName(_tmpTaskName);
            final long _tmpLocationId;
            _tmpLocationId = _cursor.getLong(_cursorIndexOfLocationId);
            _item.setLocationId(_tmpLocationId);
            final String _tmpImageUri;
            _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
            _item.setImageUri(_tmpImageUri);
            final int _tmpIsDone;
            _tmpIsDone = _cursor.getInt(_cursorIndexOfIsDone);
            _item.setIsDone(_tmpIsDone);
            final int _tmpIsAlarmSet;
            _tmpIsAlarmSet = _cursor.getInt(_cursorIndexOfIsAlarmSet);
            _item.setIsAlarmSet(_tmpIsAlarmSet);
            final int _tmpReminderRange;
            _tmpReminderRange = _cursor.getInt(_cursorIndexOfReminderRange);
            _item.setReminderRange(_tmpReminderRange);
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            _item.setNote(_tmpNote);
            final LocalTime _tmpStartTime;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfStartTime);
            _tmpStartTime = __timeConverter.stringToLocalTime(_tmp);
            _item.setStartTime(_tmpStartTime);
            final LocalTime _tmpEndTime;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfEndTime);
            _tmpEndTime = __timeConverter.stringToLocalTime(_tmp_1);
            _item.setEndTime(_tmpEndTime);
            final LocalDate _tmpStartDate;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfStartDate);
            _tmpStartDate = __dateConverter.stringToDate(_tmp_2);
            _item.setStartDate(_tmpStartDate);
            final LocalDate _tmpEndDate;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfEndDate);
            _tmpEndDate = __dateConverter.stringToDate(_tmp_3);
            _item.setEndDate(_tmpEndDate);
            final LocalDate _tmpNextStartDate;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfNextStartDate);
            _tmpNextStartDate = __dateConverter.stringToDate(_tmp_4);
            _item.setNextStartDate(_tmpNextStartDate);
            final int _tmpRepeatType;
            _tmpRepeatType = _cursor.getInt(_cursorIndexOfRepeatType);
            _item.setRepeatType(_tmpRepeatType);
            final int _tmpMovementType;
            _tmpMovementType = _cursor.getInt(_cursorIndexOfMovementType);
            _item.setMovementType(_tmpMovementType);
            final int _tmpActivityType;
            _tmpActivityType = _cursor.getInt(_cursorIndexOfActivityType);
            _item.setActivityType(_tmpActivityType);
            final float _tmpLastDistance;
            _tmpLastDistance = _cursor.getFloat(_cursorIndexOfLastDistance);
            _item.setLastDistance(_tmpLastDistance);
            final LocalDate _tmpLastTriggered;
            final String _tmp_5;
            _tmp_5 = _cursor.getString(_cursorIndexOfLastTriggered);
            _tmpLastTriggered = __dateConverter.stringToDate(_tmp_5);
            _item.setLastTriggered(_tmpLastTriggered);
            final Long _tmpSnoozedAt;
            if (_cursor.isNull(_cursorIndexOfSnoozedAt)) {
              _tmpSnoozedAt = null;
            } else {
              _tmpSnoozedAt = _cursor.getLong(_cursorIndexOfSnoozedAt);
            }
            _item.setSnoozedAt(_tmpSnoozedAt);
            final LocalDate _tmpDateAdded;
            final String _tmp_6;
            _tmp_6 = _cursor.getString(_cursorIndexOfDateAdded);
            _tmpDateAdded = __dateConverter.stringToDate(_tmp_6);
            _item.setDateAdded(_tmpDateAdded);
            final int _tmpRepeatCode;
            _tmpRepeatCode = _cursor.getInt(_cursorIndexOfRepeatCode);
            _item.setRepeatCode(_tmpRepeatCode);
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
    }.getLiveData();
  }
}
