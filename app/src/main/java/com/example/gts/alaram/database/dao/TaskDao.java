package com.example.gts.alaram.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.gts.alaram.models.TaskModel;

/**
 * @author shilpi
 */
@Dao
public interface TaskDao {

    @Insert
    long insertTask(TaskModel task);

    @Insert
    List<Long> insertTasks(List<TaskModel> tasks);

    @Update
    void updateTask(TaskModel task);

    @Delete
    void deleteTask(TaskModel task);

    @Query("SELECT * FROM tasks")
    List<TaskModel> getAllTasks();

    @Query("SELECT * FROM  tasks WHERE id = :taskId")
    TaskModel getTaskWithId(long taskId);

    /**
     * Query to fetch the tasks not marked as done and active for today.
     */
    @Query("SELECT * FROM tasks WHERE is_done = 0 AND (end_date IS NULL OR end_date >= date(:today)) " +
            "AND start_date <= date(:today)")
    List<TaskModel> getNotDoneTasksForToday(String today);

    @Query("SELECT * FROM tasks")
    LiveData<List<TaskModel>> getAllTasksWithUpdates();

    @Update
    void updateTasks(List<TaskModel> tasks);
}
