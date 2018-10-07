package com.example.pedro.todoapp.tasks;

import android.graphics.Color;
import android.util.Log;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.data.TableDataRepository;
import com.example.pedro.todoapp.data.TaskDataRepository;
import com.example.pedro.todoapp.data.entity.Table;
import com.example.pedro.todoapp.data.entity.Task;
import com.example.pedro.todoapp.ViewState;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class TasksViewModel extends ViewModel implements LifecycleObserver {

    private MutableLiveData<ViewState<List<Task>>> state;

    private TaskDataRepository taskDataRepository;
    private TableDataRepository tableDataRepository;

    private CompositeDisposable disposable = new CompositeDisposable();
    private int tableId;

    @Inject
    public TasksViewModel(TableDataRepository tableDataRepository, TaskDataRepository taskDataRepository) {
        this.tableDataRepository = tableDataRepository;
        this.taskDataRepository = taskDataRepository;

        tableDataRepository.insertTable(new Table("Trabalho"));
        state = new MutableLiveData<>();
    }

    private void handleOnNext(@NotNull List<Task> list) {

        List<Task> tasks = new ArrayList<>();
        List<Task> tasksCompleted = new ArrayList<>();

        for (Task task : list) {
            if (task.isCompleted()) {
                tasksCompleted.add(task);
            } else {
                tasks.add(task);
            }
        }

        tasks.sort((t1, t2) -> {
            if (t1.getId() > t2.getId()) {
                return -1;
            } else {
                return 1;
            }
        });

        List<Task> result = new ArrayList<>();

        result.addAll(tasks);
        result.addAll(result.size(), tasksCompleted);

        state.postValue(new ViewState<>(ViewState.Status.SUCCESS, result, null));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void fetchIfNeeded() {
        if (state.getValue() == null) {
            fetchTasks();
        }
    }

    private void fetchTasks() {
        disposable.add(taskDataRepository.getTasksByTable(tableId, false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleOnNext));
    }

    public void updateTaskCompleted(Task task) {
        Completable completable = taskDataRepository.updateCompleted(String.valueOf(task.getId()), true);;

        completable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void addTodo(String text) {
        taskDataRepository.insertTask(new Task(text, tableId, false));
    }

    public LiveData<ViewState<List<Task>>> getViewState() {
        return state;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
}
