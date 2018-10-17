package com.github.dawidd6.andttt.animations;

import android.view.View;
import android.view.animation.Transformation;

public class PulseAnimation extends BaseAnimation {
    public PulseAnimation(View view, int duration) {
        super(view, duration);
        this.setRepeatCount(1);
        this.setRepeatMode(BaseAnimation.REVERSE);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float scale = 1.0f + (interpolatedTime * 0.2f);
        view.setScaleX(scale);
        view.setScaleY(scale);
    }
}
