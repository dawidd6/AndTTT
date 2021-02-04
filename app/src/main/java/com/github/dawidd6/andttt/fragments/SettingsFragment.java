package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.os.Handler;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.activities.MainActivity;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static final String TAG = "SettingsFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int delay = getResources().getInteger(R.integer.activity_recreate_delay);

        PreferenceManager manager = getPreferenceManager();
        Preference night_mode = manager.findPreference("night_mode");
        Preference status_bar = manager.findPreference("show_status_bar");
        Preference animations = manager.findPreference("animations");
        Preference maximization = manager.findPreference("maximization");
        Preference libraries = manager.findPreference("libraries");

        assert night_mode != null;
        night_mode.setOnPreferenceChangeListener((preference, newValue) -> {
            new Handler().postDelayed(() -> getActivity().recreate(), delay);
            return true;
        });

        assert status_bar != null;
        status_bar.setOnPreferenceChangeListener((preference, newValue) -> {
            new Handler().postDelayed(() -> requireActivity().recreate(), delay);
            return true;
        });

        assert animations != null;
        animations.setOnPreferenceChangeListener((preference, newValue) -> {
            MainActivity.isAnimationEnabled = (boolean)newValue;
            return true;
        });

        assert maximization != null;
        maximization.setOnPreferenceChangeListener((preference, newValue) -> {
            MainActivity.isMaximizationEnabled = (boolean)newValue;
            return true;
        });

        assert libraries != null;
        libraries.setOnPreferenceClickListener(preference -> {
            NavController navigation = Navigation.findNavController(requireActivity(), R.id.navigation_host_main);
            navigation.navigate(R.id.action_settingsFragment_to_librariesFragment);
            return true;
        });
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }
}