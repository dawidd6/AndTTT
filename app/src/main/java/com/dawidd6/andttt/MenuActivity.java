package com.dawidd6.andttt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

@SuppressWarnings("unused")
public class MenuActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void switchActivity(Class c) {
        final Intent intent = new Intent(this, c);
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
