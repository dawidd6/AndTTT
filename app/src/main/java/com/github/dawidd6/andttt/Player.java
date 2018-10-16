package com.github.dawidd6.andttt;

public class Player {
    private boolean turn;
    private Symbol symbol;
    private String name;
    private int wins;
    private int color;
    
    public boolean isTurn() {
        return turn;
    }

    public Player setTurn(boolean turn) {
        this.turn = turn;
        return this;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public Player setSymbol(Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public int getWins() {
        return wins;
    }

    public Player addWin() {
        wins++;
        return this;
    }

    public int getColor() {
        return color;
    }

    public Player setColor(int color) {
        this.color = color;
        return this;
    }
}
