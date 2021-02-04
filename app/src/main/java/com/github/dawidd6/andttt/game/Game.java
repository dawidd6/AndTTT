package com.github.dawidd6.andttt.game;

import com.github.dawidd6.andttt.proto.Symbol;

import java.util.Arrays;
import java.util.Random;

public class Game {
    private Status status;
    private Symbol tiles[];
    private int noneCounter;
    private static final int patterns[][] = {
            {0,1,2}, // 0 horizontal up
            {3,4,5}, // 1 horizontal mid
            {6,7,8}, // 2 horizontal bottom
            {0,3,6}, // 3 vertical left
            {1,4,7}, // 4 vertical mid
            {2,5,8}, // 5 vertical right
            {0,4,8}, // 6 narrow left
            {2,4,6}, // 7 narrow right
    };
    private int pattern;

    public Game() {
        tiles = new Symbol[9];

        status = Status.PLAYING;
        pattern = -1;
    }

    public boolean isPlaying() {
        return status == Status.PLAYING;
    }

    public boolean isDraw() {
        return status == Status.DRAW;
    }

    public boolean isWin() {
        return status == Status.WIN;
    }

    public void resetStatus() {
        this.status = Status.PLAYING;
    }

    public void setTile(int i, Symbol symbol) {
        tiles[i] = symbol;
        noneCounter--;

        int crosses = 0;
        int circles = 0;

        for(int x = 0; x < patterns.length; x++) {
            for(int y = 0; y < patterns[x].length; y++) {
                switch(tiles[patterns[x][y]]) {
                    case CROSS:
                        crosses++;
                        break;
                    case CIRCLE:
                        circles++;
                        break;
                }
            }

            if(crosses == 3 || circles == 3) {
                status = Status.WIN;
                pattern = x;
                return;
            }

            crosses = 0;
            circles = 0;
        }

        if(noneCounter == 0) {
            status = Status.DRAW;
        }
    }

    public Symbol getTile(int i) {
        return tiles[i];
    }

    public Symbol[] getTiles() {
        return tiles;
    }

    public void resetTiles() {
        Arrays.fill(tiles, Symbol.NO);
    }

    public int getPattern() {
        return pattern;
    }

    public static int[][] getPatterns() {
        return patterns;
    }

    public void resetNoneCounter() {
        this.noneCounter = tiles.length;
    }

    public boolean[] getRandomTurns() {
        boolean turns[] = new boolean[2];
        boolean random = new Random().nextBoolean();

        turns[0] = random;
        turns[1] = !random;

        return turns;
    }

    public Symbol[] getRandomSymbols() {
        Symbol symbols[] = new Symbol[2];
        boolean random = new Random().nextBoolean();

        symbols[0] = random ? Symbol.CROSS : Symbol.CIRCLE;
        symbols[1] = !random ? Symbol.CROSS : Symbol.CIRCLE;

        return symbols;
    }
}
