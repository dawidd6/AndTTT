package com.github.dawidd6.andttt.fragments;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.OnlineActivity;
import com.github.dawidd6.andttt.R;

public class MenuFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button localButton = view.findViewById(R.id.localButton);
        localButton.setOnClickListener(v -> {
            MainActivity.switchFragments(getFragmentManager(), new LocalFragment(), true);
        });

        Button singleButton = view.findViewById(R.id.singleButton);
        singleButton.setOnClickListener(v -> {
            MainActivity.switchFragments(getFragmentManager(), new SingleFragment(), true);
        });

        Button onlineButton = view.findViewById(R.id.onlineButton);
        onlineButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OnlineActivity.class);
            startActivity(intent);
        });

        Button settingsButton = view.findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(v -> {
            MainActivity.switchFragments(getFragmentManager(), new SettingsFragment(), true);
        });
    }
}
