package com.github.dawidd6.andttt.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.github.dawidd6.andttt.R;

import butterknife.OnClick;

public class MenuFragment extends BaseFragment {
    public static final String TAG = "MenuFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, parent, false);
    }

    @OnClick(R.id.localButton)
    public void onLocalButtonClick() {
        Navigation.findNavController(requireActivity(), R.id.navigation_host_main).navigate(R.id.action_menuFragment_to_localFragment);
    }

    @OnClick(R.id.singleButton)
    public void onSingleButtonClick() {
        Navigation.findNavController(requireActivity(), R.id.navigation_host_main).navigate(R.id.action_menuFragment_to_difficultyFragment);
    }

    @OnClick(R.id.onlineButton)
    public void onOnlineButtonClick() {
        Navigation.findNavController(requireActivity(), R.id.navigation_host_main).navigate(R.id.action_menuFragment_to_onlineActivity);
    }

    @OnClick(R.id.arenaButton)
    public void onArenaButtonClick() {
        Navigation.findNavController(requireActivity(), R.id.navigation_host_main).navigate(R.id.action_menuFragment_to_gladiatorsFragment);
    }

    @OnClick(R.id.settingsButton)
    public void onSettingsButtonClick() {
        Navigation.findNavController(requireActivity(), R.id.navigation_host_main).navigate(R.id.action_menuFragment_to_settingsFragment);
    }
}
