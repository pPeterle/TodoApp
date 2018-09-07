package com.example.pedro.todoapp.view;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pedro.todoapp.data.entity.Task;

public class LottieContainer {

    private LottieAnimationView lottieAnimation;

    public LottieContainer(LottieAnimationView lottieAnimation) {
        this.lottieAnimation = lottieAnimation;
    }

    public int getAnimationFrame(Task task) {
        if (task.isCompleted()) {
            return  (int) lottieAnimation.getMaxFrame();
        } else {
            return  (int) lottieAnimation.getMinFrame();
        }
    }
}
