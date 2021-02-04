package com.github.dawidd6.andttt.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.events.ConnectEvent;
import com.github.dawidd6.andttt.events.ConnectFailEvent;
import com.github.dawidd6.andttt.events.ConnectSuccessEvent;
import com.github.dawidd6.andttt.events.ConnectTimeoutEvent;
import com.github.dawidd6.andttt.events.SendEvent;
import com.github.dawidd6.andttt.proto.RegisterNameRequest;
import com.github.dawidd6.andttt.proto.RegisterNameResponse;
import com.github.dawidd6.andttt.proto.Request;
import com.github.dawidd6.andttt.services.ClientService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.github.dawidd6.andttt.activities.OnlineActivity.bus;
import static com.github.dawidd6.andttt.activities.OnlineActivity.dialogManager;
import static com.github.dawidd6.andttt.activities.OnlineActivity.isConnected;
import static com.github.dawidd6.andttt.activities.OnlineActivity.name;


public class ConnectFragment extends BaseFragment {
    public static final String TAG = "ConnectFragment";
    @BindView(R.id.nameEdit) EditText nameEdit;
    @BindView(R.id.serverCheck) CheckBox serverCheck;
    @BindView(R.id.addressText) TextView addressText;
    @BindView(R.id.addressEdit) EditText addressEdit;
    private boolean isCustomServer;
    private boolean isRegistered;
    private static final String DEFAULT_SERVER_ADDRESS = "andttt-server.awsmppl.com:33333";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connect, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addressEdit.setVisibility(View.GONE);
        addressText.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        requireActivity().startService(new Intent(requireContext(), ClientService.class));
        bus.register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        requireActivity().stopService(new Intent(requireContext(), ClientService.class));
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
        Navigation.findNavController(requireActivity(), R.id.navigation_host_online).navigate(R.id.action_connectFragment_to_roomsFragment);
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

        String address = isCustomServer ? addressEdit.getText().toString() : DEFAULT_SERVER_ADDRESS;
        String[] split = address.split(":");
        String host = split[0];
        try {
            int port = Integer.parseInt(split[1]);
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
