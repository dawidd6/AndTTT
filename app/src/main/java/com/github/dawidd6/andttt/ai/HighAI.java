package com.github.dawidd6.andttt.ai;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.game.Game;
import com.github.dawidd6.andttt.game.Player;
import com.github.dawidd6.andttt.game.Status;
import com.github.dawidd6.andttt.proto.Symbol;

import java.util.Random;
import java.util.Vector;

public class HighAI implements AI {
    @Override
    public int getMove(Game game, Player ai, Player player) {
        return 0; // TODO
    }

    @Override
    public int getDifficulty() {
        return R.string.difficulty_high;
    }
}
