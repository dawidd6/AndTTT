package com.github.dawidd6.andttt.fragments;

import com.github.dawidd6.andttt.R;

public class LocalFragment extends BaseGameFragment {
    @Override
    protected void onFirstStart() {
        super.onFirstStart();

        player1.setName(getString(R.string.player) + " 1");
        player2.setName(getString(R.string.player) + " 2");
    }
}