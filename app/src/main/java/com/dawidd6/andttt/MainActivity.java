package com.dawidd6.andttt;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    private TextView text1;
    private Resources res;
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
        res = getResources();
        drawable_o = res.getDrawable(R.drawable.o);
        drawable_x = res.getDrawable(R.drawable.x);
        xnow = true;
        clicks = 0;
        buttons = new ImageButton[9];
        int btn_ids[] = {R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6, R.id.bt7, R.id.bt8, R.id.bt9};
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
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
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