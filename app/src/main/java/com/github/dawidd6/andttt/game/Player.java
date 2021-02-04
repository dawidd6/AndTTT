package com.github.dawidd6.andttt.game;

import android.graphics.Color;

import com.github.dawidd6.andttt.proto.Symbol;

public class Player {
    private boolean turn;
    private Symbol symbol;
    private String name;
    private int wins;
    private int color;

    public Player() {
        turn = false;
        symbol = Symbol.NO;
        name = "";
        wins = 0;
        color = Color.GRAY;
    }
    
    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        this.wins++;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
