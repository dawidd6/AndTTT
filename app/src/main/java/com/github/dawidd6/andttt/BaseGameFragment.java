package com.github.dawidd6.andttt;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.dawidd6.andttt.animations.DarkenAnimation;
import com.github.dawidd6.andttt.animations.LightenAnimation;
import com.github.dawidd6.andttt.animations.PulseAnimation;
import com.github.dawidd6.andttt.drawings.Draw;
import com.github.dawidd6.andttt.drawings.DrawCircle;
import com.github.dawidd6.andttt.drawings.DrawCross;
import com.github.dawidd6.andttt.drawings.DrawLine;


public abstract class BaseGameFragment extends Fragment {
    private ImageView tilesView[];
    private ImageView boardView;
    private Bitmap tilesBitmap[];
    private Bitmap boardBitmap;

    private TextView scoreText;
    private TextView conclusionText;

    private FrameLayout conclusionFrame;

    private int tile_dimen;
    private int symbol_dimen;
    private int board_dimen;
    private int frame_dimen;
    private int symbol_thickness_dimen;
    private int line_thickness_dimen;

    protected Player player1;
    protected Player player2;
    private TextView player1Text;
    private TextView player2Text;
    private ImageView player1View;
    private ImageView player2View;
    private Bitmap player1Bitmap;
    private Bitmap player2Bitmap;
    
    private int colorSymbol;
    private int colorLine;

    private int animation_duration;

    protected Game game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set animation duration
        animation_duration = getResources().getInteger(R.integer.animation_duration);
        animation_duration = ((MainActivity)getActivity()).isAnimationEnabled ? animation_duration : 0;

        // set on clicks listeners
        Button restartButton = view.findViewById(R.id.restartButton);
        restartButton.setOnClickListener(this::onClickRestart);

        // get colorSymbol and colorLine
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        colorSymbol = typedValue.data;
        colorLine = ContextCompat.getColor(getActivity(), R.color.color_green);
        
        // init stuff
        game = new Game();
        tilesView = new ImageView[9];
        tilesBitmap = new Bitmap[9];

        // get dimens
        tile_dimen = getResources().getDimensionPixelSize(R.dimen.tile_dimen);
        symbol_dimen = getResources().getDimensionPixelSize(R.dimen.symbol_dimen);
        board_dimen = getResources().getDimensionPixelSize(R.dimen.board_dimen);
        frame_dimen = getResources().getDimensionPixelSize(R.dimen.frame_dimen);
        symbol_thickness_dimen = getResources().getDimensionPixelSize(R.dimen.symbol_thickness_dimen);
        line_thickness_dimen = getResources().getDimensionPixelSize(R.dimen.line_thickness_dimen);

        // initialize players
        player1Text = view.findViewById(R.id.player1Text);
        player2Text = view.findViewById(R.id.player2Text);
        player1View = view.findViewById(R.id.player1View);
        player2View = view.findViewById(R.id.player2View);

        player1 = new Player()
                .setColor(Color.GREEN)
                .setName(getString(R.string.player1));
        player2 = new Player()
                .setColor(Color.RED)
                .setName(getString(R.string.player2));

        // find views
        scoreText = view.findViewById(R.id.scoreText);
        conclusionFrame = view.findViewById(R.id.conclusionFrame);
        conclusionText = view.findViewById(R.id.conclusionText);
        boardView = view.findViewById(R.id.boardView);

        // function meant to be override,
        // if there is a need to do something before
        // restarting the game for the first time
        onFirstStart();

        // init tiles + their bitmaps
        for(int i = 0; i < 9; i++) {
            tilesBitmap[i] = Bitmap.createBitmap(tile_dimen, tile_dimen, Bitmap.Config.ARGB_4444);
            tilesView[i] = view.findViewById(getResources().getIdentifier("b" + i, "id", getActivity().getPackageName()));
            tilesView[i].setOnClickListener(this::onClickTile);
            tilesView[i].setImageBitmap(tilesBitmap[i]);
        }

        // init board and its bitmap
        boardBitmap = Bitmap.createBitmap(board_dimen, board_dimen, Bitmap.Config.ARGB_4444);
        boardView.setImageBitmap(boardBitmap);

        // init players' bitmaps
        player1Bitmap = Bitmap.createBitmap(symbol_dimen, symbol_dimen, Bitmap.Config.ARGB_4444);
        player2Bitmap = Bitmap.createBitmap(symbol_dimen, symbol_dimen, Bitmap.Config.ARGB_4444);
        player1View.setImageBitmap(player1Bitmap);
        player2View.setImageBitmap(player2Bitmap);

        // set players' names to display
        player1Text.setText(player1.getName());
        player2Text.setText(player2.getName());

        // starting point
        restartGame();
    }

    protected void onFirstStart() {}

    private void drawLine() {
        int startX, startY, stopX, stopY;

        switch(game.getPattern()) {
            case 0: // h1
                startX = 0;
                startY = tile_dimen / 2;
                stopX = DrawLine.INCREMENT;
                stopY = tile_dimen / 2;
                break;
            case 1: // h2
                startX = 0;
                startY = board_dimen / 2;
                stopX = DrawLine.INCREMENT;
                stopY = board_dimen / 2;
                break;
            case 2: // h3
                startX = 0;
                startY = board_dimen - (tile_dimen / 2);
                stopX = DrawLine.INCREMENT;
                stopY = board_dimen - (tile_dimen / 2);
                break;
            case 3: // v1
                startX = tile_dimen / 2;
                startY = 0;
                stopX = tile_dimen / 2;
                stopY = DrawLine.INCREMENT;
                break;
            case 4: // v2
                startX = board_dimen / 2;
                startY = 0;
                stopX = board_dimen / 2;
                stopY = DrawLine.INCREMENT;
                break;
            case 5: // v3
                startX = board_dimen - (tile_dimen / 2);
                startY = 0;
                stopX = board_dimen - (tile_dimen / 2);
                stopY = DrawLine.INCREMENT;
                break;
            case 6: // nl
                // TODO random line direction, same below and above
                //if(new Random().nextBoolean())
                startX = 0;
                startY = 0;
                stopX = DrawLine.INCREMENT;
                stopY = DrawLine.INCREMENT;
                break;
            case 7: // nr
                startX = board_dimen;
                startY = 0;
                stopX = DrawLine.DECREMENT;
                stopY = DrawLine.INCREMENT;
                break;
            default:
                startX = 0;
                startY = 0;
                stopX = 0;
                stopY = 0;
                break;
        }

        new DrawLine()
                .setPoints(startX, startY, stopX, stopY)
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

    public void restartGame() {
        // determine if game is started for first time or restarted
        if(game.isPlaying()) {
            conclusionText.setAlpha(0);
            conclusionFrame.setAlpha(0);
        } else {
            new LightenAnimation(conclusionText, animation_duration);
            new LightenAnimation(conclusionFrame, animation_duration);
        }

        // game stuff
        game.resetStatus();
        game.resetNoneCounter();
        game.resetTiles();

        // randomize players' turns
        boolean turns[] = game.getRandomTurns();
        player1.setTurn(turns[0]);
        player2.setTurn(turns[1]);

        // randomize symbols for players
        Symbol symbols[] = game.getRandomSymbols();
        player1.setSymbol(symbols[0]);
        player2.setSymbol(symbols[1]);

        // draw players' symbols on respective views
        drawSymbol(player1View, player1Bitmap, player1.getSymbol());
        drawSymbol(player2View, player2Bitmap, player2.getSymbol());

        // reset tiles
        for(int i = 0; i < 9; i++) {
            tilesBitmap[i].eraseColor(Color.TRANSPARENT);
            tilesView[i].clearAnimation();
        }

        // clear rest of bitmaps
        boardBitmap.eraseColor(Color.TRANSPARENT);
        player1Bitmap.eraseColor(Color.TRANSPARENT);
        player2Bitmap.eraseColor(Color.TRANSPARENT);

        // set score
        scoreText.setText(getString(R.string.score, player1.getWins(), player2.getWins()));

        // set font type for players
        updateTurnFont(player1, player1Text);
        updateTurnFont(player2, player2Text);

        // enable all tiles
        setAllTilesClickable(true);
    }

    private void updateTurnFont(Player player, TextView playerText) {
        if(player.isTurn()) {
            playerText.setTypeface(null, Typeface.BOLD);
            playerText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        } else {
            playerText.setTypeface(null, Typeface.NORMAL);
            playerText.setPaintFlags(0);
        }
    }

    public void checkStatus() {
        if(game.isWin()) {
            if(player1.isTurn()) {
                player1.addWin();
                conclusionText.setText(getString(R.string.player_won, player1.getName()));
                conclusionText.setTextColor(player1.getColor());
            } else {
                player2.addWin();
                conclusionText.setText(getString(R.string.player_won, player2.getName()));
                conclusionText.setTextColor(player2.getColor());
            }
            new PulseAnimation(scoreText, animation_duration);
            scoreText.setText(getString(R.string.score, player1.getWins(), player2.getWins()));
            drawLine();
        } else if(game.isDraw()) {
            conclusionText.setText(getString(R.string.nobody_won));
            conclusionText.setTextColor(Color.BLUE);
        } else {
            return;
        }

        new DarkenAnimation(conclusionFrame, animation_duration*2);
        new DarkenAnimation(conclusionText, animation_duration*2);

        setAllTilesClickable(false);
    }

    protected void setAllTilesClickable(boolean clickable) {
        for(int i = 0; i < 9; i++)
            if(game.getTile(i) == Symbol.NONE)
                tilesView[i].setClickable(clickable);
    }



    public void makeMove(Player playerWithTurn, Player playerWithoutTurn, int i) {
        game.setTile(i, playerWithTurn.getSymbol());
        tilesView[i].setClickable(false);

        drawSymbol(tilesView[i], tilesBitmap[i], playerWithTurn.getSymbol());

        checkStatus();

        if(game.isPlaying()) {
            playerWithTurn.setTurn(false);
            playerWithoutTurn.setTurn(true);
        }

        updateTurnFont(player1, player1Text);
        updateTurnFont(player2, player2Text);
    }

    public void onClickTile(View view) {
        int i = Character.getNumericValue(getResources().getResourceEntryName(view.getId()).charAt(1));
        makeMove(player1.isTurn() ? player1 : player2,
                player2.isTurn() ? player1 : player2,
                i);
    }

    public void onClickRestart(View view) {
        restartGame();
    }
}
