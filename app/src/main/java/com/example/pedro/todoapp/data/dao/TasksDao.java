package com.example.pedro.todoapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pedro.todoapp.data.entity.Task;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM tasks")
    Flowable<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE completed = :completed")
    Flowable<List<Task>> getTasksByCompleted(boolean completed);

    @Query("SELECT * FROM Tasks WHERE id = :id")
    Flowable<Task> getTaskById(String id);

    @Query("UPDATE tasks SET completed = :completed WHERE id = :id")
    void updateCompleted(String id, boolean completed);

    @Query("DELETE FROM Tasks WHERE id = :id")
    void deleteTaskById(String id);
}