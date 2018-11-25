package com.github.dawidd6.andttt.fragments;


import android.app.Fragment;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.dialogs.ErrorDialogFragment;
import com.github.dawidd6.andttt.gui.RoomAdapter;
import com.github.dawidd6.andttt.proto.*;
import com.github.dawidd6.andttt.proto.Error;

import java.util.ArrayList;

import static com.github.dawidd6.andttt.OnlineActivity.client;

public class RoomsFragment extends BaseFragment {
    private SwipeRefreshLayout layout;
    private TextView noRoomsText;
    private ListView roomList;
    private ErrorDialogFragment errorDialogFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rooms, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        errorDialogFragment = new ErrorDialogFragment();
        errorDialogFragment.setOnOkClickListener((v) -> {
            errorDialogFragment.dismiss();
        });

        client.setOnResponseListener(this::dispatch);

        layout = view.findViewById(R.id.swiperefresh);
        layout.setOnRefreshListener(this::onRefresh);

        noRoomsText = view.findViewById(R.id.noRoomsText);
        noRoomsText.setVisibility(View.GONE);

        roomList = view.findViewById(R.id.roomList);
        roomList.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {
            Request request = Request.newBuilder()
                    .setJoinRoom(JoinRoomRequest.newBuilder()
                            .setName(((Room)parent.getItemAtPosition(position)).getName()))
                    .build();
            client.send(request);
        });

        Button createButton = view.findViewById(R.id.createButton);
        createButton.setOnClickListener((v) -> {
            MainActivity.switchFragments(getFragmentManager(), new CreateFragment(), true);
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        onRefresh();
    }

    private void onRefresh() {
        layout.setRefreshing(true);

        Request request = Request.newBuilder()
                .setGetRooms(GetRoomsRequest.newBuilder())
                .build();

        client.send(request);
    }

    private void dispatch(Response response) {
        if(response.getError() != Error.NONE) {
            errorDialogFragment.setText(null);
            errorDialogFragment.setErrorCode(response.getError());
            errorDialogFragment.show(getFragmentManager(), null);
            return;
        }

        switch (response.getTypeCase()) {
            case GET_ROOMS:
                getActivity().runOnUiThread(() -> {
                    if(response.getGetRooms().getRoomsCount() == 0) {
                        noRoomsText.setVisibility(View.VISIBLE);
                        roomList.setVisibility(View.GONE);
                    } else {
                        noRoomsText.setVisibility(View.GONE);
                        roomList.setVisibility(View.VISIBLE);
                        ArrayList<Room> array = new ArrayList<>(response.getGetRooms().getRoomsList());
                        RoomAdapter adapter = new RoomAdapter(getActivity(), array);
                        roomList.setAdapter(adapter);
                    }

                    layout.setRefreshing(false);
                });
                break;
            case JOIN_ROOM:
                MainActivity.switchFragments(getFragmentManager(), new OnlineFragment(), true);
        }
    }
}
