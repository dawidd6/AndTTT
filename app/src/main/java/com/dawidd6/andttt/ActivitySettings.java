package com.dawidd6.andttt;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class ActivitySettings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private boolean isNightModeEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        isNightModeEnabled = getIntent().getBooleanExtra("night_mode", false);
        setTheme(isNightModeEnabled ? R.style.theme_dark : R.style.theme_light);

        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    public static class SettingsFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        switch(key)
        {
            case "night_mode":
                getIntent().putExtra("night_mode", sharedPreferences.getBoolean("night_mode", false));
                Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    public void run()
                    {
                        recreate();
                    }
                }, 200);
            break;
        }
    }

    @Override
    public void onBackPressed()
    {
        onNavigateUp();
    }
}
