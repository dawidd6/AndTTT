package com.github.dawidd6.andttt.drawings;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;

public abstract class Draw extends Animation {
    protected Canvas canvas;
    protected Paint paint;
    protected ImageView image;
    protected Bitmap bitmap;

    protected int start;
    protected int stop;

    protected int size;
    protected int color;
    protected int thickness;
    protected int duration;

    public Draw() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        canvas = new Canvas();

        setInterpolator(new AccelerateInterpolator());

        init();
    }

    public void init() {}

    public Draw setImage(ImageView image) {
        this.image = image;
        this.image.clearAnimation();
        return this;
    }

    public Draw setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        size = bitmap.getWidth();
        stop = size - thickness;
        canvas.setBitmap(bitmap);
        return this;
    }

    public Draw setColor(int color) {
        this.color = color;
        paint.setColor(color);
        return this;
    }

    public Draw setThickness(int thickness) {
        this.thickness = thickness;
        start = thickness;
        stop = size - thickness;
        paint.setStrokeWidth(thickness);
        return this;
    }

    public Draw setDuration(int duration) {
        this.duration = duration;
        setDuration((long)duration);
        return this;
    }

    public Draw execute() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        image.startAnimation(this);
        return this;
    }
}
