package com.github.dawidd6.andttt;

import android.os.Bundle;
import android.view.View;

import java.util.Vector;

public class SingleActivity extends BaseGameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        int pos = 0;

        Symbols smb[] = {player2.getSymbol(), player1.getSymbol()};

        for(int i = 0; i < tiles.length; i++)
            if(tiles[i] == Symbols.NONE)
                    nonePositions.add(i);

        for(Symbols s : smb)
            for(int p[] : patterns) {
                for(int j = 0; j < 3; j++) {
                    if(tiles[p[j]] == s)
                        counter++;
                    else if(tiles[p[j]] == Symbols.NONE) {
                        counter = counter + 8;
                        pos = p[j];
                    }
                }

                if(counter == 10)
                    return pos;
                else
                    counter = 0;
            }

        return nonePositions.elementAt(rand.nextInt(nonePositions.size()));
    }
}