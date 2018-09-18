package com.example.pedro.todoapp.view.di.module;

import com.example.pedro.todoapp.view.fragment.TasksFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract TasksFragment bindTodoFragment();
}
