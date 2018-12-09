package com.github.dawidd6.andttt.fragments;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.ClientService;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.dialogs.BaseDialogFragment;
import com.github.dawidd6.andttt.dialogs.ErrorDialogFragment;
import com.github.dawidd6.andttt.events.*;
import com.github.dawidd6.andttt.proto.*;
import com.github.dawidd6.andttt.proto.Error;
import com.google.protobuf.InvalidProtocolBufferException;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ConnectFragment extends BaseOnlineFragment {
    private Button okButton;
    private EditText addressEdit;
    private EditText nameEdit;
    private final String TAG = "ConnectFragment";
    private boolean isConnected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connect, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addressEdit = view.findViewById(R.id.addressEdit);
        nameEdit = view.findViewById(R.id.nameEdit);

        getActivity().startService(new Intent(getActivity(), ClientService.class));

        okButton = view.findViewById(R.id.okButton);
        okButton.setOnClickListener((v) -> {
            connect();
            register();
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterName(RegisterNameResponse response) {
        RoomsFragment roomsFragment = new RoomsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", response.getName());
        roomsFragment.setArguments(bundle);
        MainActivity.switchFragments(getFragmentManager(), roomsFragment, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectSuccess(ConnectSuccessEvent event) {
        EventBus.getDefault().post(new NotifyEvent(getString(R.string.connected), event.getAddress(), true));
        isConnected = true;
        register();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectFail(ConnectFailEvent event) {
        errorDialogFragment.setText(R.string.connection_failed);

        if(isResumed()) {
            errorDialogFragment.show(getFragmentManager(), null);
        } else {
            savedDialogFragment = errorDialogFragment;
        }

        isConnected = false;
    }

    private void connect() {
        if(!isConnected) {
            String address = addressEdit.getText().toString();
            if(address.isEmpty())
                address = addressEdit.getHint().toString();
            String split[] = address.split(":");
            String host = split[0];
            int port = Integer.valueOf(split[1]);
            EventBus.getDefault().post(new ConnectEvent(host, port));
        }
    }

    private void register() {
        if(isConnected) {
            String name = nameEdit.getText().toString();
            if (name.isEmpty())
                name = nameEdit.getHint().toString();
            Request request = Request.newBuilder()
                    .setRegisterName(RegisterNameRequest.newBuilder()
                            .setName(name))
                    .build();
            EventBus.getDefault().post(new SendEvent(request));
        }
    }
}
