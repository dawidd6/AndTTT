package com.github.dawidd6.andttt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;

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
        startActivity(intent);
    }

    public void onClickPlaySingle(View view) {
        switchActivity(SingleActivity.class);
    }

    public void onClickPlayLocal(View view) {
        switchActivity(LocalActivity.class);
    }

    public void onClickSettings(View view) {
        switchActivity(SettingsActivity.class);
    }
}
