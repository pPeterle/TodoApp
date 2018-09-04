package com.example.pedro.todoapp.tasks;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

import com.example.pedro.todoapp.data.model.Task;

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

    public Task getmTask() {
        return mTask;
    }

    public int getBackgroundColor() {
        return mBackground;
    }

    public int getTextColor() {
        return mTextColor;
    }

}
