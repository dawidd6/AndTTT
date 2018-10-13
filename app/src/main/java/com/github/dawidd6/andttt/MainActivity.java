package com.github.dawidd6.andttt;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import java.util.List;

public class MainActivity extends Activity {
    protected boolean isNightModeEnabled;
    protected boolean isAnimationEnabled;
    protected boolean isStatusBarEnabled;
    protected int animation_duration;
    protected int colorForeground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        isAnimationEnabled = prefs.getBoolean("animations", true);
        isStatusBarEnabled = prefs.getBoolean("show_status_bar", false);
        isNightModeEnabled = prefs.getBoolean("night_mode", false);

        animation_duration = getResources().getInteger(R.integer.animation_duration);
        animation_duration = isAnimationEnabled ? animation_duration : 0;

        colorForeground = isNightModeEnabled ? Color.WHITE : Color.BLACK;

        if(isStatusBarEnabled)
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setTheme(isNightModeEnabled ? R.style.theme_dark : R.style.theme_light);
        setContentView(R.layout.activity);

        if(savedInstanceState == null)
            switchFragments(new MenuFragment(), false);
    }

    public void switchFragments(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction t = getFragmentManager().beginTransaction();

        if(addToBackStack)
            t.addToBackStack(null);

        if(isAnimationEnabled)
            t.setCustomAnimations(
                    R.animator.slide_in_left,
                    R.animator.slide_out_right,
                    R.animator.slide_in_right,
                    R.animator.slide_out_left);

        //t.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);

        t.replace(R.id.placeholder, fragment);
        t.commit();
    }
}
