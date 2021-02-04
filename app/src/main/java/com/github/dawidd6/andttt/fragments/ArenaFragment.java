package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.bots.Bot;
import com.github.dawidd6.andttt.bots.EasyBot;
import com.github.dawidd6.andttt.bots.HardBot;
import com.github.dawidd6.andttt.bots.MediumBot;

public class ArenaFragment extends BaseGameFragment {
    public static final String TAG = "ArenaFragment";
    private Bot bot1;
    private Bot bot2;
    private int delay;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int firstBotSpinnerID = requireArguments().getInt("first-bot-spinner-id");
        switch(firstBotSpinnerID) {
            case 0:
                bot1 = new EasyBot();
                break;
            case 1:
                bot1 = new MediumBot();
                break;
            case 2:
                bot1 = new HardBot();
                break;
        }

        int secondBotSpinnerID = requireArguments().getInt("second-bot-spinner-id");
        switch(secondBotSpinnerID) {
            case 0:
                bot2 = new EasyBot();
                break;
            case 1:
                bot2 = new MediumBot();
                break;
            case 2:
                bot2 = new HardBot();
                break;
        }

        player1.setName(getString(R.string.bot) + " 1 (" + getString(bot1.getDifficulty()) + ")");
        player2.setName(getString(R.string.bot) + " 2 (" + getString(bot2.getDifficulty()) + ")");

        delay = getResources().getInteger(R.integer.arena_move_delay);
    }

    @Override
    public void restartGame() {
        super.restartGame();

        setAllTilesClickable(false);

        BotMove();
    }

    private void BotMove() {
        new Handler().postDelayed(() -> {
            if(getActivity() == null) {
                return;
            }

            if(player1.isTurn())
                makeMove(player1, player2, bot1.getMove(game, player1, player2));
            else if(player2.isTurn())
                makeMove(player2, player1, bot2.getMove(game, player2, player1));

            if(game.isPlaying())
                BotMove();
        }, delay);
    }
}