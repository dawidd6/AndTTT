package com.github.dawidd6.andttt.animations;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class DarkenAnimation extends Animation {
    private View view;

    public DarkenAnimation(View view, int duration) {
        this.view = view;
        this.setDuration(duration);
        view.startAnimation(this);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float alpha = 0.8f * interpolatedTime;
        view.setAlpha(alpha);
    }
}
