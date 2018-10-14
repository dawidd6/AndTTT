package com.github.dawidd6.andttt;

import android.graphics.Typeface;
import android.widget.TextView;

public class Player {
    private TextView text;
    private SymbolView view;
    private Symbols symbol;
    private String name;
    private int wins;
    private int color;

    public Player(TextView text, SymbolView view, String name, int color, int thickness, int size) {
        this.wins = 0;

        this.text = text;
        this.view = view;

        this.view.setColor(color);
        this.view.setThickness(thickness/2);
        this.view.setSize(size);

        this.name = name;
        this.text.setText(name);
    }

    public void addWin() {
        this.wins++;
    }

    public int getWins() {
        return wins;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setSymbol(Symbols symbol, int animation_duration) {
        this.symbol = symbol;
        view.setMode(symbol);
        new SymbolAnimation(view).setDuration(animation_duration);
    }

    public Symbols getSymbol() {
        return symbol;
    }

    public void setTurn(boolean turn) {
        this.text.setTypeface(null, turn ? Typeface.BOLD : Typeface.NORMAL);
    }

    public void setName(String name) {
        this.name = name;
        this.text.setText(name);
    }

    public String getName() {
        return name;
    }
}
