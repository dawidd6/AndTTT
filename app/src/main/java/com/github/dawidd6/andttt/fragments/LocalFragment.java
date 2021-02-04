package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.view.View;

import com.github.dawidd6.andttt.R;

public class LocalFragment extends BaseGameFragment {
    public static final String TAG = "LocalFragment";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        player1.setName(getString(R.string.player) + " 1");
        player2.setName(getString(R.string.player) + " 2");
    }
}