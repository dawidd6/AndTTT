package com.github.dawidd6.andttt.drawings;


import android.view.animation.Transformation;

public class DrawLine extends Draw {
    public final static int INCREMENT = -1;
    public final static int DECREMENT = -2;

    private int startX;
    private int startY;
    private int stopX;
    private int stopY;

    public DrawLine setPoints(int startX, int startY, int stopX, int stopY) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        return this;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float increment = size * interpolatedTime;
        float decrement = size - increment;

        canvas.drawLine(
                startX == -1 ? increment : (startX == -2 ? decrement : startX),
                startY == -1 ? increment : (startY == -2 ? decrement : startY),
                stopX == -1 ? increment : (stopX == -2 ? decrement : stopX),
                stopY == -1 ? increment : (stopY == -2 ? decrement : stopY),
                paint);

        image.invalidate();
    }
}