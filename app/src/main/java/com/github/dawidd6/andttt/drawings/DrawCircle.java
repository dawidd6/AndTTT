package com.github.dawidd6.andttt.drawings;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.animation.Transformation;

import java.util.Random;

public class DrawCircle extends Draw {
    private int startAngle;
    private int arc;

    @Override
    public void init() {
        super.init();

        arc = 360;
        startAngle = new Random().nextInt(arc);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float stopAngle = arc * interpolatedTime;

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawArc(start, start, stop, stop, startAngle, stopAngle, false, paint);

        image.invalidate();
    }
}
