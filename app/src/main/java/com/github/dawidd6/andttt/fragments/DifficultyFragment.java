package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.github.dawidd6.andttt.R;

import butterknife.OnClick;

public class DifficultyFragment extends BaseFragment {
    public static final String TAG = "DifficultyFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_difficulty, parent, false);
    }

    @OnClick(R.id.easyButton)
    public void onEasyButtonClick() {
        launch(R.id.easyButton);
    }

    @OnClick(R.id.mediumButton)
    public void onMediumButtonClick() {
        launch(R.id.mediumButton);
    }

    @OnClick(R.id.hardButton)
    public void onHardButtonClick() {
        launch(R.id.hardButton);
    }

    private void launch(int botButtonID) {
        Bundle bundle = new Bundle();
        bundle.putInt("bot-button-id", botButtonID);
        Navigation.findNavController(requireActivity(), R.id.navigation_host_main).navigate(R.id.action_difficultyFragment_to_singleFragment, bundle);
    }
}
