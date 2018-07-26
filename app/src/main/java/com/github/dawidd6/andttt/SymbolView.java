package com.github.dawidd6.andttt;

import android.content.Context;
import android.database.CrossProcessCursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class SymbolView extends View {
    public enum Mode {
        CIRCLE,
        CROSS,
        LINE,
    }

    private Mode mode = Mode.CIRCLE;

    private Paint paint = new Paint();

    private int thickness;
    private int padding;
    private int size;
    private int clearBackground;
    private int lineStartX;
    private int lineStartY;
    private int lineStopX;
    private int lineStopY;
    private float stop;

    private boolean wantClear = false;
    
    public SymbolView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        padding = getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(wantClear) {
            setBackgroundColor(clearBackground);
            wantClear = false;
        } else {
            switch(mode) {
                case CIRCLE:
                    canvas.drawArc(thickness + padding,
                            thickness + padding,
                            size - thickness - padding,
                            size - thickness - padding,
                            90,
                            stop,
                            false,
                            paint);
                    break;
                case CROSS:
                    canvas.drawLine(thickness + padding,
                            thickness + padding,
                            stop <= thickness + padding ? thickness + padding : stop,
                            stop <= thickness + padding ? thickness + padding : stop,
                            paint);
                    canvas.drawLine(size - thickness - padding,
                            thickness + padding,
                            size - stop >= size - thickness - padding ? size - thickness - padding : size - stop,
                            stop <= thickness + padding ? thickness + padding : stop,
                            paint);
                    break;
                case LINE:
                    canvas.drawLine(lineStartX,
                            lineStartY == size ? stop : lineStartY,
                            lineStopX == size ? stop : (lineStartX == size ? size - stop : lineStopX),
                            lineStopY == size ? stop : lineStopY,
                            paint);
                    break;
            }
        }
    }

    // called only from SymbolAnimation
    public void setStop(float stop) {
        this.stop = stop;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }
    
    public void setThickness(int thickness) {
        this.thickness = thickness;
        paint.setStrokeWidth(thickness);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setLinePoints(int lineStartX, int lineStartY, int lineStopX, int lineStopY) {
        this.lineStartX = lineStartX;
        this.lineStartY = lineStartY;
        this.lineStopX = lineStopX;
        this.lineStopY = lineStopY;
    }
    
    public int getSize() {
        switch(mode) {
            case CIRCLE:
                return 360;
            case CROSS:
                return size - thickness - padding;
            case LINE:
                return size;
        }
        return size;
    }

    public void clear(int clearBackground) {
        this.clearBackground = clearBackground;
        this.wantClear = true;
        invalidate();
    }
}