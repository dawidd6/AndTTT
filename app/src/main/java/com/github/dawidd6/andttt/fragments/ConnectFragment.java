package com.github.dawidd6.andttt.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.github.dawidd6.andttt.ClientService;
import com.github.dawidd6.andttt.OnlineActivity;
import com.github.dawidd6.andttt.OnlineActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.events.*;
import com.github.dawidd6.andttt.proto.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.github.dawidd6.andttt.OnlineActivity.*;


public class ConnectFragment extends BaseFragment {
    public static final String TAG = "ConnectFragment";
    @BindView(R.id.nameEdit) EditText nameEdit;
    @BindView(R.id.serverCheck) CheckBox serverCheck;
    @BindView(R.id.addressText) TextView addressText;
    @BindView(R.id.addressEdit) EditText addressEdit;
    private boolean isCustomServer;
    private boolean isRegistered;
    private String server = "srv02.mikr.us:20564";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connect, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().startService(new Intent(getActivity(), ClientService.class));

        addressEdit.setVisibility(View.GONE);
        addressText.setVisibility(View.GONE);
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

    @OnClick(R.id.okButton)
    public void onOkButtonClick() {
        if(!isConnected)
            connect();
        else if(!isRegistered)
            register();
    }

    @OnCheckedChanged(R.id.serverCheck)
    public void onCustomServerChecked(CompoundButton button, boolean checked) {
        isCustomServer = checked;

        addressText.setVisibility(checked ? View.VISIBLE : View.GONE);
        addressEdit.setVisibility(checked ? View.VISIBLE : View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterName(RegisterNameResponse response) {
        isRegistered = true;
        name = response.getName();
        dialogManager.dismiss();
        OnlineActivity.switchFragment(getFragmentManager(), new RoomsFragment(), false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectSuccess(ConnectSuccessEvent event) {
        isConnected = true;
        if(!isRegistered)
            register();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectFail(ConnectFailEvent event) {
        isConnected = false;

        dialogManager.showError(getActivity(), R.string.connection_failed, ((dialog, which) -> {
            dialog.dismiss();
        }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectTimeout(ConnectTimeoutEvent event) {
        isConnected = false;

        dialogManager.showError(getActivity(), R.string.connection_timeout, ((dialog, which) -> {
            dialog.dismiss();
        }));
    }

    private void connect() {
        dialogManager.showLoading(getActivity(), R.string.connecting);

        String address = isCustomServer ? addressEdit.getText().toString() : server;
        String split[] = address.split(":");
        String host = split[0];
        try {
            int port = Integer.valueOf(split[1]);
            bus.post(new ConnectEvent(host, port));
        } catch(ArrayIndexOutOfBoundsException | NumberFormatException e) {
            dialogManager.showError(getActivity(), R.string.wrong_format, ((dialog, which) -> {
                dialog.dismiss();
            }));
        }
    }

    private void register() {
        dialogManager.showLoading(getActivity(), R.string.registering);

        Request request = Request.newBuilder()
                .setRegisterName(RegisterNameRequest.newBuilder()
                        .setName(nameEdit.getText().toString()))
                .build();
        bus.post(new SendEvent(request));
    }
}
