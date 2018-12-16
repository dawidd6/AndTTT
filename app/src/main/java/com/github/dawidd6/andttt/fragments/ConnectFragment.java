package com.github.dawidd6.andttt.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.github.dawidd6.andttt.ClientService;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.events.*;
import com.github.dawidd6.andttt.proto.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ConnectFragment extends BaseFragment {
    public static final String TAG = "ConnectFragment";
    @BindView(R.id.addressEdit) EditText addressEdit;
    @BindView(R.id.nameEdit) EditText nameEdit;
    private boolean isConnected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isOnline = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connect, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().startService(new Intent(getActivity(), ClientService.class));
    }

    @OnClick(R.id.okButton)
    public void onOkButtonClick() {
        connect();
        register();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterName(RegisterNameResponse response) {
        RoomsFragment roomsFragment = new RoomsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", response.getName());
        roomsFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out,
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(R.id.placeholder, roomsFragment, RoomsFragment.TAG)
                .commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectSuccess(ConnectSuccessEvent event) {
        EventBus.getDefault().post(new NotifyEvent(getString(R.string.connected), event.getAddress(), true));
        isConnected = true;
        register();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectFail(ConnectFailEvent event) {
        showError(R.string.connection_failed, ((dialog, which) -> {
            dialog.dismiss();
        }));

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
