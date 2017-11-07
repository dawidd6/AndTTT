package com.dawidd6.andttt;

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
import android.widget.Toast;

import java.util.Random;

public class ActivitySingle extends Activity
{
    private boolean isMyTurn;
    private boolean isThereAWinner;
    private boolean isNightModeEnabled;
    private ImageButton button[][];
    private char button_str[][];
    private char smb[];
    private char char_my;
    private char char_comp;
    private Bitmap bitmap_x;
    private Bitmap bitmap_o;
    private Bitmap bitmap_board;
    private Bitmap bitmap_my;
    private Bitmap bitmap_comp;
    private ImageView board;
    private ImageView current;
    private Paint paint;
    private Canvas canvas;
    private Random rand;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        isNightModeEnabled = getIntent().getBooleanExtra("night_mode", false);
        setTheme(isNightModeEnabled ? R.style.theme_dark : R.style.theme_light);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        board = (ImageView)findViewById(R.id.board);
        txt = (TextView)findViewById(R.id.txt);
        current = (ImageView)findViewById(R.id.current);

        rand = new Random();
        smb = new char[] {'x', 'o'};
        button = new ImageButton[3][3];
        button_str = new char[3][3];
        int btn_ids[][] = {{R.id.b0, R.id.b1, R.id.b2}, {R.id.b3, R.id.b4, R.id.b5}, {R.id.b6, R.id.b7, R.id.b8}};
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            button[x][y] = (ImageButton)findViewById(btn_ids[x][y]);

        setPaint(isNightModeEnabled);
        drawCross();
        drawCircle();
        restartGame(null);
    }

    public void drawLine(String l)
    {
        bitmap_board = Bitmap.createBitmap(304, 304, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap_board);
        //drawLine(startx, starty, stopx, stopy, paint)

        if(l == "nl")
            canvas.drawLine(4, 4, 300, 300, paint);
        else if(l == "nr")
            canvas.drawLine(300, 4, 4, 300, paint);
        else if(l == "h1")
            canvas.drawLine(0, 50, 304, 50, paint);
        else if(l == "h2")
            canvas.drawLine(0, 152, 304, 152, paint);
        else if(l == "h3")
            canvas.drawLine(0, 254, 304, 254, paint);
        else if(l == "v1")
            canvas.drawLine(50, 0, 50, 304, paint);
        else if(l == "v2")
            canvas.drawLine(152, 0, 152, 304, paint);
        else if(l == "v3")
            canvas.drawLine(254, 0, 254, 304, paint);

        board.setImageBitmap(bitmap_board);
    }

    public void setPaint(boolean mode)
    {
        paint = new Paint();
        paint.setColor(mode ? Color.WHITE : Color.BLACK);
        paint.setStrokeWidth(6);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void drawCross()
    {

        bitmap_x = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap_x);
        canvas.drawLine(6, 6, 94, 94, paint);
        canvas.drawLine(94, 6, 6, 94, paint);
    }

    public void drawCircle()
    {
        bitmap_o = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap_o);
        canvas.drawCircle(50, 50, 45, paint);
    }

    public void randomTurn()
    {
        int k = rand.nextInt(2);
        isMyTurn = (k != 0);
    }

    public void randomSymbol()
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

    public void restartGame(View view)
    {
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
        current.setImageBitmap(bitmap_my);
        if(!isMyTurn)
            compMove();
    }

    public void markDisabledAll()
    {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            button[x][y].setClickable(false);
    }

    public void markEnabledAll()
    {
        for(int x = 0; x < 3; x++) for(int y = 0; y < 3; y++)
            button[x][y].setClickable(true);
    }

    public void doWin(String l)
    {
        isThereAWinner = true;
        markDisabledAll();
        Toast.makeText(this, "WIN", Toast.LENGTH_SHORT).show();
        drawLine(l);
    }

    public void checkConditions()
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

    public int gimmeFreeRoomHorizon(int x)
    {
        for(int y = 0; y < 3; y++)
            if(button_str[x][y] == '0')
                return y;
        return -1;
    }

    public int gimmeFreeRoomVertical(int y)
    {
        for(int x = 0; x < 3; x++)
            if(button_str[x][y] == '0')
            {
                return x;
            }
        return -1;
    }

    public void compMove()
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
        else if(xx == -1 || yy == -1)
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

    public boolean yallGotAnymoreOfThemButtons()
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
        Intent intent = new Intent(this, ActivityMenu.class);
        startActivity(intent);
    }
}