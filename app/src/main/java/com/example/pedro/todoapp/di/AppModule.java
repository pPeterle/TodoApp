package com.example.pedro.todoapp.di;

import android.app.Application;
import android.content.Context;

import com.example.pedro.todoapp.ViewModelFactory;
import com.example.pedro.todoapp.data.DataRepository;
import com.example.pedro.todoapp.data.DataSource;
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
    public DataSource provideDataSource(DataRepository dataRepository) {
        return dataRepository;
    }
}
