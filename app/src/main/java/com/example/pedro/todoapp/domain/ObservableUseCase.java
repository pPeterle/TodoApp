package com.example.pedro.todoapp.domain;

import com.example.pedro.todoapp.domain.executor.PostExecutionThread;
import com.fernandocejas.arrow.checks.Preconditions;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class ObservableUseCase<T, Params> {

    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    public ObservableUseCase(PostExecutionThread postExecutionThread) {
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    protected abstract Observable<T> buildUseCaseObservable(Params params);

    public void execute(Params params, DisposableObserver<T> observer) {
        Preconditions.checkNotNull(observer);
        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.getScheduler());

        Disposable disposable = observable.subscribeWith(observer);
        addDisposable(disposable);
    }

    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    public void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        disposables.add(disposable);
    }
}
