package com.example.pedro.todoapp.domain;

import com.example.pedro.todoapp.domain.executor.PostExecutionThread;
import com.fernandocejas.arrow.checks.Preconditions;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class CompletableUseCase<Params> {

    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    public CompletableUseCase(PostExecutionThread postExecutionThread) {
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    protected abstract Completable buildUseCaseCompletable(Params params);

    public void execute(DisposableCompletableObserver observer, Params params) {
        Preconditions.checkNotNull(observer);
        final Completable observable = this.buildUseCaseCompletable(params)
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
