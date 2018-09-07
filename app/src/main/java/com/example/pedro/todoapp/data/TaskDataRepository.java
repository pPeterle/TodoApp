package com.example.pedro.todoapp.data;

import com.example.pedro.todoapp.data.entity.Task;
import com.example.pedro.todoapp.data.todo.TasksDao;
import com.example.pedro.todoapp.domain.TaskRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class TaskDataRepository implements TaskRepository {

    private TasksDao tasksDao;

    @Inject
    public TaskDataRepository(TasksDao tasksDao) {
        this.tasksDao = tasksDao;
    }

    @Override
    public void insertTask(Task task) {
        tasksDao.insertTask(task);
    }

    @Override
    public void updateTask(Task task) {
        tasksDao.updateTask(task);
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
    public void updateCompleted(String id, boolean completed) {
        tasksDao.updateCompleted(id, completed);
    }

    @Override
    public void deleteTaskById(String id) {
        tasksDao.deleteTaskById(id);
    }
}
