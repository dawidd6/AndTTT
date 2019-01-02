package com.github.dawidd6.andttt.ai;

import android.content.res.Resources;
import com.github.dawidd6.andttt.game.Game;
import com.github.dawidd6.andttt.game.Player;

public interface AI {
    int getMove(Game game, Player ai, Player player);
    int getDifficulty();
}
