package com.example.pedro.todoapp.di.module;

import android.app.Application;
import android.content.Context;

import com.example.pedro.todoapp.data.dao.TableDao;
import com.example.pedro.todoapp.data.dao.TaskDao;
import com.example.pedro.todoapp.data.db.TodoDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    public Context provideContext(Application application) {
        return application;
    }

    @Provides
    public TaskDao provideTasksDao(Context context) {
        return TodoDatabase.getInstance(context).tasksDao();
    }

    @Provides
    public TableDao provideTablesDao(Context context) {
        return TodoDatabase.getInstance(context).tableDao();
    }
}
