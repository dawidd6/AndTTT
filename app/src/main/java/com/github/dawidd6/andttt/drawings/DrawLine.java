package com.github.dawidd6.andttt.drawings;

import android.view.animation.Transformation;

import java.util.Random;

public class DrawLine extends Draw {
    private final int INCREMENT = -1;
    private final int DECREMENT = -2;

    private int startX;
    private int startY;
    private int stopX;
    private int stopY;

    public DrawLine(int startX, int startY, int stopX, int stopY, boolean random) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;

        if(new Random().nextBoolean() && random) {
            this.startX = stopX;
            this.startY = stopY;
            this.stopX = startX;
            this.stopY = startY;
        }

        this.stopX = this.stopX < this.startX ? DECREMENT :
                (this.stopX > this.startX ? INCREMENT : this.stopX);
        this.stopY = this.stopY < this.startY ? DECREMENT :
                (this.stopY > this.startY ? INCREMENT : this.stopY);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float increment = size * interpolatedTime;
        float decrement = size - increment;

        canvas.drawLine(
                startX,
                startY,
                stopX == INCREMENT ? increment :
                        (stopX == DECREMENT ? decrement : stopX),
                stopY == INCREMENT ? increment :
                        (stopY == DECREMENT ? decrement : stopY),
                paint);

        image.invalidate();
    }
}