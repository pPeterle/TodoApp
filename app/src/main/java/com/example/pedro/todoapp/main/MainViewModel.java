package com.example.pedro.todoapp.main;

import com.example.pedro.todoapp.ViewState;
import com.example.pedro.todoapp.data.TableDataRepository;
import com.example.pedro.todoapp.data.entity.Table;
import com.example.pedro.todoapp.data.entity.Task;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LifecycleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel implements LifecycleObserver {


    private final TableDataRepository tableDataRepository;

    private MutableLiveData<ViewState<List<Table>>> state;
    private CompositeDisposable disposable;

    @Inject
    public MainViewModel(TableDataRepository tableDataRepository) {
        this.tableDataRepository = tableDataRepository;
        state = new MutableLiveData<>();
        disposable = new CompositeDisposable();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void fetchIfNeeded() {
        if (state.getValue() == null) {
            fetchTables();
        }
    }

    private void fetchTables() {
        disposable.add(tableDataRepository.getAllTables()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(list -> state.postValue(new ViewState<>(ViewState.Status.SUCCESS, list, null)),
                        error -> state.postValue(new ViewState<>(ViewState.Status.ERROR, null, error))));
    }

    public LiveData<ViewState<List<Table>>> getState() {
        return state;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
