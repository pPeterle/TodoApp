package com.example.pedro.todoapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.pedro.todoapp.data.DataSource;
import com.example.pedro.todoapp.home.HomeViewModel;
import com.example.pedro.todoapp.list.ListViewModel;
import com.example.pedro.todoapp.note.NoteViewModel;
import com.example.pedro.todoapp.reminder.ReminderViewModel;
import com.example.pedro.todoapp.tasks.TasksViewModel;

import javax.inject.Inject;


public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private DataSource dataSource;

    @Inject
    public ViewModelFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel();
        } else if (modelClass.isAssignableFrom(ListViewModel.class)) {
            return (T) new ListViewModel();
        } else if (modelClass.isAssignableFrom(NoteViewModel.class)) {
            return (T) new NoteViewModel();
        }  else if (modelClass.isAssignableFrom(ReminderViewModel.class)) {
            return (T) new ReminderViewModel();
        } else if (modelClass.isAssignableFrom(TasksViewModel.class)) {
            return (T) new TasksViewModel(dataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
