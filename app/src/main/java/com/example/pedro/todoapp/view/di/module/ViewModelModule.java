package com.example.pedro.todoapp.view.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.pedro.todoapp.ViewModelFactory;
import com.example.pedro.todoapp.presentation.viewmodel.TasksViewModel;
import com.example.pedro.todoapp.view.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(TasksViewModel.class)
    public abstract ViewModel bindListViewModel(TasksViewModel viewModel);
}
