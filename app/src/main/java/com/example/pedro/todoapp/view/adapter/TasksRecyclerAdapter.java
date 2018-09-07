package com.example.pedro.todoapp.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.presentation.model.TaskItem;

import java.util.List;

public class TasksRecyclerAdapter extends RecyclerView.Adapter<TasksViewHolder> {

    private final List<TaskItem> mTaskList;
    private OnTaskCompletedListener mListener;

    public TasksRecyclerAdapter(List<TaskItem> list) {
        this.mTaskList = list;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TasksViewHolder holder, final int position) {
        holder.bind(mTaskList.get(position), mListener);
    }

    public void setOnItemClickListener(OnTaskCompletedListener onTaskCompletedListener) {
        this.mListener = onTaskCompletedListener;
    }

    public interface OnTaskCompletedListener {
        void onTaskCompleted(int pos);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}
