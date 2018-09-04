package com.example.pedro.todoapp.data;

import com.example.pedro.todoapp.data.model.Task;

import java.util.List;

import io.reactivex.Flowable;

public interface DataSource {

    void insertTask(Task task);

    void updateTask(Task task);

    Flowable<List<Task>> getAllTasks();

    Flowable<List<Task>> getTasksByCompleted(boolean completed);

    Flowable<Task> getTaskById(String id);

    void updateCompleted(String id, boolean completed);

    void deleteTaskById(String id);
}
