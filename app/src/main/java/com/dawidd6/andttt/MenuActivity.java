package com.dawidd6.andttt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

@SuppressWarnings("unused")
public class MenuActivity extends Activity {
    private boolean isNightModeEnabled;
    private boolean isAnimationEnabled;
    private boolean isStatusBarEnabled;
    private int animation_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        isNightModeEnabled = prefs.getBoolean("night_mode", false);
        isAnimationEnabled = prefs.getBoolean("animations", true);
        isStatusBarEnabled = prefs.getBoolean("show_status_bar", false);

        setTheme(isNightModeEnabled ? R.style.theme_dark : R.style.theme_light);
        animation_duration = isAnimationEnabled ? 600 : 0;
        if(isStatusBarEnabled)
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void switchActivity(Class c) {
        final Intent intent = new Intent(this, c);
        intent.putExtra("night_mode" , isNightModeEnabled);
        intent.putExtra("animation_duration" , animation_duration);
        intent.putExtra("show_status_bar" , isStatusBarEnabled);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);
            }
        }, animation_duration);
    }

    public void onClickPlaySingle(View view) {
        YoYo.with(Techniques.StandUp).duration(animation_duration).playOn(view);

        switchActivity(SingleActivity.class);
    }

    public void onClickSettings(View view) {
        YoYo.with(Techniques.StandUp).duration(animation_duration).playOn(view);

        switchActivity(SettingsActivity.class);
    }

    public void onClickExit(View view) {
        finish();
        System.exit(0);
    }
}
