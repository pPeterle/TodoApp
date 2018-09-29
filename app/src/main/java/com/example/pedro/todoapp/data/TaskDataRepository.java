package com.example.pedro.todoapp.data;

import com.example.pedro.todoapp.data.dao.TaskDao;
import com.example.pedro.todoapp.data.entity.Task;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Singleton
public class TaskDataRepository {

    private TaskDao taskDao;

    @Inject
    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void insertTask(Task task) {
        /*return Completable.defer(() -> {
                taskDao.insertTask(task);
                return Completable.complete();
        });*/
        taskDao.insertTask(task);
    }

    public Completable updateTask(Task task) {
        return Completable.defer(() -> {
            taskDao.updateTask(task);
            return Completable.complete();
        });
    }

    public Flowable<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public Flowable<List<Task>> getTasksByTable(int tableId, boolean completed) {
        return taskDao.getTasksByTable(tableId, completed);
    }

    public Flowable<Task> getTaskById(String id) {
        return taskDao.getTaskById(id);
    }

    public Completable updateCompleted(String id, boolean completed) {
        return Completable.defer(() -> {
            taskDao.updateCompleted(id, completed);
            return Completable.complete();
        });
    }

    public Completable deleteTask(Task task) {
        return Completable.defer(() -> {
            taskDao.deleteTask(task);
            return Completable.complete();
        });
    }

    public Completable deleteTaskById(String id) {
        return Completable.defer(() -> {
            taskDao.deleteTaskById(id);
            return Completable.complete();
        });
    }
}
