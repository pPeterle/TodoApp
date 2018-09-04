package com.example.pedro.todoapp;

public class ViewState<T> {

    private T data;
    private Throwable error;
    private Status status;

    public ViewState(T data, Status status) {
        this.data = data;
        this.status = status;
    }

    public ViewState(Throwable error, Status status) {
        this.error = error;
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }

    public Status getStatus() {
        return status;
    }


    public enum Status{
        SUCCESS, ERROR
    }
}
