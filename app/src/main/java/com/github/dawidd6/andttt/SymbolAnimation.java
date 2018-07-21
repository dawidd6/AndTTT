package com.github.dawidd6.andttt;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class SymbolAnimation extends Animation {

    private SymbolView symbol;
    private SymbolView.MODE mode;

    public SymbolAnimation(SymbolView symbol, SymbolView.MODE mode, int color, long duration) {
        this.symbol = symbol;
        this.mode = mode;

        setDuration(duration);

        symbol.setMode(mode);
        symbol.setColor(color);
        symbol.startAnimation(this);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        switch(mode)
        {
            case CIRCLE:
                float angle = 360 * interpolatedTime;
                symbol.setCircleAngle(angle);
                break;
            case CROSS:
                float stop = 200 * interpolatedTime;
                symbol.setLineStop(stop);
                break;
        }
        symbol.invalidate();
    }
}
