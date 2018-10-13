package com.github.dawidd6.andttt;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

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

    protected enum Statuses {
        PLAYING,
        WIN,
        DRAW,
    }

    protected Statuses status;

    protected Random rand;

    protected Symbols tiles[];

    private SymbolView tilesView[];
    private SymbolView boardView;

    private TextView scoreText;
    private TextView conclusionText;

    private FrameLayout conclusionFrame;

    private int tile_dimen;
    private int symbol_dimen;
    private int board_dimen;
    private int frame_dimen;

    private int noneCounter;

    private boolean bool;
    protected boolean player1Turn;

    protected Player player1;
    protected Player player2;

    private TextView player1Text;
    private TextView player2Text;
    private SymbolView player1View;
    private SymbolView player2View;

    private Animator darken;
    private Animator lighten;
    private AnimatorSet pulse;

    private MainActivity activity;

    protected class Player {
        private TextView text;
        private SymbolView view;
        private Symbols symbol;
        private String name;
        private int string;
        private int wins;
        private int color;

        public Player(TextView text, SymbolView view, int string) {
            this.wins = 0;
            this.string = string;

            this.text = text;
            this.view = view;

            this.view.setColor(activity.colorForeground);
            this.view.setThickness(2 + frame_dimen);
            this.view.setSize(symbol_dimen);

            this.text.setText(getString(string, name));
        }

        public void addWin() {
            this.wins++;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }

        public void setSymbol(Symbols symbol) {
            this.symbol = symbol;
            view.setMode(symbol);
            new SymbolAnimation(view).setDuration(activity.animation_duration);
        }

        public Symbols getSymbol() {
            return symbol;
        }

        public void setTurn(boolean turn) {
            this.text.setTypeface(null, turn ? Typeface.BOLD : Typeface.NORMAL);
        }

        public void setName(String name) {
            this.name = name;
            this.text.setText(getString(string, name));
        }

        public String getName() {
            return name;
        }
    }

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

        activity = (MainActivity)getActivity();

        // set on clicks listeners
        Button restartButton = view.findViewById(R.id.restartButton);
        restartButton.setOnClickListener(this::onClickRestart);

        // load animations
        darken = AnimatorInflater.loadAnimator(activity, R.animator.darken);
        lighten = AnimatorInflater.loadAnimator(activity, R.animator.lighten);
        pulse = (AnimatorSet)AnimatorInflater.loadAnimator(activity, R.animator.pulse);

        // init
        rand = new Random();
        tiles = new Symbols[9];
        tilesView = new SymbolView[9];
        status = Statuses.PLAYING;

        // get dimens
        tile_dimen = getResources().getDimensionPixelSize(R.dimen.tile_dimen);
        symbol_dimen = getResources().getDimensionPixelSize(R.dimen.symbol_dimen);
        board_dimen = getResources().getDimensionPixelSize(R.dimen.board_dimen);
        frame_dimen = getResources().getDimensionPixelSize(R.dimen.frame_dimen);

        // initialize players
        player1Text = view.findViewById(R.id.player1Text);
        player2Text = view.findViewById(R.id.player2Text);
        player1View = view.findViewById(R.id.player1View);
        player2View = view.findViewById(R.id.player2View);

        player1 = new Player(player1Text, player1View, R.string.player1);
        player2 = new Player(player2Text, player2View, R.string.player2);
        player1.setColor(Color.GREEN);
        player2.setColor(Color.RED);

        // find views + init tiles
        scoreText = view.findViewById(R.id.scoreText);
        conclusionFrame = view.findViewById(R.id.conclusionFrame);
        conclusionText = view.findViewById(R.id.conclusionText);
        boardView = view.findViewById(R.id.boardView);
        for(int i = 0; i < 9; i++) {
            tilesView[i] = view.findViewById(getResources().getIdentifier("b" + i, "id", activity.getPackageName()));
            tilesView[i].setColor(activity.colorForeground);
            tilesView[i].setThickness(12);
            tilesView[i].setSize(tile_dimen);
            tilesView[i].setOnClickListener(this::onClickTile);
        }

        // game drawing stuff
        boardView.setMode(Symbols.LINE);
        boardView.setColor(ContextCompat.getColor(activity, R.color.color_green));
        boardView.setThickness(20);
        boardView.setSize(board_dimen);

        // starting point
        restartGame();
    }

    private void drawLine(int i) {
        switch(i) {
            case 0: // h1
                boardView.setLinePoints(0, tile_dimen / 2, board_dimen, tile_dimen / 2);
                break;
            case 1: // h2
                boardView.setLinePoints(0, board_dimen / 2, board_dimen, board_dimen / 2);
                break;
            case 2: // h3
                boardView.setLinePoints(0, board_dimen - (tile_dimen / 2), board_dimen, board_dimen - (tile_dimen / 2));
                break;
            case 3: // v1
                boardView.setLinePoints(tile_dimen / 2, 0, tile_dimen / 2, board_dimen);
                break;
            case 4: // v2
                boardView.setLinePoints(board_dimen / 2, 0, board_dimen / 2, board_dimen);
                break;
            case 5: // v3
                boardView.setLinePoints(board_dimen - (tile_dimen / 2), 0, board_dimen - (tile_dimen / 2), board_dimen);
                break;
            case 6: // nl
                boardView.setLinePoints(0, 0, board_dimen, board_dimen);
                break;
            case 7: // nr
                boardView.setLinePoints(board_dimen, 0, 0, board_dimen);
                break;
        }

        new SymbolAnimation(boardView).setDuration(activity.animation_duration);
    }

    public void restartGame() {
        if(status == Statuses.PLAYING) {
            conclusionText.setAlpha(0);
            conclusionFrame.setAlpha(0);
        }
        else {
            YoYo.with(new FadeOutAnimator()).duration(activity.animation_duration).playOn(conclusionText);
            YoYo.with(new FadeOutAnimator()).duration(activity.animation_duration).playOn(conclusionFrame);
            status = Statuses.PLAYING;
        }

        noneCounter = tiles.length;

        player1Turn = rand.nextBoolean();
        player1.setTurn(player1Turn);
        player2.setTurn(!player1Turn);

        bool = rand.nextBoolean();
        player1.setSymbol(bool ? Symbols.CIRCLE : Symbols.CROSS);
        player2.setSymbol(!bool ? Symbols.CIRCLE : Symbols.CROSS);

        for(int i = 0; i < 9; i++) {
            tilesView[i].clear();
            tiles[i] = Symbols.NONE;
        }

        boardView.clear();

        scoreText.setText(getString(R.string.score, player1.wins, player2.wins));

        setAllTilesClickable(true);
    }

    public void endGame(Statuses stat) {
        status = stat;

        switch(status) {
            case WIN:
                if(player1Turn) {
                    player1.addWin();
                    conclusionText.setText(getString(R.string.player_won, player1.getName()));
                    conclusionText.setTextColor(player1.getColor());
                } else {
                    player2.addWin();
                    conclusionText.setText(getString(R.string.player_won, player2.getName()));
                    conclusionText.setTextColor(player2.getColor());

                }
                YoYo.with(Techniques.Pulse).duration(activity.animation_duration).playOn(scoreText);
                break;
            case DRAW:
                conclusionText.setText(getString(R.string.nobody_won));
                conclusionText.setTextColor(Color.BLUE);
                break;
        }

        YoYo.with(new FadeInAnimator()).duration(activity.animation_duration).playOn(conclusionFrame);
        YoYo.with(new FadeInAnimator()).duration(activity.animation_duration).playOn(conclusionText);

        scoreText.setText(getString(R.string.score, player1.wins, player2.wins));
        setAllTilesClickable(false);
    }

    private void setAllTilesClickable(boolean enabled) {
        for(int i = 0; i < 9; i++)
            tilesView[i].setClickable(enabled);
    }

    private void checkConditions() {
        for(int i = 0; i < patterns.length; i++) {
            if(tiles[patterns[i][0]] == tiles[patterns[i][1]] && tiles[patterns[i][0]] == tiles[patterns[i][2]] &&
                    tiles[patterns[i][0]] != Symbols.NONE &&
                    tiles[patterns[i][1]] != Symbols.NONE &&
                    tiles[patterns[i][2]] != Symbols.NONE) {
                drawLine(i);
                endGame(Statuses.WIN);
                break;
            }
        }

        if(noneCounter == 0 && status == Statuses.PLAYING)
            endGame(Statuses.DRAW);
    }

    public void makeMove(Player player, int i) {
        noneCounter--;

        tiles[i] = player.getSymbol();
        tilesView[i].setClickable(false);
        tilesView[i].setMode(tiles[i]);
        new SymbolAnimation(tilesView[i]).setDuration(activity.animation_duration);
        player.setTurn(false);

        checkConditions();

        if(status == Statuses.PLAYING) {
            player1Turn = !player1Turn;
            player1.setTurn(player1Turn);
            player2.setTurn(!player1Turn);
        }
    }

    public void onClickTile(View view) {
        int i = Character.getNumericValue(getResources().getResourceEntryName(view.getId()).charAt(1));
        makeMove(player1Turn ? player1 : player2, i);
    }

    public void onClickRestart(View view) {
        restartGame();
    }
}
