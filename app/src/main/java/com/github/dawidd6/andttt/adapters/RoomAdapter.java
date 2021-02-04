package com.github.dawidd6.andttt.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.activities.MainActivity;
import com.github.dawidd6.andttt.proto.Room;

import java.util.ArrayList;
import java.util.List;

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
        sinceText.setText(getCreatedAgo(listItem.getResources(), currentRoom.getSince().getSeconds()));

        return listItem;
    }

    public static String getCreatedAgo(Resources resources, long sinceSeconds) {
        long deltaSecs = System.currentTimeMillis()/1000 - sinceSeconds;

        // days
        if(deltaSecs/60/60/24 > 1)
            return resources.getString(R.string.created_days_ago, deltaSecs/60/60/24);
        // day
        else if(deltaSecs/60/60/24 == 1)
            return resources.getString(R.string.created_day_ago);
        // hours
        else if(deltaSecs/60/60 > 1)
            return resources.getString(R.string.created_hours_ago, deltaSecs/60/60);
        // hour
        else if(deltaSecs/60/60 == 1)
            return resources.getString(R.string.created_hour_ago);
        // minutes
        else if(deltaSecs/60 > 1)
            return resources.getString(R.string.created_minutes_ago, deltaSecs/60);
        // minute
        else if(deltaSecs/60 == 1)
            return resources.getString(R.string.created_minute_ago);
        // seconds
        else if(deltaSecs > 1)
            return resources.getString(R.string.created_seconds_ago, deltaSecs);
        // second
        else
            return resources.getString(R.string.created_second_ago);
    }
}
