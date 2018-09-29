package com.example.pedro.todoapp;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;

public class ViewState<T> {

    @NotNull
    private final Status status;

    @Nullable
    private final T data;

    @Nullable
    private final Throwable error;

    public ViewState(@NotNull Status status, @Nullable T data, @Nullable Throwable error) {

        this.data = data;
        this.status = status;
        this.error = error;
    }

    @NotNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    public enum Status{
        SUCCESS, ERROR
    }
}
