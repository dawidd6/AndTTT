package com.github.dawidd6.andttt;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import com.github.dawidd6.andttt.fragments.MenuFragment;

public class MainActivity extends Activity {
    private boolean isNightModeEnabled;
    private boolean isAnimationEnabled;
    private boolean isStatusBarEnabled;

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

    public boolean isAnimationEnabled() {
        return isAnimationEnabled;
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
