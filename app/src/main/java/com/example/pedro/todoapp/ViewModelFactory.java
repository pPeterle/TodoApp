package com.example.pedro.todoapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.pedro.todoapp.domain.TaskRepository;
import com.example.pedro.todoapp.presentation.viewmodel.HomeViewModel;
import com.example.pedro.todoapp.presentation.viewmodel.ListViewModel;
import com.example.pedro.todoapp.presentation.viewmodel.NoteViewModel;
import com.example.pedro.todoapp.presentation.viewmodel.ReminderViewModel;
import com.example.pedro.todoapp.presentation.viewmodel.TasksViewModel;

import javax.inject.Inject;


public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private TaskRepository taskRepository;

    @Inject
    public ViewModelFactory(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
            return (T) new TasksViewModel(taskRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
