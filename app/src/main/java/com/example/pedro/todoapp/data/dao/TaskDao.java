package com.example.pedro.todoapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pedro.todoapp.data.entity.Task;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM tasks")
    Flowable<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE id = :id")
    Flowable<Task> getTaskById(String id);

    @Query("SELECT * FROM tasks WHERE tableId = :tableId AND completed = :completed")
    Flowable<List<Task>> getTasksByTable(int tableId, boolean completed);

    @Query("UPDATE tasks SET completed = :completed WHERE id = :id")
    void updateCompleted(String id, boolean completed);

    @Query("DELETE FROM tasks WHERE id = :id")
    void deleteTaskById(String id);
}
