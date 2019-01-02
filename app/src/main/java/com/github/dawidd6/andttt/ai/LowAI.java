package com.github.dawidd6.andttt.ai;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.game.Game;
import com.github.dawidd6.andttt.game.Player;
import com.github.dawidd6.andttt.proto.Symbol;

import java.util.Random;
import java.util.Vector;

public class LowAI implements AI {
    @Override
    public int getMove(Game game, Player ai, Player player) {
        Vector<Integer> nonePositions = new Vector<>();

        for(int i = 0; i < 9; i++) {
            if(game.getTile(i) == Symbol.NO) {
                nonePositions.add(i);
            }
        }

        return nonePositions.elementAt(new Random().nextInt(nonePositions.size()));
    }

    @Override
    public int getDifficulty() {
        return R.string.difficulty_low;
    }
}
