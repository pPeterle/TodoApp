package com.example.pedro.todoapp.domain.repository;

import com.example.pedro.todoapp.data.entity.Task;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface TaskRepository {

    Completable insertTask(Task task);

    Completable updateTask(Task task);

    Flowable<List<Task>> getAllTasks();

    Flowable<List<Task>> getTasksByCompleted(boolean completed);

    Flowable<Task> getTaskById(String id);

    Completable updateCompleted(String id, boolean completed);

    Completable deleteTaskById(String id);
}
