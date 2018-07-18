package com.dawidd6.andttt.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dawidd6.andttt.R;

@SuppressWarnings("unused")
public class MenuActivity extends Activity {
    private boolean isNightModeEnabled;
    private boolean isAnimationEnabled;
    private int animation_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        isNightModeEnabled = prefs.getBoolean("night_mode", false);
        isAnimationEnabled = prefs.getBoolean("animations", true);

        setTheme(isNightModeEnabled ? R.style.theme_dark : R.style.theme_light);

        animation_duration = isAnimationEnabled ? 600 : 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClickPlaySingle(View view) {
        YoYo.with(Techniques.StandUp).duration(animation_duration).playOn(view);
        final Intent intent = new Intent(this, com.dawidd6.andttt.activities.SingleActivity.class);
        intent.putExtra("night_mode" , isNightModeEnabled);
        intent.putExtra("animation_duration" , animation_duration);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);
            }
        }, animation_duration);
    }

    public void onClickSettings(View view) {
        YoYo.with(Techniques.StandUp).duration(animation_duration).playOn(view);
        final Intent intent = new Intent(this, com.dawidd6.andttt.activities.SettingsActivity.class);
        intent.putExtra("night_mode" , isNightModeEnabled);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);
            }
        }, animation_duration);
    }

    public void onClickExit(View view) {
        finish();
        System.exit(0);
    }
}
