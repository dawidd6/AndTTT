package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        int delay = getResources().getInteger(R.integer.activity_recreate_delay);

        PreferenceManager manager = getPreferenceManager();
        Preference night_mode = manager.findPreference("night_mode");
        Preference status_bar = manager.findPreference("show_status_bar");
        Preference animations = manager.findPreference("animations");
        Preference licenses = manager.findPreference("licenses");

        assert night_mode != null;
        night_mode.setOnPreferenceChangeListener((preference, newValue) -> {
            new Handler().postDelayed(() -> getActivity().recreate(), delay);
            return true;
        });

        assert status_bar != null;
        status_bar.setOnPreferenceChangeListener((preference, newValue) -> {
            new Handler().postDelayed(() -> getActivity().recreate(), delay);
            return true;
        });

        assert animations != null;
        animations.setOnPreferenceChangeListener((preference, newValue) -> {
            new Handler().postDelayed(() -> getActivity().recreate(), delay);
            return true;
        });

        assert licenses != null;
        licenses.setOnPreferenceClickListener(preference -> {
            ((MainActivity)getActivity()).switchFragments(new LicensesFragment(),true);
            return true;
        });
    }
}