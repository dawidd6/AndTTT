package com.github.dawidd6.andttt.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.dialogs.InputDialogFragment;
import com.github.dawidd6.andttt.events.SendEvent;
import com.github.dawidd6.andttt.adapters.RoomAdapter;
import com.github.dawidd6.andttt.proto.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


public class RoomsFragment extends BaseOnlineFragment {
    public static final String TAG = "RoomsFragment";
    @BindView(R.id.noRoomsText) TextView noRoomsText;
    @BindView(R.id.roomList) ListView roomList;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout layout;
    private long lastRefreshed;
    private String name;
    private Thread periodicRunnable = new Thread() {
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

        name = getArguments().getString("name");
        layout.setOnRefreshListener(this::refresh);
        noRoomsText.setVisibility(View.GONE);
    }

    @OnClick(R.id.createButton)
    public void onCreateButtonClick() {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out,
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(R.id.placeholder, new CreateFragment(), CreateFragment.TAG)
                .commit();
    }

    @OnItemClick(R.id.roomList)
    public void onRoomListClick(AdapterView<?> parent, View v, int position, long id) {
        Room room = (Room)parent.getItemAtPosition(position);

        if(room.getIsProtected()) {
            InputDialogFragment inputDialogFragment = new InputDialogFragment();
            inputDialogFragment.set(
                    R.string.enter_password,
                    EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD,
                    (vv, input) -> {
                        inputDialogFragment.dismiss();
                        Request request = Request.newBuilder()
                                .setJoinRoom(JoinRoomRequest.newBuilder()
                                        .setName(room.getName())
                                        .setPassword(input))
                                .build();
                        EventBus.getDefault().post(new SendEvent(request));
                    });
            inputDialogFragment.show(getFragmentManager(), null);
        } else {
            Request request = Request.newBuilder()
                    .setJoinRoom(JoinRoomRequest.newBuilder()
                            .setName(room.getName()))
                    .build();
            EventBus.getDefault().post(new SendEvent(request));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();

        periodicRunnable.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        periodicRunnable.interrupt();
    }

    private void refresh() {
        layout.setRefreshing(true);

        lastRefreshed = System.currentTimeMillis();

        Request request = Request.newBuilder()
                .setGetRooms(GetRoomsRequest.newBuilder())
                .build();
        EventBus.getDefault().post(new SendEvent(request));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onJoinRoom(JoinRoomResponse response) {
        OnlineFragment onlineFragment = new OnlineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        onlineFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out,
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(R.id.placeholder, onlineFragment, OnlineFragment.TAG)
                .commit();
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
