package com.github.dawidd6.andttt.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
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

        client.setOnGetRoomsListener(this::getRooms);

        refresh();
    }

    private void refresh() {
        layout.setRefreshing(true);

        Message message = Message.newBuilder()
                .setType(TYPE.GET_ROOMS)
                .build();

        client.send(message);
    }

    private void getRooms(Room rooms[]) {
        if(rooms.length == 0) {
            getActivity().runOnUiThread(() -> text.setVisibility(View.VISIBLE));
        } else {
            ArrayList<String> array = new ArrayList<>();
            for(Room room : rooms)
                array.add(room.getName() + "(" + room.getClientsCount() + ")");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, array);
            getActivity().runOnUiThread(() -> list.setAdapter(adapter));

        }

        getActivity().runOnUiThread(() -> layout.setRefreshing(false));
    }

    private void joinRoom(AdapterView<?> parent, View v, int position, long id) {

    }
}
