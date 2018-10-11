package com.github.dawidd6.andttt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.WindowManager;

public class SettingsFragment extends PreferenceFragment {
    private BaseActivity activity;
    private int delay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        activity = ((BaseActivity)getActivity());
        delay = getResources().getInteger(R.integer.activity_recreate_delay);

        PreferenceManager manager = getPreferenceManager();
        Preference night_mode = manager.findPreference("night_mode");
        Preference status_bar = manager.findPreference("show_status_bar");
        Preference animations = manager.findPreference("animations");

        assert night_mode != null;
        night_mode.setOnPreferenceChangeListener((preference, newValue) -> {
            new Handler().postDelayed(() -> activity.recreate(), delay);
            return true;
        });

        assert status_bar != null;
        status_bar.setOnPreferenceChangeListener((preference, newValue) -> {
            new Handler().postDelayed(() -> activity.recreate(), delay);
            return true;
        });

        assert animations != null;
        animations.setOnPreferenceChangeListener((preference, newValue) -> {
            new Handler().postDelayed(() -> activity.recreate(), delay);
            return true;
        });
    }
}