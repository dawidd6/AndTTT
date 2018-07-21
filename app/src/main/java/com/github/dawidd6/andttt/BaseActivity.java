package com.github.dawidd6.andttt;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;

public abstract class BaseActivity extends Activity {
    protected boolean isNightModeEnabled;
    protected boolean isAnimationEnabled;
    protected boolean isStatusBarEnabled;
    protected int animation_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        isNightModeEnabled = prefs.getBoolean("night_mode", false);
        isAnimationEnabled = prefs.getBoolean("animations", true);
        isStatusBarEnabled = prefs.getBoolean("show_status_bar", false);

        setTheme(isNightModeEnabled ? R.style.theme_dark : R.style.theme_light);
        animation_duration = isAnimationEnabled ? 600 : 0;
        if(isStatusBarEnabled)
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
    }
}
