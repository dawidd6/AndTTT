package com.dawidd6.andttt;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import static java.lang.Boolean.getBoolean;

public class ActivityMenu extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean night_mode = prefs.getBoolean("night_mode", false);
        setTheme(night_mode ? R.style.theme_dark : R.style.theme_light);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Log.d("dddd", "menu created");
    }

    public void onClickPlaySingle(View view)
    {
        Intent intent = new Intent(this, ActivitySingle.class);
        startActivity(intent);
    }

    public void onClickSettings(View view)
    {
        Intent intent = new Intent(this, ActivitySettings.class);
        startActivity(intent);
    }

    public void onClickExit(View view)
    {
        finishAffinity();
        System.exit(0);
    }
}
