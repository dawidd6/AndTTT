package com.github.dawidd6.andttt.fragments;


import android.app.Fragment;
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
                            .setName(parent.getItemAtPosition(position).toString()))
                    .build();
            client.send(request);
        });

        Button createButton = view.findViewById(R.id.createButton);
        createButton.setOnClickListener((v) -> {
            MainActivity.switchFragments(getFragmentManager(), new CreateFragment(), true);
        });

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
                if(response.getGetRooms().getRoomsCount() == 0) {
                    getActivity().runOnUiThread(() -> noRoomsText.setVisibility(View.VISIBLE));
                } else {
                    getActivity().runOnUiThread(() -> noRoomsText.setVisibility(View.GONE));
                    ArrayList<String> array = new ArrayList<>();
                    for(Room room : response.getGetRooms().getRoomsList())
                        array.add(room.getName());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, array);
                    getActivity().runOnUiThread(() -> roomList.setAdapter(adapter));
                }

                getActivity().runOnUiThread(() -> layout.setRefreshing(false));
                break;
            case JOIN_ROOM:
                MainActivity.switchFragments(getFragmentManager(), new OnlineFragment(), true);
        }
    }
}
