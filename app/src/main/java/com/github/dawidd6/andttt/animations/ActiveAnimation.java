package com.github.dawidd6.andttt.animations;

import android.view.View;
import android.view.animation.Transformation;

public class ActiveAnimation extends BaseAnimation {
    public ActiveAnimation(View view, int duration) {
        super(view, duration);
        start();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float alpha = 1.0f * interpolatedTime;
        view.setAlpha(alpha);
    }
}
