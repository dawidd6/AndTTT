package com.github.dawidd6.andttt;

import android.os.Bundle;
import android.view.View;

import java.util.Vector;

public class SingleFragment extends BaseGameFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        player1.setName(getString(R.string.player));
        player2.setName(getString(R.string.ai));
    }

    @Override
    public void restartGame() {
        super.restartGame();

        if(!player1Turn)
            makeMove(player2, computeMove());
    }

    @Override
    public void onClickTile(View view) {
        super.onClickTile(view);

        if(!player1Turn)
            makeMove(player2, computeMove());
    }

    public int computeMove() {
        Vector<Integer> nonePositions = new Vector<>();

        int counter = 0;

        for(Symbols s : new Symbols[] {player2.getSymbol(), player1.getSymbol()}) {
            nonePositions.removeAllElements();
            for(int p[] : patterns) {
                for(int j = 0; j < 3; j++) {
                    if(tiles[p[j]] == s)
                        counter++;
                    else if(tiles[p[j]] == Symbols.NONE) {
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