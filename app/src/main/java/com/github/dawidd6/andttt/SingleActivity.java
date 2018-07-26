package com.github.dawidd6.andttt;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Objects;
import java.util.Random;

@SuppressWarnings({"SameParameterValue", "unused"})
public class SingleActivity extends BaseActivity {
    
    private enum Status {WIN, DRAW, PLAYING}
    private enum Turn {PLAYER, ANDROID}
    
    private Status status = Status.PLAYING;
    private Turn turn;
    
    private Random rand = new Random();
    
    private int numberOfPlayerWins = 0;
    private int numberOfAndroidWins = 0;
    
    private char symbolChar[] = new char[2];
    private char tileChar[][] = new char[3][3];
    private char playerChar;
    private char androidChar;
    
    private SymbolView[][] tileView = new SymbolView[3][3];
    private SymbolView playerView;
    private SymbolView androidView;
    private SymbolView boardView;

    private TextView scoreText;
    private TextView conclusionText;
    private TextView playerText;
    private TextView androidText;

    private SymbolView.Mode tileSymbol;
    private SymbolView.Mode playerSymbol;
    private SymbolView.Mode androidSymbol;

    private int tile_dimen;
    private int symbol_dimen;
    private int board_dimen;
    private int frame_dimen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        // get dimens
        tile_dimen = getResources().getDimensionPixelSize(R.dimen.tile_dimen);
        symbol_dimen = getResources().getDimensionPixelSize(R.dimen.symbol_dimen);
        board_dimen = getResources().getDimensionPixelSize(R.dimen.board_dimen);
        frame_dimen = getResources().getDimensionPixelSize(R.dimen.frame_dimen);

        // findViews
        playerText = findViewById(R.id.playerText);
        androidText = findViewById(R.id.androidText);
        scoreText = findViewById(R.id.scoreText);
        conclusionText = findViewById(R.id.conclusionText);
        playerView = findViewById(R.id.playerView);
        androidView = findViewById(R.id.androidView);
        boardView = findViewById(R.id.boardView);
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++) {
            tileView[x][y] = findViewById(getResources().getIdentifier("b" + x + y, "id", getPackageName()));
            tileView[x][y].setColor(colorForeground);
            tileView[x][y].setThickness(12);
            tileView[x][y].setSize(tile_dimen);
        }

        // drawing stuff
        boardView.setMode(SymbolView.Mode.LINE);
        boardView.setColor(ContextCompat.getColor(this, R.color.color_green));
        boardView.setThickness(20);
        boardView.setSize(board_dimen);
        playerView.setColor(colorForeground);
        playerView.setThickness(6);
        playerView.setSize(symbol_dimen);
        androidView.setColor(colorForeground);
        androidView.setThickness(6);
        androidView.setSize(symbol_dimen);

        restartGame(null);

        scoreText.setText(getString(R.string.score, numberOfPlayerWins, numberOfAndroidWins));
    }

    private void drawLine(String l) {
        if(Objects.equals(l, "nl"))
            boardView.setLinePoints(0, 0, board_dimen, board_dimen);
        else if(Objects.equals(l, "nr"))
            boardView.setLinePoints(board_dimen, 0, 0, board_dimen);
        else if(Objects.equals(l, "h1"))
            boardView.setLinePoints(0, tile_dimen / 2, board_dimen, tile_dimen / 2);
        else if(Objects.equals(l, "h2"))
            boardView.setLinePoints(0, board_dimen / 2, board_dimen, board_dimen / 2);
        else if(Objects.equals(l, "h3"))
            boardView.setLinePoints(0, board_dimen - (tile_dimen / 2), board_dimen, board_dimen - (tile_dimen / 2));
        else if(Objects.equals(l, "v1"))
            boardView.setLinePoints(tile_dimen / 2, 0, tile_dimen / 2, board_dimen);
        else if(Objects.equals(l, "v2"))
            boardView.setLinePoints(board_dimen / 2, 0, board_dimen / 2, board_dimen);
        else if(Objects.equals(l, "v3"))
            boardView.setLinePoints(board_dimen - (tile_dimen / 2), 0, board_dimen - (tile_dimen / 2), board_dimen);

        new SymbolAnimation(boardView).setDuration(animation_duration);
    }

    private void randomTurn() {
        turn = rand.nextInt(2) == 0 ? Turn.PLAYER : Turn.ANDROID;
    }

    private void randomSymbol() {
        if(rand.nextInt(2) == 0) {
            playerChar = 'x';
            androidChar = 'o';
            playerSymbol = SymbolView.Mode.CROSS;
            androidSymbol = SymbolView.Mode.CIRCLE;
        } else {
            playerChar = 'o';
            androidChar = 'x';
            playerSymbol = SymbolView.Mode.CIRCLE;
            androidSymbol = SymbolView.Mode.CROSS;
        }
        symbolChar[0] = androidChar;
        symbolChar[1] = playerChar;
    }

    @SuppressWarnings("WeakerAccess")
    public void restartGame(View view) {
        if(status != Status.PLAYING) {
            YoYo.with(Techniques.FadeOut).duration(animation_duration).playOn(conclusionText);
            status = Status.PLAYING;
        } else
            conclusionText.setVisibility(View.GONE);

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++) {
                tileChar[x][y] = '0';
                tileView[x][y].clear(Color.TRANSPARENT);
            }

        boardView.clear(Color.TRANSPARENT);

        markEnabledAll();
        randomTurn();
        randomSymbol();

        playerView.setMode(playerSymbol);
        new SymbolAnimation(playerView).setDuration(animation_duration);
        androidView.setMode(androidSymbol);
        new SymbolAnimation(androidView).setDuration(animation_duration);

        if(turn == Turn.PLAYER) {
            playerText.setTypeface(null, Typeface.BOLD);
            androidText.setTypeface(null, Typeface.NORMAL);
        } else {
            playerText.setTypeface(null, Typeface.NORMAL);
            androidText.setTypeface(null, Typeface.BOLD);
            androidMove();
        }
    }

    private void markDisabledAll() {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            tileView[x][y].setClickable(false);
    }

    private void markEnabledAll() {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            tileView[x][y].setClickable(true);
    }

    private void doWin(String l) {
        status = Status.WIN;

        if (turn == Turn.ANDROID) {
            numberOfPlayerWins++;
            conclusionText.setText(getString(R.string.you_won));
            conclusionText.setTextColor(Color.GREEN);
        } else {
            numberOfAndroidWins++;
            conclusionText.setText(getString(R.string.android_won));
            conclusionText.setTextColor(Color.RED);
        }

        scoreText.setText(getString(R.string.score, numberOfPlayerWins, numberOfAndroidWins));

        conclusionText.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn).duration(animation_duration).playOn(conclusionText);
        YoYo.with(Techniques.Pulse).duration(animation_duration).playOn(scoreText);

        markDisabledAll();
        drawLine(l);
    }

    private void checkConditions() {
        for(int i = 0; i < 2; i++) {
            if (tileChar[0][0] == symbolChar[i] && tileChar[0][1] == symbolChar[i] && tileChar[0][2] == symbolChar[i])
                doWin("h1");
            else if (tileChar[1][0] == symbolChar[i] && tileChar[1][1] == symbolChar[i] && tileChar[1][2] == symbolChar[i])
                doWin("h2");
            else if (tileChar[2][0] == symbolChar[i] && tileChar[2][1] == symbolChar[i] && tileChar[2][2] == symbolChar[i])
                doWin("h3");
            else if (tileChar[0][0] == symbolChar[i] && tileChar[1][0] == symbolChar[i] && tileChar[2][0] == symbolChar[i])
                doWin("v1");
            else if (tileChar[0][1] == symbolChar[i] && tileChar[1][1] == symbolChar[i] && tileChar[2][1] == symbolChar[i])
                doWin("v2");
            else if (tileChar[0][2] == symbolChar[i] && tileChar[1][2] == symbolChar[i] && tileChar[2][2] == symbolChar[i])
                doWin("v3");
            else if (tileChar[0][0] == symbolChar[i] && tileChar[1][1] == symbolChar[i] && tileChar[2][2] == symbolChar[i])
                doWin("nl");
            else if (tileChar[0][2] == symbolChar[i] && tileChar[1][1] == symbolChar[i] && tileChar[2][0] == symbolChar[i])
                doWin("nr");
        }

        if(!yallGotAnymoreOfThemButtons() && status != Status.WIN) {
            status = Status.DRAW;
            conclusionText.setText(getString(R.string.nobody_won));
            conclusionText.setTextColor(Color.BLUE);
            conclusionText.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.ZoomIn).duration(animation_duration).playOn(conclusionText);
        }
    }

    private int gimmeFreeRoomHorizon(int x) {
        for(int y = 0; y < 3; y++)
            if(tileChar[x][y] == '0')
                return y;
        return -1;
    }

    private int gimmeFreeRoomVertical(int y) {
        for(int x = 0; x < 3; x++)
            if(tileChar[x][y] == '0')
                return x;
        return -1;
    }

    private void androidMove() {
        int xx = -1;
        int yy = -1;
        int y = 0;
        int x = 0;
        int count_my = 0;
        int count_comp = 0;
        int count_zero = 0;
        boolean horizon = false;
        boolean computed = false;
        
        for(int i = 0; i < 2; i++) {
            if(tileChar[0][0] == symbolChar[i] && tileChar[1][1] == symbolChar[i] && tileChar[2][2] == '0') {
                computed = true;
                xx = 2;
                yy = 2;
                break;
            } else if(tileChar[1][1] == symbolChar[i] && tileChar[2][2] == symbolChar[i] && tileChar[0][0] == '0') {
                computed = true;
                xx = 0;
                yy = 0;
                break;
            } else if(tileChar[0][0] == symbolChar[i] && tileChar[2][2] == symbolChar[i] && tileChar[1][1] == '0') {
                computed = true;
                xx = 1;
                yy = 1;
                break;
            } else if(tileChar[0][2] == symbolChar[i] && tileChar[2][0] == symbolChar[i] && tileChar[1][1] == '0') {
                computed = true;
                xx = 1;
                yy = 1;
                break;
            } else if(tileChar[1][1] == symbolChar[i] && tileChar[2][0] == symbolChar[i] && tileChar[0][2] == '0') {
                computed = true;
                xx = 0;
                yy = 2;
                break;
            } else if(tileChar[0][2] == symbolChar[i] && tileChar[1][1] == symbolChar[i] && tileChar[2][0] == '0') {
                computed = true;
                xx = 2;
                yy = 0;
                break;
            }
        }

        if(!computed)
            for(x = 0; x < 3; x++) {
                count_my = 0;
                count_comp = 0;
                count_zero = 0;
                for(y = 0; y < 3; y++) {
                    if(tileChar[x][y] == playerChar)
                        count_my++;
                    else if(tileChar[x][y] == androidChar)
                        count_comp++;
                    else
                        count_zero++;
                }
                if(count_my == 0 && count_comp == 2 && count_zero == 1) {
                    computed = true;
                    horizon = true;
                    break;
                }
            }

        if(!computed)
            for(x = 0; x < 3; x++) {
                count_my = 0;
                count_comp = 0;
                count_zero = 0;
                for(y = 0; y < 3; y++) {
                    if(tileChar[x][y] == playerChar)
                        count_my++;
                    else if(tileChar[x][y] == androidChar)
                        count_comp++;
                    else
                        count_zero++;
                }
                if(count_my == 2 && count_comp == 0 && count_zero == 1) {
                    computed = true;
                    horizon = true;
                    break;
                }
            }

        if(!computed)
            for(y = 0; y < 3; y++) {
                count_my = 0;
                count_comp = 0;
                count_zero = 0;
                for(x = 0; x < 3; x++) {
                    if(tileChar[x][y] == playerChar)
                        count_my++;
                    else if(tileChar[x][y] == androidChar)
                        count_comp++;
                    else
                        count_zero++;
                }
                if(count_my == 0 && count_comp == 2 && count_zero == 1) {
                    computed = true;
                    horizon = false;
                    break;
                }
            }

        if(!computed)
            for(y = 0; y < 3; y++) {
                count_my = 0;
                count_comp = 0;
                count_zero = 0;
                for(x = 0; x < 3; x++) {
                    if(tileChar[x][y] == playerChar)
                        count_my++;
                    else if(tileChar[x][y] == androidChar)
                        count_comp++;
                    else
                        count_zero++;
                }
                if(count_my == 2 && count_comp == 0 && count_zero == 1) {
                    //noinspection UnusedAssignment
                    computed = true;
                    horizon = false;
                    break;
                }
            }

        if(count_my == 0 && count_comp == 2 && count_zero == 1) {
            if(horizon) {
                yy = gimmeFreeRoomHorizon(x);
                xx = x;
            } else {
                xx = gimmeFreeRoomVertical(y);
                yy = y;
            }
        } else if(count_my == 2 && count_comp == 0 && count_zero == 1) {
            if(horizon) {
                yy = gimmeFreeRoomHorizon(x);
                xx = x;
            } else {
                xx = gimmeFreeRoomVertical(y);
                yy = y;
            }
        } else //noinspection ConstantConditions
            if(xx == -1 || yy == -1)
            do {
                xx = rand.nextInt(3);
                yy = rand.nextInt(3);
            } while(tileChar[xx][yy] != '0');


        tileChar[xx][yy] = androidChar;
        turn = Turn.PLAYER;
        tileView[xx][yy].setClickable(false);

        tileView[xx][yy].setMode(androidSymbol);
        new SymbolAnimation(tileView[xx][yy]).setDuration(animation_duration);

        checkConditions();
    }

    private boolean yallGotAnymoreOfThemButtons() {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            if(tileChar[x][y] == '0')
                return true;
        return false;
    }

    public void playerMove(View view) {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            if(tileView[x][y] == findViewById(view.getId())) {
                tileChar[x][y] = playerChar;
                turn = Turn.ANDROID;
                tileView[x][y].setClickable(false);

                tileView[x][y].setMode(playerSymbol);
                new SymbolAnimation(tileView[x][y]).setDuration(animation_duration);

                checkConditions();
                if(yallGotAnymoreOfThemButtons() && status == Status.PLAYING)
                    androidMove();
                break;
            }
    }
}