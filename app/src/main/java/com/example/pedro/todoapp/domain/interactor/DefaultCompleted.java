package com.example.pedro.todoapp.domain.interactor;

import io.reactivex.observers.DisposableCompletableObserver;

public class DefaultCompleted extends DisposableCompletableObserver {
    @Override
    public void onComplete() {
        //empty
    }

    @Override
    public void onError(Throwable e) {
        //empty
    }
}
