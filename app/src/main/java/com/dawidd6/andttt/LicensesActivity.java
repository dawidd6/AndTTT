package com.dawidd6.andttt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

public class LicensesActivity extends BaseActivity
{
    private LinearLayout layout;
    private Vector<View> libraries = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licenses);

        layout = findViewById(R.id.licenses_layout);

        addLibrary("Android View Animations", "daimajia", "MIT", "https://github.com/daimajia/AndroidViewAnimations");
        addLibrary("Android Easing Functions", "daimajia", "MIT", "https://github.com/daimajia/AnimationEasingFunctions");
    }

    public void onClickLibrary(View v) {
        TextView url = v.findViewById(R.id.library_url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.getText().toString()));
        startActivity(browserIntent);
    }

    public void addLibrary(String name, String author, String license, String url) {
        libraries.add(View.inflate(this, R.layout.library,null));
        layout.addView(libraries.lastElement());

        TextView library_name = libraries.lastElement().findViewById(R.id.library_name);
        TextView library_author = libraries.lastElement().findViewById(R.id.library_author);
        TextView library_license = libraries.lastElement().findViewById(R.id.library_license);
        TextView library_url = libraries.lastElement().findViewById(R.id.library_url);

        library_name.setText(name);
        library_author.setText(author);
        library_license.setText(license);
        library_url.setText(url);
    }
}
