package com.github.dawidd6.andttt;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.WindowManager;

public class MainActivity extends Activity {
    protected boolean isNightModeEnabled;
    protected boolean isAnimationEnabled;
    protected boolean isStatusBarEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        isAnimationEnabled = prefs.getBoolean("animations", true);
        isStatusBarEnabled = prefs.getBoolean("show_status_bar", false);
        isNightModeEnabled = prefs.getBoolean("night_mode", false);

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
                    android.R.animator.fade_in,
                    android.R.animator.fade_out,
                    android.R.animator.fade_in,
                    android.R.animator.fade_out);

        t.replace(R.id.placeholder, fragment);
        t.commit();
    }
}
