package com.github.dawidd6.andttt;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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

    private Stack previousStack;
    private Stack currentStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        if(savedInstanceState != null) {
            previousStack = (Stack)savedInstanceState.getSerializable("previousStack");
            currentStack = (Stack)savedInstanceState.getSerializable("currentStack");

            // a little sneaky trick, disable transition animation,
            // so it seems like preference change is smooth
            boolean anim = isAnimationEnabled;
            isAnimationEnabled = false;
            switchFragments(currentStack);
            isAnimationEnabled = anim;
        }
        else
            switchFragments(Stack.MENU);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("previousStack", previousStack);
        outState.putSerializable("currentStack", currentStack);
    }

    @Override
    public void onBackPressed() {
        if(previousStack == null)
            finish();
        else
            switchFragments(previousStack);
    }

    public void switchFragments(Stack stack) {
        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.disallowAddToBackStack();
        if(isAnimationEnabled)
            t.setCustomAnimations(
                    R.animator.slide_in_left,
                    R.animator.slide_out_right,
                    R.animator.slide_in_right,
                    R.animator.slide_out_left);

        //t.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);

        switch(stack) {
            case MENU:
                t.replace(R.id.placeholder, new MenuFragment());
                previousStack = null;
                break;
            case SETTINGS:
                t.replace(R.id.placeholder, new SettingsFragment());
                previousStack = Stack.MENU;
                break;
        }

        currentStack = stack;

        t.commit();
    }
}
