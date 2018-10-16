package com.github.dawidd6.andttt;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.Vector;

public class SingleFragment extends BaseGameFragment {
    private int delay;

    @Override
    protected void onFirstStart() {
        super.onFirstStart();

        delay = getResources().getInteger(R.integer.ai_move_delay);

        player1.setName(getString(R.string.player));
        player2.setName(getString(R.string.ai));
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

    private void AIMove() {
        setAllTilesClickable(false);

        new Handler().postDelayed(() -> {
            if (!player1.isTurn())
                makeMove(player2, player1, computeMove());

            setAllTilesClickable(true);
        }, delay);
    }

    public int computeMove() {
        Vector<Integer> nonePositions = new Vector<>();

        int counter = 0;

        for(Symbol s : new Symbol[] {player2.getSymbol(), player1.getSymbol()}) {
            nonePositions.removeAllElements();
            for(int p[] : patterns) {
                for(int j = 0; j < 3; j++) {
                    if(tiles[p[j]] == s)
                        counter++;
                    else if(tiles[p[j]] == Symbol.NONE) {
                        counter = counter + 8;
                        nonePositions.add(p[j]);
                    }
                }

                if(counter == 10)
                    return nonePositions.lastElement();
                else
                    counter = 0;
            }
        }

        return nonePositions.elementAt(rand.nextInt(nonePositions.size()));
    }
}