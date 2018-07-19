package com.dawidd6.andttt;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.ListView;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // remove dividers
        ListView list = getView().findViewById(android.R.id.list);
        list.setDivider(null);
    }
}