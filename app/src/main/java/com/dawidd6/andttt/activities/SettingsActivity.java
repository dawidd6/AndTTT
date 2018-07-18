package com.dawidd6.andttt.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.dawidd6.andttt.R;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean isNightModeEnabled = getIntent().getBooleanExtra("night_mode", false);
        setTheme(isNightModeEnabled ? R.style.theme_dark : R.style.theme_light);

        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch(key) {
            case "night_mode":
                getIntent().putExtra("night_mode", sharedPreferences.getBoolean("night_mode", false));
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        recreate();
                    }
                }, 250);
            break;
        }
    }

    @Override
    public void onBackPressed()
    {
        onNavigateUp();
    }
}
