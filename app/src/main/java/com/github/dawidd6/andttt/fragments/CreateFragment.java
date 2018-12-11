package com.github.dawidd6.andttt.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.github.dawidd6.andttt.ClientService;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.dialogs.ErrorDialogFragment;
import com.github.dawidd6.andttt.events.SendEvent;
import com.github.dawidd6.andttt.proto.*;
import com.github.dawidd6.andttt.proto.Error;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class CreateFragment extends BaseOnlineFragment {
    public static final String TAG = "CreateFragment";
    @BindView(R.id.roomEdit) EditText roomEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create, parent, false);
    }

    @OnClick(R.id.okButton)
    public void onOkButtonClick() {
        Request request = Request.newBuilder()
                .setCreateRoom(CreateRoomRequest.newBuilder()
                        .setName(roomEdit.getText().toString()))
                .build();

        EventBus.getDefault().post(new SendEvent(request));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateRoom(CreateRoomResponse response) {
        getActivity().onBackPressed();
    }
}
