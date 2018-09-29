package com.example.pedro.todoapp.di;

import com.example.pedro.todoapp.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();
}