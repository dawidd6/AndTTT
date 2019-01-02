package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.github.dawidd6.andttt.activities.MainActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.ai.AI;

public class SingleFragment extends BaseGameFragment {
    public static final String TAG = "SingleFragment";
    private AI ai;
    private int delay;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        player1.setName(getString(R.string.player));
        player2.setName(getString(R.string.ai) + " (" + getString(ai.getDifficulty()) + ")");

        delay = getResources().getInteger(R.integer.ai_move_delay);
        delay = MainActivity.isAnimationEnabled ? delay : 0;
    }

    @Override
    public void restartGame() {
        super.restartGame();

        AIMove();
    }

    @Override
    public void onClickTile(View view) {
        super.onClickTile(view);

        AIMove();
    }

    public void setAI(AI ai) {
        this.ai = ai;
    }

    private void AIMove() {
        setAllTilesClickable(false);

        new Handler().postDelayed(() -> {
            if (player2.isTurn())
                makeMove(player2, player1, ai.getMove(game, player2, player1));
            if(player1.isTurn())
                setAllTilesClickable(true);
        }, delay);
    }


}