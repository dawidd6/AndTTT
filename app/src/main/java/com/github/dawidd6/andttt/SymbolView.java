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

    private int START_ANGLE_POINT = 90;
    private int strokeWidth = 10;
    private Paint paint = new Paint();
    private RectF rect = new RectF(strokeWidth, strokeWidth, 200 - strokeWidth, 200 - strokeWidth);
    private float angle = 0;
    private float stop = 0;

    public enum MODE {CIRCLE, CROSS, CLEAR}

    private MODE mode = MODE.CLEAR;

    public SymbolView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch(mode) {
            case CIRCLE:
                canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
                break;
            case CROSS:
                canvas.drawLine(strokeWidth, strokeWidth, stop - strokeWidth, stop - strokeWidth, paint);
                canvas.drawLine(200 - strokeWidth, strokeWidth, 200 - stop + strokeWidth, stop - strokeWidth, paint);
                break;
            case CLEAR:
                setBackgroundColor(Color.TRANSPARENT);
                break;
        }
    }

    public void setCircleAngle(float angle) {
        this.angle = angle;
    }

    public void setLineStop(float stop) {
        this.stop = stop;
    }

    public void setMode(MODE mode) {
        this.mode = mode;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void clear() {
        this.mode = MODE.CLEAR;
        invalidate();
    }
}
