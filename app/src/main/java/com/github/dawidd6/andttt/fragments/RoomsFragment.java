package com.github.dawidd6.andttt.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import com.github.dawidd6.andttt.OnlineActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.events.SendEvent;
import com.github.dawidd6.andttt.adapters.RoomAdapter;
import com.github.dawidd6.andttt.proto.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import static com.github.dawidd6.andttt.OnlineActivity.bus;
import static com.github.dawidd6.andttt.OnlineActivity.dialogManager;


public class RoomsFragment extends BaseFragment {
    public static final String TAG = "RoomsFragment";
    @BindView(R.id.noRoomsText) TextView noRoomsText;
    @BindView(R.id.roomList) ListView roomList;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout layout;
    private long lastRefreshed;
    private Thread periodicRefreshThread = new Thread() {
        @Override
        public void run() {
            setName("periodic-refresh-thread");

            long refreshInterval = 10 * 1000;

            while(!isInterrupted()) {
                try {
                    Thread.sleep(refreshInterval);
                    if(System.currentTimeMillis()-lastRefreshed > refreshInterval) {
                        getActivity().runOnUiThread(RoomsFragment.this::refresh);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rooms, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout.setOnRefreshListener(this::refresh);
        noRoomsText.setVisibility(View.GONE);
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
        OnlineActivity.switchFragment(getFragmentManager(), new CreateFragment(), true);
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

        refresh();

        periodicRefreshThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        periodicRefreshThread.interrupt();
    }

    private void refresh() {
        layout.setRefreshing(true);

        lastRefreshed = System.currentTimeMillis();

        Request request = Request.newBuilder()
                .setGetRooms(GetRoomsRequest.newBuilder())
                .build();
        bus.post(new SendEvent(request));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJoinRoom(JoinRoomResponse response) {
        dialogManager.dismiss();
        OnlineActivity.switchFragment(getFragmentManager(), new OnlineFragment(), true);
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
            RoomAdapter adapter = new RoomAdapter(getActivity(), array);
            roomList.setAdapter(adapter);
        }

        layout.setRefreshing(false);
    }
}
