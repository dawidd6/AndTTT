package com.github.dawidd6.andttt.bots;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.game.Game;
import com.github.dawidd6.andttt.game.Player;
import com.github.dawidd6.andttt.proto.Symbol;

import java.util.Random;
import java.util.Vector;

public class EasyBot implements Bot {
    @Override
    public int getMove(Game game, Player bot, Player player) {
        Vector<Integer> nonePositions = new Vector<>();
        int counter = 0;

        for(int p[] : Game.getPatterns()) {
            for(int j = 0; j < 3; j++) {
                if(game.getTile(p[j]) == bot.getSymbol())
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

        return nonePositions.elementAt(new Random().nextInt(nonePositions.size()));
    }

    @Override
    public int getDifficulty() {
        return R.string.difficulty_easy;
    }
}
