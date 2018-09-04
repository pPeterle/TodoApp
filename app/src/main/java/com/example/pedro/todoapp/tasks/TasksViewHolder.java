package com.example.pedro.todoapp.tasks;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pedro.todoapp.R;

public class TasksViewHolder extends RecyclerView.ViewHolder implements Animator.AnimatorListener, View.OnClickListener {

    private TasksRecyclerAdapter.OnTaskCompletedListener listener;
    private TextView title;
    private LottieAnimationView animation;
    private LottieContainer lottieContainer;

    public TasksViewHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.todo_title);
        animation = itemView.findViewById(R.id.todo_animation);

        lottieContainer = new LottieContainer(animation);

        animation.setOnClickListener(this);
        animation.addAnimatorListener(this);
    }

    public void bind(final TaskItem taskItem, TasksRecyclerAdapter.OnTaskCompletedListener listener) {
        title.setText(taskItem.getmTask().getTitle());
        title.setTextColor(taskItem.getTextColor());
        title.setBackgroundResource(taskItem.getBackgroundColor());
        animation.setFrame(lottieContainer.getAnimationFrame(taskItem.getmTask()));
        this.listener = listener;

    }

    @Override
    public void onClick(View view) {
        animation.playAnimation();
    }

    @Override
    public void onAnimationStart(Animator animator) {
        animation.setClickable(false);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        if (listener != null) {
            listener.onTaskCompleted(getAdapterPosition());
        }
        animation.setFrame((int) animation.getMinFrame());
        animation.setClickable(true);
    }

    @Override
    public void onAnimationCancel(Animator animator) {
        animation.setClickable(true);
    }

    @Override
    public void onAnimationRepeat(Animator animator) {
    }
}