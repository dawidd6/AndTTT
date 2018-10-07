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
    BaseActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        setRetainInstance(true);

        PreferenceManager manager = getPreferenceManager();
        Preference night_mode = manager.findPreference("night_mode");
        Preference status_bar = manager.findPreference("show_status_bar");
        activity = ((BaseActivity)getActivity());

        night_mode.setOnPreferenceChangeListener((preference, newValue) -> {
            Log.i("TAG", "changed theme");
            new Handler().postDelayed(() -> activity.recreate(), 250);
            return true;
        });

        status_bar.setOnPreferenceChangeListener((preference, newValue) -> {
            Log.i("TAG", "changed status bar");
            new Handler().postDelayed(() -> {

            }, 250);
            return true;
        });
    }
}