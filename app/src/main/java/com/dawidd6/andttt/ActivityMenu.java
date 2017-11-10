package com.dawidd6.andttt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

@SuppressWarnings("unused")
public class ActivityMenu extends Activity
{
    private boolean isNightModeEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        isNightModeEnabled = prefs.getBoolean("night_mode", false);
        setTheme(isNightModeEnabled ? R.style.theme_dark : R.style.theme_light);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClickPlaySingle(View view)
    {
        Intent intent = new Intent(this, ActivitySingle.class);
        intent.putExtra("night_mode" , isNightModeEnabled);
        startActivity(intent);
    }

    public void onClickSettings(View view)
    {
        Intent intent = new Intent(this, ActivitySettings.class);
        intent.putExtra("night_mode" , isNightModeEnabled);
        startActivity(intent);
    }

    public void onClickExit(View view)
    {
        finishAffinity();
        System.exit(0);
    }
}
