package com.github.dawidd6.andttt;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;

public abstract class BaseActivity extends Activity {
    protected boolean isNightModeEnabled;
    protected boolean isAnimationEnabled;
    protected boolean isStatusBarEnabled;
    protected int animation_duration;
    protected int colorForeground;
    protected int colorBackground;
    protected int theme;
    protected int theme_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        isNightModeEnabled = prefs.getBoolean("night_mode", false);
        isAnimationEnabled = prefs.getBoolean("animations", true);
        isStatusBarEnabled = prefs.getBoolean("show_status_bar", false);

        colorBackground = getResources().getColor(isNightModeEnabled ? R.color.color_black : R.color.color_light);
        colorForeground = isNightModeEnabled ? Color.WHITE : Color.BLACK;
        animation_duration = isAnimationEnabled ? 600 : 0;
        theme = isNightModeEnabled ? R.style.theme_dark : R.style.theme_light;
        theme_dialog = isNightModeEnabled ? R.style.theme_dark_dialog : R.style.theme_light_dialog;

        if(isStatusBarEnabled)
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setTheme(theme);
        super.onCreate(savedInstanceState);
    }
}
