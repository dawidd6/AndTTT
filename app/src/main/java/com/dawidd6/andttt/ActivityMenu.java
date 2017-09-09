package com.dawidd6.andttt;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class ActivityMenu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
    }

    public void onClickPlaySingle(View view)
    {
        Intent intent = new Intent(this, ActivitySingle.class);
        startActivity(intent);
    }

    public void onClickExit(View view)
    {
        finishAffinity();
        System.exit(0);
    }
}
