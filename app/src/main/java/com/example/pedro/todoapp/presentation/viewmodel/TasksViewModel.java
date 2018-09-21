package com.example.pedro.todoapp.presentation.viewmodel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import android.graphics.Color;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.domain.interactor.DefaultCompleted;
import com.example.pedro.todoapp.domain.interactor.FilterFlowable;
import com.example.pedro.todoapp.domain.interactor.task.GetAllTasks;
import com.example.pedro.todoapp.domain.interactor.task.InsertTask;
import com.example.pedro.todoapp.domain.interactor.task.RemoveTask;
import com.example.pedro.todoapp.domain.interactor.task.UpdateTaskCompleted;
import com.example.pedro.todoapp.presentation.ViewState;
import com.example.pedro.todoapp.data.entity.Task;
import com.example.pedro.todoapp.presentation.model.TaskItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;


public class TasksViewModel extends ViewModel implements LifecycleObserver {

    private final GetAllTasks getAllTasks;
    private final UpdateTaskCompleted updateTask;
    private final RemoveTask removeTask;
    private final InsertTask insertTask;

    private MutableLiveData<ViewState<List<TaskItem>>> state;
    private MutableLiveData<ViewState<List<TaskItem>>> stateCompleted;

    private FilterFlowable<Task> taskList = new FilterFlowable<>();

    @Inject
    public TasksViewModel(GetAllTasks getAllTasks,
                          InsertTask insertTask,
                          RemoveTask removeTask,
                          UpdateTaskCompleted updateTaskCompleted) {
        this.getAllTasks = getAllTasks;
        this.insertTask = insertTask;
        this.removeTask = removeTask;
        this.updateTask = updateTaskCompleted;
        state = new MutableLiveData<>();
        stateCompleted = new MutableLiveData<>();
    }

    private void fetchTasks() {
        getAllTasks.execute( null, new DisposableObserver<List<Task>>() {
            @Override
            public void onNext(List<Task> list) {
                handleOnNext(taskList.filterList(list));
            }

            @Override
            public void onError(Throwable throwable) {
                state.postValue((new ViewState<>(throwable, ViewState.Status.ERROR)));
            }

            @Override
            public void onComplete() {}
        });
    }

    private void handleOnNext(@NotNull List<Task> list) {
        List<TaskItem> tasks = new ArrayList<>();
        List<TaskItem> tasksCompleted = new ArrayList<>();

        for (Task task : list) {
            TaskItem taskItem = constructTaskItem(task);

            if (taskItem.getTask().isCompleted()) {
                tasksCompleted.add(taskItem);
            } else {
                tasks.add(taskItem);
            }
        }

        state.postValue(new ViewState<>(tasks, ViewState.Status.SUCCESS));
        stateCompleted.postValue(new ViewState<>(tasksCompleted, ViewState.Status.SUCCESS));
    }


    private TaskItem constructTaskItem(@NotNull Task task) {
        if (task.isCompleted()) {
            return new TaskItem(task, R.drawable.strike, Color.GRAY);
        } else {
            return new TaskItem(task, R.drawable.no_strike, Color.BLACK);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void fetchIfNeeded() {
        if (state.getValue() == null) {
            fetchTasks();
        }
    }

    public void updateTaskCompleted(Task task) {
        if (task.isCompleted()) {
            removeTask.execute(new DefaultCompleted(), new RemoveTask.Params(String.valueOf(task.getId())));
            taskList.itemRemoved(task);
            addTodo(task.getTitle());
        } else {
            updateTask.execute(new DefaultCompleted(), UpdateTaskCompleted.Params.forTask(String.valueOf(task.getId())));
            taskList.itemRemoved(task);
        }
    }

    public void addTodo(String text) {
        insertTask.execute(new DefaultCompleted(), new InsertTask.Params(new Task(text, false)));
    }

    public LiveData<ViewState<List<TaskItem>>> getViewState() {
        return state;
    }

    public LiveData<ViewState<List<TaskItem>>> getViewStateCompleted() {
        return stateCompleted;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        insertTask.dispose();
        getAllTasks.dispose();
        updateTask.dispose();
        removeTask.dispose();
    }
}
