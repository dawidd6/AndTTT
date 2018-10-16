package com.github.dawidd6.andttt;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LocalFragment extends BaseGameFragment {
    @Override
    protected void onFirstStart() {
        super.onFirstStart();

        player1.setName(getString(R.string.player) + " 1");
        player2.setName(getString(R.string.player) + " 2");
    }
}