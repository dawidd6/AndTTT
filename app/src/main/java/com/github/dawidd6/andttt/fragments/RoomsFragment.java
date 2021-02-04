package com.github.dawidd6.andttt.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.adapters.RoomAdapter;
import com.github.dawidd6.andttt.events.SendEvent;
import com.github.dawidd6.andttt.proto.GetRoomsRequest;
import com.github.dawidd6.andttt.proto.GetRoomsResponse;
import com.github.dawidd6.andttt.proto.JoinRoomRequest;
import com.github.dawidd6.andttt.proto.JoinRoomResponse;
import com.github.dawidd6.andttt.proto.Request;
import com.github.dawidd6.andttt.proto.Room;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

import static com.github.dawidd6.andttt.activities.OnlineActivity.bus;
import static com.github.dawidd6.andttt.activities.OnlineActivity.dialogManager;

public class RoomsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "RoomsFragment";
    @BindView(R.id.noRoomsText) TextView noRoomsText;
    @BindView(R.id.roomList) ListView roomList;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rooms, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout.setOnRefreshListener(this);
        noRoomsText.setVisibility(View.GONE);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bus.register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bus.unregister(this);
    }

    @OnClick(R.id.createButton)
    public void onCreateButtonClick() {
        Navigation.findNavController(requireActivity(), R.id.navigation_host_online).navigate(R.id.action_roomsFragment_to_createFragment);
    }

    @OnItemClick(R.id.roomList)
    public void onRoomListClick(AdapterView<?> parent, View v, int position, long id) {
        Room room = (Room)parent.getItemAtPosition(position);

        if(room.getIsProtected()) {
            int inputType = EditorInfo.TYPE_CLASS_TEXT|EditorInfo.TYPE_TEXT_VARIATION_PASSWORD;

            dialogManager.showInput(getActivity(), R.string.enter_password, inputType, ((dialog, which) -> {
                dialog.dismiss();
                dialogManager.showLoading(getActivity(), R.string.joining);
                Request request = Request.newBuilder()
                        .setJoinRoom(JoinRoomRequest.newBuilder()
                                .setName(room.getName())
                                .setPassword(dialog.getInputEditText().getText().toString()))
                        .build();
                bus.post(new SendEvent(request));
            }));
        } else {
            dialogManager.showLoading(getActivity(), R.string.joining);
            Request request = Request.newBuilder()
                    .setJoinRoom(JoinRoomRequest.newBuilder()
                            .setName(room.getName()))
                    .build();
            bus.post(new SendEvent(request));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        onRefresh();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onRefresh() {
        layout.setRefreshing(true);

        Request request = Request.newBuilder()
                .setGetRooms(GetRoomsRequest.newBuilder())
                .build();
        bus.post(new SendEvent(request));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJoinRoom(JoinRoomResponse response) {
        dialogManager.dismiss();
        Navigation.findNavController(requireActivity(), R.id.navigation_host_online).navigate(R.id.action_roomsFragment_to_onlineFragment);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetRooms(GetRoomsResponse response) {
        if(response.getRoomsCount() == 0) {
            noRoomsText.setVisibility(View.VISIBLE);
            roomList.setVisibility(View.GONE);
        } else {
            noRoomsText.setVisibility(View.GONE);
            roomList.setVisibility(View.VISIBLE);
            ArrayList<Room> array = new ArrayList<>(response.getRoomsList());
            RoomAdapter adapter = new RoomAdapter(requireContext(), array);
            roomList.setAdapter(adapter);
        }

        layout.setRefreshing(false);
    }
}
