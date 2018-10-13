package com.github.dawidd6.andttt.animations;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class PulseAnimation extends Animation {
    private View view;

    public PulseAnimation(View view, int duration) {
        this.view = view;
        this.setDuration(duration);
        this.setRepeatCount(1);
        this.setRepeatMode(Animation.REVERSE);
        view.startAnimation(this);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float scale = 1.0f + (interpolatedTime * 0.2f);
        view.setScaleX(scale);
        view.setScaleY(scale);
    }
}
