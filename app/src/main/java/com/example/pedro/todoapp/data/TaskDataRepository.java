package com.example.pedro.todoapp.data;

import android.util.Log;

import com.example.pedro.todoapp.data.entity.Task;
import com.example.pedro.todoapp.data.dao.TasksDao;
import com.example.pedro.todoapp.domain.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;

@Singleton
public class TaskDataRepository implements TaskRepository {

    private TasksDao tasksDao;

    @Inject
    public TaskDataRepository(TasksDao tasksDao) {
        this.tasksDao = tasksDao;
    }

    @Override
    public Completable insertTask(Task task) {
        return Completable.defer(() -> {
                tasksDao.insertTask(task);
                return Completable.complete();
        });
    }

    @Override
    public Completable updateTask(Task task) {
        return Completable.defer(() -> {
            tasksDao.updateTask(task);
            return Completable.complete();
        });
    }

    @Override
    public Flowable<List<Task>> getAllTasks() {
        return tasksDao.getAllTasks();
    }

    @Override
    public Flowable<List<Task>> getTasksByCompleted(boolean completed) {
        return tasksDao.getTasksByCompleted(completed);
    }

    @Override
    public Flowable<Task> getTaskById(String id) {
        return tasksDao.getTaskById(id);
    }

    @Override
    public Completable updateCompleted(String id, boolean completed) {
        return Completable.defer(() -> {
            tasksDao.updateCompleted(id, completed);
            return Completable.complete();
        });
    }

    @Override
    public Completable deleteTaskById(String id) {
        return Completable.defer(() -> {
            tasksDao.deleteTaskById(id);
            return Completable.complete();
        });
    }
}
