package com.example.pedro.todoapp.tasks;

import android.animation.Animator;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pedro.todoapp.data.entity.Task;

public class LottieContainer implements Animator.AnimatorListener {

    private LottieAnimationView lottieAnimation;
    private AnimationEndListener listener;

    public LottieContainer(LottieAnimationView lottieAnimation) {
        this.lottieAnimation = lottieAnimation;
        lottieAnimation.addAnimatorListener(this);
    }

    public void setAnimationEndListener(AnimationEndListener listener) {
        this.listener = listener;
    }

    public void startAnimation() {
        lottieAnimation.playAnimation();
    }

    @Override
    public void onAnimationStart(Animator animator) {
        lottieAnimation.setClickable(false);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        if (listener != null) {
            listener.onAnimationEnd();
        }
        lottieAnimation.setFrame((int) lottieAnimation.getMinFrame());
        lottieAnimation.setClickable(true);
    }

    @Override
    public void onAnimationCancel(Animator animator) {
        lottieAnimation.setClickable(true);
    }

    @Override
    public void onAnimationRepeat(Animator animator) {
    }

    interface AnimationEndListener {
        void onAnimationEnd();
    }

}
