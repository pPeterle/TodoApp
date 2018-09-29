package com.example.pedro.todoapp.tasks;

import android.animation.Animator;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pedro.todoapp.R;
import com.example.pedro.todoapp.data.entity.Task;

public class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, LottieContainer.AnimationEndListener {

    private TasksRecyclerAdapter.OnTaskCompletedListener listener;
    private TextView title;
    private LottieAnimationView animation;
    private LottieContainer lottieContainer;

    public TasksViewHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.todo_title);
        animation = itemView.findViewById(R.id.todo_animation);

        lottieContainer = new LottieContainer(animation);
        lottieContainer.setAnimationEndListener(this);
        animation.setOnClickListener(this);
    }

    public void bind(Task task, TasksRecyclerAdapter.OnTaskCompletedListener listener) {
        title.setText(task.getTitle());
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        lottieContainer.startAnimation();
    }

    @Override
    public void onAnimationEnd() {
        listener.onTaskCompleted(getAdapterPosition());
    }
}
