package com.example.pedro.todoapp.presentation.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.graphics.Color;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.domain.TaskRepository;
import com.example.pedro.todoapp.presentation.ViewState;
import com.example.pedro.todoapp.data.entity.Task;
import com.example.pedro.todoapp.presentation.model.TaskItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class TasksViewModel extends ViewModel implements LifecycleObserver {

    private final TaskRepository mTaskRepository;

    private MutableLiveData<ViewState<List<TaskItem>>> state;
    private MutableLiveData<ViewState<List<TaskItem>>> stateCompleted;

    private List<TaskItem> taskList = new ArrayList<>();
    private List<TaskItem> taskListCompleted = new ArrayList<>();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public TasksViewModel(TaskRepository TaskRepository) {
        this.mTaskRepository = TaskRepository;
        state = new MutableLiveData<>();
        stateCompleted = new MutableLiveData<>();
    }

    private void fetchTasks() {
        mCompositeDisposable.add(mTaskRepository.getTasksByCompleted(false)
                .map(new Function<List<Task>, List<TaskItem>>() {
                    @Override
                    public List<TaskItem> apply(List<Task> list) throws Exception {
                        List<TaskItem> taskItems = new ArrayList<>();
                        for (Task task : list) {
                            taskItems.add(constructTaskItem(task));
                        }
                        return taskItems;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TaskItem>>() {
                    @Override
                    public void accept(List<TaskItem> list) {
                        list.removeAll(taskList);
                        state.postValue(new ViewState<>(list, ViewState.Status.SUCCESS));
                        taskList.addAll(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        state.postValue((new ViewState<List<TaskItem>>(throwable, ViewState.Status.ERROR)));
                    }
                }));
    }

    private TaskItem constructTaskItem(Task task) {
        if (task.isCompleted()) {
            return new TaskItem(task, R.drawable.strike, Color.GRAY);
        } else {
            return new TaskItem(task, R.drawable.no_strike, Color.BLACK);
        }
    }

    private void fetchTasksCompleted() {
        mCompositeDisposable.add(mTaskRepository.getTasksByCompleted(true)
                .map(new Function<List<Task>, List<TaskItem>>() {
                    @Override
                    public List<TaskItem> apply(List<Task> list) throws Exception {
                        List<TaskItem> taskItems = new ArrayList<>();
                        for (Task task : list) {
                            taskItems.add(constructTaskItem(task));
                        }
                        return taskItems;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TaskItem>>() {
                    @Override
                    public void accept(List<TaskItem> list) {
                        list.removeAll(taskListCompleted);
                        stateCompleted.postValue(new ViewState<>(list, ViewState.Status.SUCCESS));
                        taskListCompleted.addAll(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        stateCompleted.postValue((new ViewState<List<TaskItem>>(throwable, ViewState.Status.ERROR)));
                    }
                }));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void fetchIfNeeded() {
        if (state.getValue() == null) {
            fetchTasks();
            fetchTasksCompleted();
        }
    }

    public void updateTaskCompleted(TaskItem taskItem) {
        if (taskItem.getmTask().isCompleted()) {
            mTaskRepository.updateCompleted(String.valueOf(taskItem.getmTask().getId()), false);
            taskListCompleted.remove(taskItem);
        } else {
            mTaskRepository.updateCompleted(String.valueOf(taskItem.getmTask().getId()), true);
            taskList.remove(taskItem);
        }
    }

    public void addTodo(String text) {
        mTaskRepository.insertTask(new Task(text, false));
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
        mCompositeDisposable.dispose();
    }
}
