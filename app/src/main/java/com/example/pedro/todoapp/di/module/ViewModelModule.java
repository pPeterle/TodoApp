package com.example.pedro.todoapp.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.pedro.todoapp.ViewModelFactory;
import com.example.pedro.todoapp.completed.CompletedViewModel;
import com.example.pedro.todoapp.main.MainViewModel;
import com.example.pedro.todoapp.tasks.TasksViewModel;
import com.example.pedro.todoapp.di.ViewModelKey;

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

    @Binds
    @IntoMap
    @ViewModelKey(CompletedViewModel.class)
    public abstract ViewModel bindCompletedViewModel(CompletedViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel binMainViewModel(MainViewModel viewModel);

}
