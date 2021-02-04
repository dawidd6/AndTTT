package com.github.dawidd6.andttt.animations;

import android.view.View;
import android.view.animation.Animation;

public abstract class BaseAnimation extends Animation {
    protected View view;

    public BaseAnimation(View view, int duration) {
        this.view = view;
        setDuration(duration);
    }

    @Override
    public void start() {
        view.startAnimation(this);
    }
}
