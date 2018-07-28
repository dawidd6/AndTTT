package com.github.dawidd6.andttt;

import android.os.Bundle;

public class LocalActivity extends BaseGameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        player1.setName(getString(R.string.player) + " 1");
        player2.setName(getString(R.string.player) + " 2");
    }
}
