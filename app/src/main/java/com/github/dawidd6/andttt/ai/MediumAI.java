package com.github.dawidd6.andttt.ai;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.game.Game;
import com.github.dawidd6.andttt.game.Player;
import com.github.dawidd6.andttt.proto.Symbol;

import java.util.Random;
import java.util.Vector;

public class MediumAI implements AI {
    @Override
    public int getMove(Game game, Player ai, Player player) {
        Vector<Integer> nonePositions = new Vector<>();
        int counter = 0;

        for(Symbol s : new Symbol[] {ai.getSymbol(), player.getSymbol()}) {
            nonePositions.removeAllElements();
            for(int p[] : game.getPatterns()) {
                for(int j = 0; j < 3; j++) {
                    if(game.getTile(p[j]) == s)
                        counter++;
                    else if(game.getTile(p[j]) == Symbol.NO) {
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

        return nonePositions.elementAt(new Random().nextInt(nonePositions.size()));
    }

    @Override
    public int getDifficulty() {
        return R.string.difficulty_medium;
    }
}
