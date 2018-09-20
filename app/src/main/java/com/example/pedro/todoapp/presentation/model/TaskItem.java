package com.example.pedro.todoapp.presentation.model;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.example.pedro.todoapp.data.entity.Task;

import java.util.Objects;

public final class TaskItem {

    private Task mTask;

    @DrawableRes
    private int mBackground;

    @ColorInt
    private int mTextColor;

    public TaskItem(Task task, int background, int textColor) {
        this.mTask = task;
        this.mBackground = background;
        this.mTextColor = textColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskItem taskItem = (TaskItem) o;
        return mBackground == taskItem.mBackground &&
                mTextColor == taskItem.mTextColor &&
                Objects.equals(mTask, taskItem.mTask);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mTask, mBackground, mTextColor);
    }

    public Task getTask() {
        return mTask;
    }

    public int getBackgroundColor() {
        return mBackground;
    }

    public int getTextColor() {
        return mTextColor;
    }

}
