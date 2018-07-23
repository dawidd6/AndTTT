package com.github.dawidd6.andttt;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class SymbolAnimation extends Animation {

    private SymbolView symbol;

    public SymbolAnimation(SymbolView symbol) {
        this.symbol = symbol;
        symbol.startAnimation(this);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float stop = symbol.getSize() * interpolatedTime;
        symbol.setStop(stop);
        symbol.invalidate();
    }
}