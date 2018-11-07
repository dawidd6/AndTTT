package com.github.dawidd6.andttt.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.proto.Message;
import com.github.dawidd6.andttt.proto.Room;
import com.github.dawidd6.andttt.proto.TYPE;

import java.util.ArrayList;

import static com.github.dawidd6.andttt.MainActivity.client;

public class RoomServiceFragment extends Fragment {
    SwipeRefreshLayout layout;
    ListView list;
    TextView text;
    Button createButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_roomservice, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = view.findViewById(R.id.swiperefresh);
        layout.setOnRefreshListener(this::refresh);

        text = view.findViewById(R.id.no_rooms_text);
        text.setVisibility(View.GONE);

        list = view.findViewById(R.id.rooms);
        list.setOnItemClickListener(this::joinRoom);

        createButton = view.findViewById(R.id.createButton);
        createButton.setOnClickListener(this::createRoom);

        client.setOnMessageReceivedListener(this::onMessage);

        refresh();
    }

    private void refresh() {
        layout.setRefreshing(true);

        Message message = Message.newBuilder()
                .setType(TYPE.GET_ROOMS)
                .build();

        client.send(message);
    }

    private void onMessage(Message message) {
        if(message.getType() != TYPE.GET_ROOMS)
            return;

        if(message.getRoomsCount() == 0) {
            getActivity().runOnUiThread(() -> text.setVisibility(View.VISIBLE));
        } else {
            getActivity().runOnUiThread(() -> text.setVisibility(View.GONE));
            ArrayList<String> array = new ArrayList<>();
            for(Room room : message.getRoomsList())
                array.add(room.getName());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, array);
            getActivity().runOnUiThread(() -> list.setAdapter(adapter));
        }

        getActivity().runOnUiThread(() -> layout.setRefreshing(false));
    }

    private void joinRoom(AdapterView<?> parent, View v, int position, long id) {
        Message message = Message.newBuilder()
                .setType(TYPE.JOIN_ROOM)
                .setName(parent.getItemAtPosition(position).toString())
                .build();
    }

    private void createRoom(View v) {
        // TODO input dialog
    }
}