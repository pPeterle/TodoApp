package com.example.pedro.todoapp.view.di.module;

import android.app.Application;
import android.content.Context;

import com.example.pedro.todoapp.data.TaskDataRepository;
import com.example.pedro.todoapp.domain.executor.PostExecutionThread;
import com.example.pedro.todoapp.domain.repository.TaskRepository;
import com.example.pedro.todoapp.data.dao.TasksDao;
import com.example.pedro.todoapp.data.db.TodoDatabase;
import com.example.pedro.todoapp.presentation.UIThread;

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

    @Provides
    public PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }
}
