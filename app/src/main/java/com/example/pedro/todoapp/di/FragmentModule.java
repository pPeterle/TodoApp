package com.example.pedro.todoapp.di;

import com.example.pedro.todoapp.tasks.TasksFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract TasksFragment bindTodoFragment();
}
