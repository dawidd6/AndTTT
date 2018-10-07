package com.github.dawidd6.andttt;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;
import android.view.WindowManager;

public class BaseActivity extends Activity {
    protected boolean isNightModeEnabled;
    protected boolean isAnimationEnabled;
    protected boolean isStatusBarEnabled;
    protected int animation_duration;
    protected int colorForeground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        isAnimationEnabled = prefs.getBoolean("animations", true);
        isStatusBarEnabled = prefs.getBoolean("show_status_bar", false);
        isNightModeEnabled = prefs.getBoolean("night_mode", false);

        animation_duration = isAnimationEnabled ? 600 : 0;
        colorForeground = isNightModeEnabled ? Color.WHITE : Color.BLACK;

        if(isStatusBarEnabled)
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setTheme(isNightModeEnabled ? R.style.theme_dark : R.style.theme_light);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        replaceFragment(new MenuFragment(), R.id.placeholder1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void replaceFragment(Fragment frag, int layout){
        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.setCustomAnimations(
                R.animator.slide_in_left,
                R.animator.slide_out_right,
                R.animator.slide_in_right,
                R.animator.slide_out_left);
        t.replace(layout, frag);
        t.commit();
    }
}
