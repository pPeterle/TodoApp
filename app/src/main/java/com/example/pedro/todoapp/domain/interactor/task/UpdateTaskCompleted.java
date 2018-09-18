package com.example.pedro.todoapp.domain.interactor.task;

import com.example.pedro.todoapp.domain.CompletableUseCase;
import com.example.pedro.todoapp.domain.executor.PostExecutionThread;
import com.example.pedro.todoapp.domain.repository.TaskRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class UpdateTaskCompleted extends CompletableUseCase<UpdateTaskCompleted.Params> {

    private final TaskRepository taskRepository;

    @Inject
    public UpdateTaskCompleted(PostExecutionThread postExecutionThread, TaskRepository taskRepository) {
        super(postExecutionThread);
        this.taskRepository = taskRepository;
    }

    @Override
    protected Completable buildUseCaseCompletable(Params params) {
        return taskRepository.updateCompleted(params.taskId, true);
    }

    public static final class Params {
        private final String taskId;

        public Params(String taskId) {
            this.taskId = taskId;
        }

        public static Params forTask(String userId) {
            return new Params(userId);
        }
    }

}
