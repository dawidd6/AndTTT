package com.github.dawidd6.andttt.animations;

import android.view.View;
import android.view.animation.Animation;

public abstract class BaseAnimation extends Animation {
    protected View view;

    public BaseAnimation(View view, int duration) {
        if(duration == 0) {
            return;
        }

        this.view = view;
        this.setDuration(duration);
        view.startAnimation(this);
    }
}
