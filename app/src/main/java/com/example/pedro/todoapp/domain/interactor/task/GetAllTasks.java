package com.example.pedro.todoapp.domain.interactor.task;

import android.graphics.Color;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.data.entity.Task;
import com.example.pedro.todoapp.domain.ObservableUseCase;
import com.example.pedro.todoapp.domain.executor.PostExecutionThread;
import com.example.pedro.todoapp.domain.repository.TaskRepository;
import com.example.pedro.todoapp.presentation.model.TaskItem;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetAllTasks extends ObservableUseCase<List<Task>, Void> {

    private final TaskRepository taskRepository;

    @Inject
    public GetAllTasks(PostExecutionThread postExecutionThread, TaskRepository taskRepository) {
        super(postExecutionThread);
        this.taskRepository = taskRepository;
    }

    @Override
    protected Observable<List<Task>> buildUseCaseObservable(Void unused) {
        return this.taskRepository.getAllTasks()
                .toObservable();
    }
}
