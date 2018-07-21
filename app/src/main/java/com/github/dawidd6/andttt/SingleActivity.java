package com.github.dawidd6.andttt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Objects;
import java.util.Random;

@SuppressWarnings({"SameParameterValue", "unused"})
public class SingleActivity extends BaseActivity {
    private int[][] btn_ids = {{R.id.b0, R.id.b1, R.id.b2}, {R.id.b3, R.id.b4, R.id.b5}, {R.id.b6, R.id.b7, R.id.b8}};
    private char[] smb = {'x', 'o'};
    private ImageButton[][] button = new ImageButton[3][3];
    private Random rand = new Random();
    private Paint paint;
    private Canvas canvas;
    private boolean isMyTurn; //need to save
    private boolean isThereAWinner = false; //need to save
    private boolean isThereADraw = false;
    private boolean restoredState;
    private int numberOfPlayerWins = 0; //need to save
    private int numberOfAndroidWins = 0; //need to save
    private char button_str[][]; //need to save
    private char playerChar; //need to save
    private char androidChar; //need to save
    private Bitmap xBitmap;
    private Bitmap oBitmap;
    private Bitmap boardBitmap; //need to save
    private Bitmap playerBitmap;
    private Bitmap androidBitmap;
    private ImageView boardImage;
    private ImageView playerImage;
    private ImageView androidImage;
    private TextView scoreText;
    private TextView conclusionText;
    private TextView playerText;
    private TextView androidText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        // findViews
        playerText = findViewById(R.id.playerText);
        androidText = findViewById(R.id.androidText);
        scoreText = findViewById(R.id.scoreText);
        conclusionText = findViewById(R.id.conclusionText);
        boardImage = findViewById(R.id.boardImage);
        playerImage = findViewById(R.id.playerImage);
        androidImage = findViewById(R.id.androidImage);
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++) {
            button[x][y] = findViewById(btn_ids[x][y]);
        }

        // draw symbols
        setPaint();
        drawCross();
        drawCircle();

        // restore state
        if(savedInstanceState != null) {
            restoredState = true;

            numberOfPlayerWins = savedInstanceState.getInt("numberOfPlayerWins");
            numberOfAndroidWins = savedInstanceState.getInt("numberOfAndroidWins");
            isMyTurn = savedInstanceState.getBoolean("isMyTurn");
            playerChar = savedInstanceState.getChar("playerChar");
            androidChar = savedInstanceState.getChar("androidChar");
            button_str = (char[][])savedInstanceState.getSerializable("button_str");
            boardBitmap = (Bitmap)savedInstanceState.get("boardBitmap");

            if(playerChar == 'x') {
                playerBitmap = xBitmap;
                androidBitmap = oBitmap;
            } else {
                playerBitmap = oBitmap;
                androidBitmap = xBitmap;
            }

            playerImage.setImageBitmap(playerBitmap);
            androidImage.setImageBitmap(androidBitmap);
            boardImage.setImageBitmap(boardBitmap);

            for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++) {
                button[x][y] = findViewById(btn_ids[x][y]);
                if(button_str[x][y] != '0') {
                    button[x][y].setImageBitmap(button_str[x][y] == playerChar ? playerBitmap : androidBitmap);
                    button[x][y].setClickable(false);
                }
            }

            checkConditions();

            restoredState = false;
        } else {
            restoredState = false;
            button_str = new char[3][3];
            restartGame(null);
        }

        scoreText.setText(getString(R.string.score, numberOfPlayerWins, numberOfAndroidWins));
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("numberOfPlayerWins", numberOfPlayerWins);
        bundle.putInt("numberOfAndroidWins", numberOfAndroidWins);
        bundle.putBoolean("isMyTurn", isMyTurn);
        bundle.putChar("playerChar", playerChar);
        bundle.putChar("androidChar", androidChar);
        bundle.putSerializable("button_str", button_str);
    }

    private void drawLine(String l) {
        paint.setColor(getResources().getColor(R.color.color_green));
        boardBitmap = Bitmap.createBitmap(304, 304, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(boardBitmap);

        if(Objects.equals(l, "nl"))
            canvas.drawLine(4, 4, 300, 300, paint);
        else if(Objects.equals(l, "nr"))
            canvas.drawLine(300, 4, 4, 300, paint);
        else if(Objects.equals(l, "h1"))
            canvas.drawLine(0, 50, 304, 50, paint);
        else if(Objects.equals(l, "h2"))
            canvas.drawLine(0, 152, 304, 152, paint);
        else if(Objects.equals(l, "h3"))
            canvas.drawLine(0, 254, 304, 254, paint);
        else if(Objects.equals(l, "v1"))
            canvas.drawLine(50, 0, 50, 304, paint);
        else if(Objects.equals(l, "v2"))
            canvas.drawLine(152, 0, 152, 304, paint);
        else if(Objects.equals(l, "v3"))
            canvas.drawLine(254, 0, 254, 304, paint);

        boardImage.setImageBitmap(boardBitmap);
        YoYo.with(Techniques.Landing).duration(animation_duration).playOn(boardImage);
    }

    private void setPaint() {
        paint = new Paint();
        paint.setColor(isNightModeEnabled ? Color.WHITE : Color.BLACK);
        paint.setStrokeWidth(6);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    private void drawCross() {
        xBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(xBitmap);
        canvas.drawLine(6, 6, 94, 94, paint);
        canvas.drawLine(94, 6, 6, 94, paint);
    }

    private void drawCircle() {
        oBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(oBitmap);
        canvas.drawCircle(50, 50, 45, paint);
    }

    private void randomTurn() {
        int k = rand.nextInt(2);
        isMyTurn = (k != 0);
    }

    private void randomSymbol() {
        int k = rand.nextInt(2);
        if(k == 0) {
            playerBitmap = xBitmap;
            androidBitmap = oBitmap;
            playerChar = 'x';
            androidChar = 'o';
        } else {
            playerBitmap = oBitmap;
            androidBitmap = xBitmap;
            playerChar = 'o';
            androidChar = 'x';
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void restartGame(View view) {
        if(view != null)
            YoYo.with(Techniques.StandUp).duration(animation_duration).playOn(view);

        if(isThereAWinner || isThereADraw) {
            YoYo.with(Techniques.FadeOut).duration(animation_duration).playOn(conclusionText);
            YoYo.with(Techniques.FadeOut).duration(animation_duration).playOn(boardImage);
        } else
            conclusionText.setVisibility(View.GONE);
        restoredState = false;
        isThereAWinner = false;
        isThereADraw = false;
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++) {
            YoYo.with(Techniques.FadeOut).duration(animation_duration).playOn(button[x][y]);
            button_str[x][y] = '0';
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
                    button[x][y].setImageBitmap(null);
                boardImage.setImageBitmap(null);
                markEnabledAll();
                if(!isMyTurn)
                    compMove();
            }
        }, animation_duration);

        randomTurn();
        randomSymbol();
        playerImage.setImageBitmap(playerBitmap);
        YoYo.with(Techniques.Wobble).duration(animation_duration).playOn(playerImage);
        androidImage.setImageBitmap(androidBitmap);
        YoYo.with(Techniques.Wobble).duration(animation_duration).playOn(androidImage);

        if(isMyTurn) {
            playerText.setTypeface(null, Typeface.BOLD);
            androidText.setTypeface(null, Typeface.NORMAL);
        } else {
            playerText.setTypeface(null, Typeface.NORMAL);
            androidText.setTypeface(null, Typeface.BOLD);
        }
    }

    private void markDisabledAll() {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            button[x][y].setClickable(false);
    }

    private void markEnabledAll() {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            button[x][y].setClickable(true);
    }

    private void doWin(String l) {
        isThereAWinner = true;
        if(!restoredState) {
            if (!isMyTurn) {
                numberOfPlayerWins++;
                conclusionText.setText(getString(R.string.you_won));
                conclusionText.setTextColor(Color.GREEN);
            } else {
                numberOfAndroidWins++;
                conclusionText.setText(getString(R.string.android_won));
                conclusionText.setTextColor(Color.RED);
            }
            scoreText.setText(getString(R.string.score, numberOfPlayerWins, numberOfAndroidWins));
        }

        conclusionText.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn).duration(animation_duration).playOn(conclusionText);
        YoYo.with(Techniques.Pulse).duration(animation_duration).playOn(scoreText);

        markDisabledAll();
        drawLine(l);
    }

    private void checkConditions() {
        for(int i = 0; i < 2; i++) {
            if (button_str[0][0] == smb[i] && button_str[0][1] == smb[i] && button_str[0][2] == smb[i])
                doWin("h1");
            else if (button_str[1][0] == smb[i] && button_str[1][1] == smb[i] && button_str[1][2] == smb[i])
                doWin("h2");
            else if (button_str[2][0] == smb[i] && button_str[2][1] == smb[i] && button_str[2][2] == smb[i])
                doWin("h3");
            else if (button_str[0][0] == smb[i] && button_str[1][0] == smb[i] && button_str[2][0] == smb[i])
                doWin("v1");
            else if (button_str[0][1] == smb[i] && button_str[1][1] == smb[i] && button_str[2][1] == smb[i])
                doWin("v2");
            else if (button_str[0][2] == smb[i] && button_str[1][2] == smb[i] && button_str[2][2] == smb[i])
                doWin("v3");
            else if (button_str[0][0] == smb[i] && button_str[1][1] == smb[i] && button_str[2][2] == smb[i])
                doWin("nl");
            else if (button_str[0][2] == smb[i] && button_str[1][1] == smb[i] && button_str[2][0] == smb[i])
                doWin("nr");
        }

        if(!yallGotAnymoreOfThemButtons() && !isThereAWinner) {
            isThereADraw = true;
            conclusionText.setText(getString(R.string.nobody_won));
            conclusionText.setTextColor(Color.BLUE);
            conclusionText.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.ZoomIn).duration(animation_duration).playOn(conclusionText);
        }
    }

    private int gimmeFreeRoomHorizon(int x) {
        for(int y = 0; y < 3; y++)
            if(button_str[x][y] == '0')
                return y;
        return -1;
    }

    private int gimmeFreeRoomVertical(int y) {
        for(int x = 0; x < 3; x++)
            if(button_str[x][y] == '0')
                return x;
        return -1;
    }

    private void compMove() {
        int xx = -1;
        int yy = -1;
        int y = 0;
        int x = 0;
        int count_my = 0;
        int count_comp = 0;
        int count_zero = 0;
        boolean horizon = false;
        boolean computed = false;
        char smb[] = new char[2];
        smb[0] = androidChar;
        smb[1] = playerChar;

        for(int i = 0; i < 2; i++) {
            if(button_str[0][0] == smb[i] && button_str[1][1] == smb[i] && button_str[2][2] == '0') {
                computed = true;
                xx = 2;
                yy = 2;
                break;
            } else if(button_str[1][1] == smb[i] && button_str[2][2] == smb[i] && button_str[0][0] == '0') {
                computed = true;
                xx = 0;
                yy = 0;
                break;
            } else if(button_str[0][0] == smb[i] && button_str[2][2] == smb[i] && button_str[1][1] == '0') {
                computed = true;
                xx = 1;
                yy = 1;
                break;
            } else if(button_str[0][2] == smb[i] && button_str[2][0] == smb[i] && button_str[1][1] == '0') {
                computed = true;
                xx = 1;
                yy = 1;
                break;
            } else if(button_str[1][1] == smb[i] && button_str[2][0] == smb[i] && button_str[0][2] == '0') {
                computed = true;
                xx = 0;
                yy = 2;
                break;
            } else if(button_str[0][2] == smb[i] && button_str[1][1] == smb[i] && button_str[2][0] == '0') {
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
                    if(button_str[x][y] == playerChar)
                        count_my++;
                    else if(button_str[x][y] == androidChar)
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
                    if(button_str[x][y] == playerChar)
                        count_my++;
                    else if(button_str[x][y] == androidChar)
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
                    if(button_str[x][y] == playerChar)
                        count_my++;
                    else if(button_str[x][y] == androidChar)
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
                    if(button_str[x][y] == playerChar)
                        count_my++;
                    else if(button_str[x][y] == androidChar)
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
            } while(button_str[xx][yy] != '0');


        button_str[xx][yy] = androidChar;
        isMyTurn = true;
        button[xx][yy].setClickable(false);

        button[xx][yy].setImageBitmap(androidBitmap);
        YoYo.with(Techniques.Landing).duration(animation_duration).playOn(button[xx][yy]);

        checkConditions();
    }

    private boolean yallGotAnymoreOfThemButtons() {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            if(button_str[x][y] == '0')
                return true;
        return false;
    }

    public void myMove(View view) {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            if(button[x][y] == findViewById(view.getId())) {
                button[x][y].setImageBitmap(playerBitmap);
                button_str[x][y] = playerChar;
                isMyTurn = false;
                button[x][y].setClickable(false);
                checkConditions();
                if(yallGotAnymoreOfThemButtons())
                    if(!isThereAWinner)
                        compMove();
                YoYo.with(Techniques.Landing).duration(animation_duration).playOn(button[x][y]);
                break;
            }
    }

    public void onClickReturn(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}