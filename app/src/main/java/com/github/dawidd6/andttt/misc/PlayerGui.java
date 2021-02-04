package com.github.dawidd6.andttt.misc;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dawidd6.andttt.game.Player;

public class PlayerGui extends Player {
    private TextView scoreView;
    private TextView textView;
    private ImageView imageView;
    private Bitmap bitmap;

    public PlayerGui(ImageView imageView, TextView textView, TextView scoreView, int size) {
        super();

        this.imageView = imageView;
        this.textView = textView;
        this.scoreView = scoreView;

        this.bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_4444);
        this.imageView.setImageBitmap(this.bitmap);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void setTurn(boolean turn) {
        super.setTurn(turn);

        if(turn) {
            textView.setTypeface(null, Typeface.BOLD);
            textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        } else {
            textView.setTypeface(null, Typeface.NORMAL);
            textView.setPaintFlags(0);
        }
    }

    @Override
    public void addWin() {
        super.addWin();

        scoreView.setText(String.valueOf(getWins()));
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        textView.setText(name);
    }
}
