package com.github.dawidd6.andttt;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import java.util.Arrays;
import java.util.Random;

public abstract class BaseGameFragment extends Fragment {
    protected int patterns[][] = {
            {0,1,2}, // 0 horizontal up
            {3,4,5}, // 1 horizontal mid
            {6,7,8}, // 2 horizontal bottom
            {0,3,6}, // 3 vertical left
            {1,4,7}, // 4 vertical mid
            {2,5,8}, // 5 vertical right
            {0,4,8}, // 6 narrow left
            {2,4,6}, // 7 narrow right
    };

    protected Status status;
    protected Symbol tiles[];
    protected Random rand;

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

    private int noneCounter;

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

        // set initial status
        status = Status.PLAYING;

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
        rand = new Random();
        tiles = new Symbol[9];
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

    private void drawLine(int i) {
        int startX, startY, stopX, stopY;

        switch(i) {
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
        if(status == Status.PLAYING) {
            conclusionText.setAlpha(0);
            conclusionFrame.setAlpha(0);
        } else {
            new LightenAnimation(conclusionText, animation_duration);
            new LightenAnimation(conclusionFrame, animation_duration);
        }

        status = Status.PLAYING;
        noneCounter = tiles.length;

        // randomize players' turns
        boolean turn = rand.nextBoolean();
        player1.setTurn(turn);
        player2.setTurn(!turn);

        // randomize symbols for players
        boolean symbol = rand.nextBoolean();
        player1.setSymbol(symbol ? Symbol.CIRCLE : Symbol.CROSS);
        player2.setSymbol(!symbol ? Symbol.CIRCLE : Symbol.CROSS);

        // draw players' symbols on respective views
        drawSymbol(player1View, player1Bitmap, player1.getSymbol());
        drawSymbol(player2View, player2Bitmap, player2.getSymbol());

        // reset tiles
        for(int i = 0; i < 9; i++) {
            tilesBitmap[i].eraseColor(Color.TRANSPARENT);
            tilesView[i].clearAnimation();
            tiles[i] = Symbol.NONE;
        }

        // clear rest of bitmaps
        boardBitmap.eraseColor(Color.TRANSPARENT);
        player1Bitmap.eraseColor(Color.TRANSPARENT);
        player2Bitmap.eraseColor(Color.TRANSPARENT);

        // set score
        scoreText.setText(getString(R.string.score, player1.getWins(), player2.getWins()));

        // set font type for players
        updateTurnFont();

        // enable all tiles
        setAllTilesClickable(true);
    }

    public void updateTurnFont() {
        if(player1.isTurn())
            player1Text.setTypeface(null, Typeface.BOLD);
        else
            player1Text.setTypeface(null, Typeface.NORMAL);

        if(player2.isTurn())
            player2Text.setTypeface(null, Typeface.BOLD);
        else
            player2Text.setTypeface(null, Typeface.NORMAL);
    }

    public void endGame(Status status) {
        this.status = status;

        switch(status) {
            case WIN:
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
                break;
            case DRAW:
                conclusionText.setText(getString(R.string.nobody_won));
                conclusionText.setTextColor(Color.BLUE);
                break;
        }

        new DarkenAnimation(conclusionFrame, animation_duration*2);
        new DarkenAnimation(conclusionText, animation_duration*2);

        scoreText.setText(getString(R.string.score, player1.getWins(), player2.getWins()));

        setAllTilesClickable(false);
    }

    protected void setAllTilesClickable(boolean clickable) {
        for(int i = 0; i < 9; i++)
            if(tiles[i] == Symbol.NONE)
                tilesView[i].setClickable(clickable);
    }

    private void checkConditions() {
        for(int i = 0; i < patterns.length; i++) {
            if(tiles[patterns[i][0]] == tiles[patterns[i][1]] && tiles[patterns[i][0]] == tiles[patterns[i][2]] &&
                    tiles[patterns[i][0]] != Symbol.NONE &&
                    tiles[patterns[i][1]] != Symbol.NONE &&
                    tiles[patterns[i][2]] != Symbol.NONE) {
                drawLine(i);
                endGame(Status.WIN);
                break;
            }
        }

        if(noneCounter == 0 && status == Status.PLAYING)
            endGame(Status.DRAW);
    }

    public void makeMove(Player playerWithTurn, Player playerWithoutTurn, int i) {
        noneCounter--;

        tiles[i] = playerWithTurn.getSymbol();
        tilesView[i].setClickable(false);

        drawSymbol(tilesView[i], tilesBitmap[i], tiles[i]);

        checkConditions();

        if(status == Status.PLAYING) {
            playerWithTurn.setTurn(false);
            playerWithoutTurn.setTurn(true);
        }

        updateTurnFont();
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
