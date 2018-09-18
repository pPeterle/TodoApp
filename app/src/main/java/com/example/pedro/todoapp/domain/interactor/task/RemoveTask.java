package com.example.pedro.todoapp.domain.interactor.task;

import com.example.pedro.todoapp.domain.CompletableUseCase;
import com.example.pedro.todoapp.domain.executor.PostExecutionThread;
import com.example.pedro.todoapp.domain.repository.TaskRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class RemoveTask extends CompletableUseCase<RemoveTask.Params> {

    private final TaskRepository taskRepository;

    @Inject
    public RemoveTask(PostExecutionThread postExecutionThread, TaskRepository taskRepository) {
        super(postExecutionThread);
        this.taskRepository = taskRepository;
    }

    @Override
    protected Completable buildUseCaseCompletable(RemoveTask.Params params) {
        return taskRepository.deleteTaskById(params.taskId);
    }

    public static final class Params {
        private final String taskId;

        public Params(String taskId) {
            this.taskId = taskId;
        }
    }
}
