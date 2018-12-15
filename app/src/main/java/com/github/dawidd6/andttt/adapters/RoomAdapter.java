package com.github.dawidd6.andttt.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.proto.Room;
import com.google.protobuf.Timestamp;
import com.google.protobuf.TimestampOrBuilder;
import com.google.protobuf.TimestampProto;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RoomAdapter extends ArrayAdapter<Room> {
    private Context context;
    private List<Room> roomsList;

    public RoomAdapter(@NonNull Context context, ArrayList<Room> list) {
        super(context, 0 , list);
        this.context = context;
        this.roomsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.room, parent, false);

        Room currentRoom = roomsList.get(position);
        Date since = new Date(currentRoom.getSince().getSeconds()*1000);
        TextView nameText = listItem.findViewById(R.id.nameText);
        TextView countText = listItem.findViewById(R.id.countText);
        TextView sinceText = listItem.findViewById(R.id.sinceText);
        ImageView lockView = listItem.findViewById(R.id.lockView);

        if(currentRoom.getIsProtected()) {
            lockView.setBackgroundResource(R.drawable.ic_lock);
            if (MainActivity.isNightModeEnabled)
                lockView.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        }

        int counter = 0;
        if(!currentRoom.getFirstClient().getName().equals(""))
            counter++;
        if(!currentRoom.getSecondClient().getName().equals(""))
            counter++;
        String count = String.valueOf(counter) + "/2";

        nameText.setText(currentRoom.getName());
        countText.setText(count);
        sinceText.setText(since.toString());

        return listItem;
    }
}
