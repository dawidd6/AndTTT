package com.github.dawidd6.andttt.bots;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.game.Game;
import com.github.dawidd6.andttt.game.Player;
import com.github.dawidd6.andttt.proto.Symbol;

import java.util.Random;
import java.util.Vector;

public class HardBot implements Bot {
    private Symbol[] board;
    private Symbol bot;
    private Symbol human;

    @Override
    public int getMove(Game game, Player bot, Player human) {
        this.board = game.getTiles().clone();
        this.bot = bot.getSymbol();
        this.human = human.getSymbol();

        Vector<Integer> nones = new Vector<>();

        for(int i = 0; i < board.length; i++) {
            if(board[i] == Symbol.NO) {
                nones.add(i);
            }
        }

        if(nones.size() == board.length)
            return nones.elementAt(new Random().nextInt(nones.size()));

        int oldValue = Integer.MIN_VALUE;
        int oldMove = -1;

        for(int newMove = 0; newMove < board.length; newMove++) {
            if(board[newMove] == Symbol.NO) {
                board[newMove] = bot.getSymbol();
                int newValue = minimax(0, false);
                board[newMove] = Symbol.NO;

                if(newValue > oldValue) {
                    oldValue = newValue;
                    oldMove = newMove;
                }
            }
        }

        return oldMove;
    }

    private int minimax(int depth, boolean isMaximizing) {
        for(int[] pattern : Game.getPatterns()) {
            int botCounter = 0;
            int humanCounter = 0;

            for(int position : pattern) {
                if(board[position] == bot)
                    botCounter++;
                if(board[position] == human)
                    humanCounter++;
            }

            if(botCounter == 3)
                return 10 - depth;
            if(humanCounter == 3)
                return -10 + depth;
        }

        int noneCounter = 0;
        for(int i = 0; i < board.length; i++) {
            if(board[i] == Symbol.NO)
                noneCounter++;
        }
        if(noneCounter == 0)
            return 0;

        if(isMaximizing) {
            int maxValue = Integer.MIN_VALUE;

            for(int i = 0; i < board.length; i++) {
                if(board[i] == Symbol.NO) {
                    board[i] = bot;
                    maxValue = Math.max(maxValue, minimax(depth+1, false));
                    board[i] = Symbol.NO;
                }
            }

            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;

            for(int i = 0; i < board.length; i++) {
                if(board[i] == Symbol.NO) {
                    board[i] = human;
                    minValue = Math.min(minValue, minimax(depth+1, true));
                    board[i] = Symbol.NO;
                }
            }

            return minValue;
        }
    }

    @Override
    public int getDifficulty() {
        return R.string.difficulty_hard;
    }
}
