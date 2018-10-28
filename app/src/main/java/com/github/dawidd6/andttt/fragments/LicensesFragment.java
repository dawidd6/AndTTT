package com.github.dawidd6.andttt.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.dawidd6.andttt.R;

import java.util.Vector;

public class LicensesFragment extends Fragment {
    private LinearLayout layout;
    private Vector<View> libraries = new Vector<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_licenses, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = view.findViewById(R.id.licenses_layout);
    }

    public void onClickLibrary(View v) {
        TextView url = v.findViewById(R.id.library_url);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.getText().toString()));
        startActivity(browserIntent);
    }

    public void addLibrary(String name, String author, String license, String url) {
        libraries.add(View.inflate(getActivity(), R.layout.library,null));
        libraries.lastElement().setOnClickListener(this::onClickLibrary);

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
