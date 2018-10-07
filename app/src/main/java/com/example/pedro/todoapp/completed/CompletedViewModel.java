package com.example.pedro.todoapp.completed;

import com.example.pedro.todoapp.ViewState;
import com.example.pedro.todoapp.data.TableDataRepository;
import com.example.pedro.todoapp.data.TaskDataRepository;
import com.example.pedro.todoapp.data.entity.Task;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CompletedViewModel extends ViewModel implements LifecycleObserver {

    private MutableLiveData<ViewState<List<Task>>> state;
    private TaskDataRepository taskDataRepository;
    private CompositeDisposable disposable;
    private int tableId;

    @Inject
    public CompletedViewModel(TaskDataRepository taskDataRepository) {
        this.taskDataRepository = taskDataRepository;
        state = new MutableLiveData<>();
        disposable = new CompositeDisposable();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void fetchIfNeeded() {
        if (state.getValue() == null) {
            fetchTasksCompleted();
        }
    }

    private void fetchTasksCompleted() {
        disposable.add(taskDataRepository.getTasksByTable(tableId, false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(list -> state.postValue(new ViewState<>(ViewState.Status.SUCCESS, list, null)),
                        error -> state.postValue(new ViewState<>(ViewState.Status.ERROR, null, error))));
    }

    public LiveData<ViewState<List<Task>>> getState() {
        return state;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
