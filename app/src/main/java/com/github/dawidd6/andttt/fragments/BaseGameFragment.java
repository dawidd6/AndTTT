package com.github.dawidd6.andttt.fragments;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.activities.MainActivity;
import com.github.dawidd6.andttt.animations.DarkenAnimation;
import com.github.dawidd6.andttt.animations.LightenAnimation;
import com.github.dawidd6.andttt.animations.PulseAnimation;
import com.github.dawidd6.andttt.drawings.DrawCircle;
import com.github.dawidd6.andttt.drawings.DrawCross;
import com.github.dawidd6.andttt.drawings.DrawLine;
import com.github.dawidd6.andttt.game.Game;
import com.github.dawidd6.andttt.game.Player;
import com.github.dawidd6.andttt.misc.PlayerGui;
import com.github.dawidd6.andttt.proto.Symbol;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public abstract class BaseGameFragment extends BaseFragment {
    private ImageView tilesView[];
    private Bitmap tilesBitmap[];
    private Bitmap boardBitmap;

    @BindView(R.id.player1Text) TextView player1Text;
    @BindView(R.id.player2Text) TextView player2Text;
    @BindView(R.id.player1View) ImageView player1View;
    @BindView(R.id.player2View) ImageView player2View;
    @BindView(R.id.player1Score) TextView player1Score;
    @BindView(R.id.player2Score) TextView player2Score;
    @BindView(R.id.restartText) TextView restartText;
    @BindView(R.id.conclusionText) TextView conclusionText;
    @BindView(R.id.conclusionFrame) FrameLayout conclusionFrame;
    @BindView(R.id.boardView) ImageView boardView;
    @BindViews({R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8}) List<ImageView> tilesList;

    @BindDimen(R.dimen.tile_dimen) int tile_dimen;
    @BindDimen(R.dimen.symbol_dimen) int symbol_dimen;
    @BindDimen(R.dimen.board_dimen) int board_dimen;
    @BindDimen(R.dimen.frame_dimen) int frame_dimen;
    @BindDimen(R.dimen.symbol_thickness_dimen) int symbol_thickness_dimen;
    @BindDimen(R.dimen.line_thickness_dimen) int line_thickness_dimen;

    protected PlayerGui player1;
    protected PlayerGui player2;
    
    private int colorSymbol;
    private int colorLine;

    protected int animation_duration;

    protected Game game;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, parent, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // unlock orientation
        lockOrientation(false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        lockOrientation(!hidden);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // lock to portrait only
        lockOrientation(true);

        // set animation duration
        animation_duration = getResources().getInteger(R.integer.animation_duration);
        animation_duration = MainActivity.isAnimationEnabled ? animation_duration : 0;

        // get colorSymbol and colorLine
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(android.R.attr.colorForeground, typedValue, true);
        colorSymbol = typedValue.data;
        getActivity().getTheme().resolveAttribute(android.R.attr.colorAccent, typedValue, true);
        colorLine = typedValue.data;
        
        // init stuff
        game = new Game();
        tilesBitmap = new Bitmap[9];
        tilesView = tilesList.toArray(new ImageView[9]);

        // initialize players
        player1 = new PlayerGui(player1View, player1Text, player1Score, symbol_dimen);
        player1.setColor(Color.GREEN);
        player1.setName(getString(R.string.player1));
        player2 = new PlayerGui(player2View, player2Text, player2Score, symbol_dimen);
        player2.setColor(Color.RED);
        player2.setName(getString(R.string.player2));

        // maximize board, tiles and other stuff
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int boardWidth = metrics.widthPixels;
        int tileWidth = 0;

        while(true) {
            tileWidth = (boardWidth - 2 * frame_dimen) / 3;

            if(tileWidth % 2 == 0) {
                break;
            } else {
                boardWidth--;
            }
        }

        if(MainActivity.isMaximizationEnabled) {
            board_dimen = boardWidth;
            tile_dimen = tileWidth;
        }

        setViewSize(view.findViewById(R.id.frame_horizon_1), board_dimen, -1);
        setViewSize(view.findViewById(R.id.frame_horizon_2), board_dimen, -1);
        setViewSize(view.findViewById(R.id.frame_vertical_1), -1, board_dimen);
        setViewSize(view.findViewById(R.id.frame_vertical_2), -1, board_dimen);
        setViewSize(boardView, board_dimen, board_dimen);

        // init tiles + their bitmaps
        for(int i = 0; i < 9; i++) {
            tilesBitmap[i] = Bitmap.createBitmap(tile_dimen, tile_dimen, Bitmap.Config.ARGB_4444);
            tilesView[i].setImageBitmap(tilesBitmap[i]);

            setViewSize(tilesView[i], tile_dimen, tile_dimen);
        }

        // init board and its bitmap
        boardBitmap = Bitmap.createBitmap(board_dimen, board_dimen, Bitmap.Config.ARGB_4444);
        boardView.setImageBitmap(boardBitmap);

        // hide conclusion stuff
        restartText.setAlpha(0);
        conclusionText.setAlpha(0);
        conclusionFrame.setAlpha(0);

        // starting point
        restartGame();
    }

    protected void lockOrientation(boolean lock) {
        int info = lock ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        getActivity().setRequestedOrientation(info);
    }

    private void setViewSize(View view, int width, int height) {
        if(width != -1) {
            view.getLayoutParams().width = width;
        }

        if(height != -1) {
            view.getLayoutParams().height = height;
        }

        view.requestLayout();
    }

    private void drawLine() {
        int startX = 0;
        int startY = 0;
        int stopX = 0;
        int stopY = 0;

        switch(game.getPattern()) {
            case 0: // h1
                startX = 0;
                startY = tile_dimen / 2;
                stopX = board_dimen;
                stopY = tile_dimen / 2;
                break;
            case 1: // h2
                startX = 0;
                startY = board_dimen / 2;
                stopX = board_dimen;
                stopY = board_dimen / 2;
                break;
            case 2: // h3
                startX = 0;
                startY = board_dimen - (tile_dimen / 2);
                stopX = board_dimen;
                stopY = board_dimen - (tile_dimen / 2);
                break;
            case 3: // v1
                startX = tile_dimen / 2;
                startY = 0;
                stopX = tile_dimen / 2;
                stopY = board_dimen;
                break;
            case 4: // v2
                startX = board_dimen / 2;
                startY = 0;
                stopX = board_dimen / 2;
                stopY = board_dimen;
                break;
            case 5: // v3
                startX = board_dimen - (tile_dimen / 2);
                startY = 0;
                stopX = board_dimen - (tile_dimen / 2);
                stopY = board_dimen;
                break;
            case 6: // nl
                startX = 0;
                startY = 0;
                stopX = board_dimen;
                stopY = board_dimen;
                break;
            case 7: // nr
                startX = board_dimen;
                startY = 0;
                stopX = 0;
                stopY = board_dimen;
                break;
        }

        new DrawLine(startX, startY, stopX, stopY, true)
                .setDuration(animation_duration)
                .setColor(colorLine)
                .setThickness(line_thickness_dimen)
                .setImage(boardView)
                .setBitmap(boardBitmap)
                .execute();
    }

    private void drawSymbol(ImageView image, Bitmap bitmap, Symbol symbol) {
        switch(symbol) {
            case CIRCLE:
                new DrawCircle()
                        .setDuration(animation_duration)
                        .setColor(colorSymbol)
                        .setThickness(symbol_thickness_dimen)
                        .setImage(image)
                        .setBitmap(bitmap)
                        .execute();
                break;
            case CROSS:
                new DrawCross()
                        .setDuration(animation_duration/2)
                        .setColor(colorSymbol)
                        .setThickness(symbol_thickness_dimen)
                        .setImage(image)
                        .setBitmap(bitmap)
                        .execute();
                break;
        }
    }

    public void showConclusion(String text, int color) {
        conclusionText.setText(text);
        conclusionText.setTextColor(color);
        if(!text.equals(getString(R.string.waiting)))
            new DarkenAnimation(restartText, animation_duration*2);
        new DarkenAnimation(conclusionFrame, animation_duration*2);
        new DarkenAnimation(conclusionText, animation_duration*2);
    }

    public void hideConclusion() {
        if(restartText.getAlpha() > 0)
            new LightenAnimation(restartText, animation_duration);
        if(conclusionText.getAlpha() > 0)
            new LightenAnimation(conclusionText, animation_duration);
        if(conclusionFrame.getAlpha() > 0)
            new LightenAnimation(conclusionFrame, animation_duration);
    }

    public void randomize() {
        // randomize players' turns
        boolean turns[] = game.getRandomTurns();
        player1.setTurn(turns[0]);
        player2.setTurn(turns[1]);

        // randomize symbols for players
        Symbol symbols[] = game.getRandomSymbols();
        player1.setSymbol(symbols[0]);
        player2.setSymbol(symbols[1]);
    }

    public void restartGame() {
        hideConclusion();

        // game stuff
        game.resetStatus();
        game.resetNoneCounter();
        game.resetTiles();

        randomize();

        // draw players' symbols on respective views
        drawSymbol(player1.getImageView(), player1.getBitmap(), player1.getSymbol());
        drawSymbol(player2.getImageView(), player2.getBitmap(), player2.getSymbol());

        // reset tiles
        for(int i = 0; i < 9; i++) {
            tilesBitmap[i].eraseColor(Color.TRANSPARENT);
            tilesView[i].clearAnimation();
        }

        // clear board
        boardBitmap.eraseColor(Color.TRANSPARENT);
        boardView.clearAnimation();

        // enable all tiles
        setAllTilesClickable(true);

        // set restart button not clickable
        setRestartButtonClickable(false);
    }

    public void checkStatus() {
        if(game.isWin()) {
            if(player1.isTurn()) {
                new PulseAnimation(player1Score, animation_duration);
                player1.addWin();
                showConclusion(getString(R.string.player_won, player1.getName()), player1.getColor());
            } else {
                new PulseAnimation(player2Score, animation_duration);
                player2.addWin();
                showConclusion(getString(R.string.player_won, player2.getName()), player2.getColor());
            }
            drawLine();
        } else if(game.isDraw()) {
            showConclusion(getString(R.string.nobody_won), Color.BLUE);
        } else {
            return;
        }

        setAllTilesClickable(false);
    }

    protected void setAllTilesClickable(boolean clickable) {
        for(int i = 0; i < 9; i++)
            if(game.getTile(i) == Symbol.NO)
                tilesView[i].setClickable(clickable);
    }

    protected void setRestartButtonClickable(boolean clickable) {
        conclusionFrame.setClickable(clickable);
    }

    protected void makeMove(Player playerWithTurn, Player playerWithoutTurn, int i) {
        game.setTile(i, playerWithTurn.getSymbol());
        tilesView[i].setClickable(false);

        drawSymbol(tilesView[i], tilesBitmap[i], playerWithTurn.getSymbol());

        checkStatus();

        if(game.isPlaying()) {
            playerWithTurn.setTurn(false);
            playerWithoutTurn.setTurn(true);
        } else {
            setRestartButtonClickable(true);
        }
    }

    @OnClick({R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8})
    public void onClickTile(View view) {
        int i = Character.getNumericValue(getResources().getResourceEntryName(view.getId()).charAt(1));
        makeMove(player1.isTurn() ? player1 : player2,
                player2.isTurn() ? player1 : player2,
                i);
    }

    @OnClick(R.id.conclusionFrame)
    public void onClickRestart(View view) {
        restartGame();
    }
}
