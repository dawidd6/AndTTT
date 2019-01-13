package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.github.dawidd6.andttt.activities.MainActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.bots.Bot;

public class SingleFragment extends BaseGameFragment {
    public static final String TAG = "SingleFragment";
    private Bot bot;
    private int delay;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        player1.setName(getString(R.string.player));
        player2.setName(getString(R.string.bot) + " (" + getString(bot.getDifficulty()) + ")");

        delay = getResources().getInteger(R.integer.bot_move_delay);
        delay = MainActivity.isAnimationEnabled ? delay : 0;
    }

    @Override
    public void restartGame() {
        super.restartGame();

        BotMove();
    }

    @Override
    public void onClickTile(View view) {
        super.onClickTile(view);

        BotMove();
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    private void BotMove() {
        setAllTilesClickable(false);

        new Handler().postDelayed(() -> {
            if (player2.isTurn())
                makeMove(player2, player1, bot.getMove(game, player2, player1));
            if(player1.isTurn())
                setAllTilesClickable(true);
        }, delay);
    }


}