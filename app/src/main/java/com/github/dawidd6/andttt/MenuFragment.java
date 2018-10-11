package com.github.dawidd6.andttt;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {
    private BaseActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        activity = ((BaseActivity)getActivity());

        Button settingsButton = view.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(v -> {
            activity.switchFragments(Stack.SETTINGS);
        });
    }
}
