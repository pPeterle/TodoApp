package com.example.pedro.todoapp.view.di;

import android.app.Application;
import android.content.Context;

import com.example.pedro.todoapp.data.TaskDataRepository;
import com.example.pedro.todoapp.domain.TaskRepository;
import com.example.pedro.todoapp.data.todo.TasksDao;
import com.example.pedro.todoapp.data.todo.TodoDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    public Context provideContext(Application application){
        return application;
    }

    @Provides
    public TasksDao provideTasksDao(Context context) {
        return TodoDatabase.getInstance(context).tasksDao();
    }

    @Provides
    public TaskRepository provideDataSource(TaskDataRepository taskDataRepository) {
        return taskDataRepository;
    }
}
