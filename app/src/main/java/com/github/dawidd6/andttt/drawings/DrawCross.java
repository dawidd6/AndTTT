package com.github.dawidd6.andttt.drawings;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.Random;

public class DrawCross extends Draw {
    private boolean second;
    private boolean firstRandom;
    private boolean secondRandom;

    @Override
    public void init() {
        super.init();

        setRepeatCount(1);
        setRepeatMode(Animation.RESTART);

        second = false;
        firstRandom = new Random().nextBoolean();
        secondRandom = new Random().nextBoolean();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float increment = (stop - start) * interpolatedTime + start;
        float decrement = stop + start - increment;

        if(second)
            if(secondRandom)
                canvas.drawLine(start, stop, increment, decrement, paint);
            else
                canvas.drawLine(stop, start, decrement, increment, paint);
        else
            if(firstRandom)
                canvas.drawLine(start, start, increment, increment, paint);
            else
                canvas.drawLine(stop, stop, decrement, decrement, paint);

        image.invalidate();

        // soon restart will occur and second line will be drawn
        //
        // interpolatedTime equals 1 two times actually
        if(interpolatedTime == 1)
            second = true;
    }
}
