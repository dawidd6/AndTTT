package com.github.dawidd6.andttt.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.misc.Library;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends ArrayAdapter<Library> {
    private Context context;
    private List<Library> list;

    public LibraryAdapter(@NonNull Context context, ArrayList<Library> list) {
        super(context, 0 , list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.library, parent, false);

        Library current = list.get(position);
        TextView nameText = listItem.findViewById(R.id.nameText);
        TextView authorText = listItem.findViewById(R.id.authorText);
        TextView licenseText = listItem.findViewById(R.id.licenseText);
        TextView urlText = listItem.findViewById(R.id.urlText);

        nameText.setText(current.getName());
        authorText.setText(current.getAuthor());
        licenseText.setText(current.getLicense());
        urlText.setText(current.getUrl());

        listItem.setOnClickListener((v) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getUrl()));
            context.startActivity(browserIntent);
        });

        return listItem;
    }
}
