package com.dawidd6.andttt;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private TextView text1;
    private Drawable drawable_x;
    private Drawable drawable_o;
    private boolean xnow;
    private int clicks;
    private ImageButton buttons[];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView)findViewById(R.id.text1);
        drawable_o = ResourcesCompat.getDrawable(getResources(), R.drawable.o, null);
        drawable_x = ResourcesCompat.getDrawable(getResources(), R.drawable.x, null);
        xnow = true;
        clicks = 0;
        buttons = new ImageButton[9];
        int btn_ids[] = {R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8};
        for(int i = 0; i < 9; i++)
            buttons[i] = (ImageButton)findViewById(btn_ids[i]);
    }

    public void restartGame(View view)
    {
        for (int i = 0; i < 9; i++)
        {
            buttons[i].setClickable(true);
            buttons[i].setImageDrawable(null);
        }
        clicks = 0;
        xnow = true;
    }

    public void onClick(View view)
    {
        String str = view.getContentDescription().toString();
        ImageButton btn = (ImageButton)findViewById(view.getId());
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        if(xnow)
        {
            btn.setImageDrawable(drawable_x);
            xnow = false;
        }
        else
        {
            btn.setImageDrawable(drawable_o);
            xnow = true;
        }
        btn.setClickable(false);
        clicks++;
        text1.setText(str);
    }
}