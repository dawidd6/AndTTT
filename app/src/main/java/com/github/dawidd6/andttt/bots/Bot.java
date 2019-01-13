package com.github.dawidd6.andttt.bots;

import com.github.dawidd6.andttt.game.Game;
import com.github.dawidd6.andttt.game.Player;

public interface Bot {
    int getMove(Game game, Player bot, Player player);
    int getDifficulty();
}
