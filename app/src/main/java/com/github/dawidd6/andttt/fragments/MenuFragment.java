package com.github.dawidd6.andttt.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.OnClick;
import com.github.dawidd6.andttt.activities.MainActivity;
import com.github.dawidd6.andttt.activities.OnlineActivity;
import com.github.dawidd6.andttt.R;

public class MenuFragment extends BaseFragment {
    public static final String TAG = "MenuFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, parent, false);
    }

    @OnClick(R.id.localButton)
    public void onLocalButtonClick() {
        MainActivity.switchFragment(getFragmentManager(), new LocalFragment(), true);
    }

    @OnClick(R.id.singleButton)
    public void onSingleButtonClick() {
        MainActivity.switchFragment(getFragmentManager(), new DifficultyFragment(), true);
    }

    @OnClick(R.id.onlineButton)
    public void onOnlineButtonClick() {
        startActivity(new Intent(getActivity(), OnlineActivity.class));
    }

    @OnClick(R.id.arenaButton)
    public void onArenaButtonClick() {
        MainActivity.switchFragment(getFragmentManager(), new GladiatorsFragment(), true);
    }

    @OnClick(R.id.settingsButton)
    public void onSettingsButtonClick() {
        MainActivity.switchFragment(getFragmentManager(), new SettingsFragment(), true);
    }
}
