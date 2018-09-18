package com.example.pedro.todoapp.domain.interactor.task;

import com.example.pedro.todoapp.data.entity.Task;
import com.example.pedro.todoapp.domain.CompletableUseCase;
import com.example.pedro.todoapp.domain.executor.PostExecutionThread;
import com.example.pedro.todoapp.domain.repository.TaskRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class InsertTask extends CompletableUseCase<InsertTask.Params> {

    private final TaskRepository taskRepository;

    @Inject
    public InsertTask(PostExecutionThread postExecutionThread, TaskRepository taskRepository) {
        super(postExecutionThread);
        this.taskRepository = taskRepository;
    }

    @Override
    protected Completable buildUseCaseCompletable(Params params) {
        return taskRepository.insertTask(params.task);
    }

    public static final class Params {

        private final Task task;

        public Params(Task task) {
            this.task = task;
        }
    }
}
