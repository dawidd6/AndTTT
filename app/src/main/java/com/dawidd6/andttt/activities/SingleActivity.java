package com.dawidd6.andttt.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dawidd6.andttt.R;

import java.util.Objects;
import java.util.Random;

@SuppressWarnings({"SameParameterValue", "unused"})
public class SingleActivity extends Activity
{
    private final int[][] btn_ids = {{R.id.b0, R.id.b1, R.id.b2}, {R.id.b3, R.id.b4, R.id.b5}, {R.id.b6, R.id.b7, R.id.b8}};
    private final char[] smb = {'x', 'o'};
    private final ImageButton[][] button = new ImageButton[3][3];
    private final Random rand = new Random();
    private boolean isMyTurn; //need to save
    private boolean isThereAWinner; //need to save
    private boolean isNightModeEnabled;
    private boolean restoredState;
    private int numberOfHumanWins = 0; //need to save
    private int numberOfAndroidWins = 0; //need to save
    private char button_str[][]; //need to save
    private char char_my; //need to save
    private char char_comp; //need to save
    private Bitmap bitmap_x;
    private Bitmap bitmap_o;
    private Bitmap bitmap_board; //need to save
    private Bitmap bitmap_my;
    private Bitmap bitmap_comp;
    private ImageView board;
    private ImageView player_symbol;
    private ImageView android_symbol;
    private Paint paint;
    private Canvas canvas;
    private TextView score;
    private TextView conclusion;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // night mode stuff and set theme
        isNightModeEnabled = getIntent().getBooleanExtra("night_mode", false);
        setTheme(isNightModeEnabled ? R.style.theme_dark : R.style.theme_light);

        // standard
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        // findViews
        conclusion = findViewById(R.id.conclusion);
        board = findViewById(R.id.board);
        score = findViewById(R.id.score);
        player_symbol = findViewById(R.id.player_symbol);
        android_symbol = findViewById(R.id.android_symbol);
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            button[x][y] = findViewById(btn_ids[x][y]);

        // draw symbols
        setPaint();
        drawCross();
        drawCircle();

        // restore state
        if(savedInstanceState != null)
        {
            restoredState = true;

            numberOfHumanWins = savedInstanceState.getInt("numberOfHumanWins");
            numberOfAndroidWins = savedInstanceState.getInt("numberOfAndroidWins");
            isMyTurn = savedInstanceState.getBoolean("isMyTurn");
            char_my = savedInstanceState.getChar("char_my");
            char_comp = savedInstanceState.getChar("char_comp");
            button_str = (char[][])savedInstanceState.getSerializable("button_str");
            bitmap_board = (Bitmap)savedInstanceState.get("bitmap_board");

            if(char_my == 'x')
            {
                bitmap_my = bitmap_x;
                bitmap_comp = bitmap_o;
            }
            else
            {
                bitmap_my = bitmap_o;
                bitmap_comp = bitmap_x;
            }

            player_symbol.setImageBitmap(bitmap_my);
            android_symbol.setImageBitmap(bitmap_comp);
            board.setImageBitmap(bitmap_board);

            for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            {
                button[x][y] = findViewById(btn_ids[x][y]);
                if(button_str[x][y] != '0')
                {
                    button[x][y].setImageBitmap(button_str[x][y] == char_my ? bitmap_my : bitmap_comp);
                    button[x][y].setClickable(false);
                }
            }

            checkConditions();

            restoredState = false;
        }
        else
        {
            restoredState = false;
            button_str = new char[3][3];
            restartGame(null);
        }

        score.setText(getString(R.string.score, numberOfHumanWins, numberOfAndroidWins));
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle)
    {
        bundle.putInt("numberOfHumanWins", numberOfHumanWins);
        bundle.putInt("numberOfAndroidWins", numberOfAndroidWins);
        bundle.putBoolean("isMyTurn", isMyTurn);
        bundle.putChar("char_my", char_my);
        bundle.putChar("char_comp", char_comp);
        bundle.putSerializable("button_str", button_str);
    }

    private void drawLine(String l)
    {
        paint.setColor(Color.parseColor("#00B75B"));
        bitmap_board = Bitmap.createBitmap(304, 304, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap_board);

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

        board.setImageBitmap(bitmap_board);
    }

    private void setPaint()
    {
        paint = new Paint();
        paint.setColor(isNightModeEnabled ? Color.WHITE : Color.BLACK);
        paint.setStrokeWidth(6);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    private void drawCross()
    {

        bitmap_x = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap_x);
        canvas.drawLine(6, 6, 94, 94, paint);
        canvas.drawLine(94, 6, 6, 94, paint);
    }

    private void drawCircle()
    {
        bitmap_o = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap_o);
        canvas.drawCircle(50, 50, 45, paint);
    }

    private void randomTurn()
    {
        int k = rand.nextInt(2);
        isMyTurn = (k != 0);
    }

    private void randomSymbol()
    {
        int k = rand.nextInt(2);
        if(k == 0)
        {
            bitmap_my = bitmap_x;
            bitmap_comp = bitmap_o;
            char_my = 'x';
            char_comp = 'o';
        }
        else
        {
            bitmap_my = bitmap_o;
            bitmap_comp = bitmap_x;
            char_my = 'o';
            char_comp = 'x';
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void restartGame(View view)
    {
        conclusion.setVisibility(View.GONE);
        restoredState = false;
        isThereAWinner = false;
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
        {
            button[x][y].setImageBitmap(null);
            button_str[x][y] = '0';
        }
        randomTurn();
        randomSymbol();
        markEnabledAll();
        board.setImageBitmap(null);
        player_symbol.setImageBitmap(bitmap_my);
        android_symbol.setImageBitmap(bitmap_comp);
        if(!isMyTurn)
            compMove();
    }

    private void markDisabledAll()
    {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            button[x][y].setClickable(false);
    }

    private void markEnabledAll()
    {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            button[x][y].setClickable(true);
    }

    private void doWin(String l)
    {
        isThereAWinner = true;
        if(!restoredState)
        {
            if (!isMyTurn)
            {
                numberOfHumanWins++;
                conclusion.setText(getString(R.string.you_won));
                conclusion.setTextColor(Color.GREEN);
            }
            else
            {
                numberOfAndroidWins++;
                conclusion.setText(getString(R.string.android_won));
                conclusion.setTextColor(Color.RED);
            }
            score.setText(getString(R.string.score, numberOfHumanWins, numberOfAndroidWins));

        }

        conclusion.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomIn).duration(1000).playOn(conclusion);

        markDisabledAll();
        drawLine(l);
    }

    private void checkConditions()
    {
        for(int i = 0; i < 2; i++)
        {
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
    }

    private int gimmeFreeRoomHorizon(int x)
    {
        for(int y = 0; y < 3; y++)
            if(button_str[x][y] == '0')
                return y;
        return -1;
    }

    private int gimmeFreeRoomVertical(int y)
    {
        for(int x = 0; x < 3; x++)
            if(button_str[x][y] == '0')
            {
                return x;
            }
        return -1;
    }

    private void compMove()
    {
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
        smb[0] = char_comp;
        smb[1] = char_my;

        for(int i = 0; i < 2; i++)
        {
            if(button_str[0][0] == smb[i] && button_str[1][1] == smb[i] && button_str[2][2] == '0')
            {
                computed = true;
                xx = 2;
                yy = 2;
                break;
            }
            else if(button_str[1][1] == smb[i] && button_str[2][2] == smb[i] && button_str[0][0] == '0')
            {
                computed = true;
                xx = 0;
                yy = 0;
                break;
            }
            else if(button_str[0][0] == smb[i] && button_str[2][2] == smb[i] && button_str[1][1] == '0')
            {
                computed = true;
                xx = 1;
                yy = 1;
                break;
            }
            else if(button_str[0][2] == smb[i] && button_str[2][0] == smb[i] && button_str[1][1] == '0')
            {
                computed = true;
                xx = 1;
                yy = 1;
                break;
            }
            else if(button_str[1][1] == smb[i] && button_str[2][0] == smb[i] && button_str[0][2] == '0')
            {
                computed = true;
                xx = 0;
                yy = 2;
                break;
            }
            else if(button_str[0][2] == smb[i] && button_str[1][1] == smb[i] && button_str[2][0] == '0')
            {
                computed = true;
                xx = 2;
                yy = 0;
                break;
            }
        }

        if(!computed)
            for(x = 0; x < 3; x++)
            {
                count_my = 0;
                count_comp = 0;
                count_zero = 0;
                for(y = 0; y < 3; y++)
                {
                    if(button_str[x][y] == char_my)
                        count_my++;
                    else if(button_str[x][y] == char_comp)
                        count_comp++;
                    else
                        count_zero++;
                }
                if(count_my == 0 && count_comp == 2 && count_zero == 1)
                {
                    computed = true;
                    horizon = true;
                    break;
                }
            }

        if(!computed)
            for(x = 0; x < 3; x++)
            {
                count_my = 0;
                count_comp = 0;
                count_zero = 0;
                for(y = 0; y < 3; y++)
                {
                    if(button_str[x][y] == char_my)
                        count_my++;
                    else if(button_str[x][y] == char_comp)
                        count_comp++;
                    else
                        count_zero++;
                }
                if(count_my == 2 && count_comp == 0 && count_zero == 1)
                {
                    computed = true;
                    horizon = true;
                    break;
                }
            }

        if(!computed)
            for(y = 0; y < 3; y++)
            {
                count_my = 0;
                count_comp = 0;
                count_zero = 0;
                for(x = 0; x < 3; x++)
                {
                    if(button_str[x][y] == char_my)
                        count_my++;
                    else if(button_str[x][y] == char_comp)
                        count_comp++;
                    else
                        count_zero++;
                }
                if(count_my == 0 && count_comp == 2 && count_zero == 1)
                {
                    computed = true;
                    horizon = false;
                    break;
                }

            }

        if(!computed)
            for(y = 0; y < 3; y++)
            {
                count_my = 0;
                count_comp = 0;
                count_zero = 0;
                for(x = 0; x < 3; x++)
                {
                    if(button_str[x][y] == char_my)
                        count_my++;
                    else if(button_str[x][y] == char_comp)
                        count_comp++;
                    else
                        count_zero++;
                }
                if(count_my == 2 && count_comp == 0 && count_zero == 1)
                {
                    //noinspection UnusedAssignment
                    computed = true;
                    horizon = false;
                    break;
                }
            }

        if(count_my == 0 && count_comp == 2 && count_zero == 1)
        {
            if(horizon)
            {
                yy = gimmeFreeRoomHorizon(x);
                xx = x;
            }
            else
            {
                xx = gimmeFreeRoomVertical(y);
                yy = y;
            }
        }
        else if(count_my == 2 && count_comp == 0 && count_zero == 1)
        {
            if(horizon)
            {
                yy = gimmeFreeRoomHorizon(x);
                xx = x;
            }
            else
            {
                xx = gimmeFreeRoomVertical(y);
                yy = y;
            }
        }
        else //noinspection ConstantConditions
            if(xx == -1 || yy == -1)
            do
            {
                xx = rand.nextInt(3);
                yy = rand.nextInt(3);
            } while(button_str[xx][yy] != '0');

        button[xx][yy].setImageBitmap(bitmap_comp);
        button_str[xx][yy] = char_comp;
        isMyTurn = true;
        button[xx][yy].setClickable(false);
        checkConditions();
    }

    private boolean yallGotAnymoreOfThemButtons()
    {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            if(button_str[x][y] == '0')
                return true;
        return false;
    }

    public void myMove(View view)
    {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            if(button[x][y] == findViewById(view.getId()))
            {
                button[x][y].setImageBitmap(bitmap_my);
                button_str[x][y] = char_my;
                isMyTurn = false;
                button[x][y].setClickable(false);
                checkConditions();
                if(yallGotAnymoreOfThemButtons())
                    if(!isThereAWinner)
                        compMove();
                break;
            }
    }

    public void onClickReturn(View view)
    {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}