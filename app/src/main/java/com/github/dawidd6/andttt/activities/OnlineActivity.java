package com.github.dawidd6.andttt.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.events.DisconnectEvent;
import com.github.dawidd6.andttt.misc.DialogManager;
import com.github.dawidd6.andttt.proto.Error;
import com.github.dawidd6.andttt.services.ClientService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class OnlineActivity extends BaseActivity {
    public static EventBus bus;
    public static DialogManager dialogManager;
    public static boolean isConnected;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null)
            return;

        BroadcastReceiver leaveBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               finish();
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("andttt-online-leave");
        registerReceiver(leaveBroadcastReceiver, intentFilter);

        startService(new Intent(this, ClientService.class));
        bus = EventBus.builder()
                .logSubscriberExceptions(true)
                .logNoSubscriberMessages(true)
                .sendNoSubscriberEvent(false)
                .build();
        bus.register(this);

        dialogManager = new DialogManager();

        ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        assert connManager != null;
        connManager.registerNetworkCallback(builder.build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onLost(Network network) {
                super.onLost(network);

                bus.post(new DisconnectEvent());
            }
        });
        setContentView(R.layout.activity_online);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(this, ClientService.class));
        bus.unregister(this);

        isConnected = false;
        name = "";
        dialogManager = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDisconnect(DisconnectEvent event) {
        if(isFinishing())
            return;

        if(!isConnected)
            return;

        isConnected = false;

        dialogManager.showError(this, R.string.disconnected, ((dialog, which) -> {
            dialog.dismiss();
            finish();
        }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onError(Error error) {
        dialogManager.showError(this, OnlineActivity.getErrorStringRes(error), ((dialog, which) -> dialog.dismiss()));
    }

    public static int getErrorStringRes(Error error) {
        switch (error) {
            case CLIENT_NAME_EMPTY:
                return R.string.error_client_name_empty;
            case CLIENT_NAME_TAKEN:
                return R.string.error_client_name_taken;
            case CLIENT_NAME_TOO_LONG:
                return R.string.error_client_name_too_long;
            case CLIENT_HAS_A_ROOM:
                return R.string.error_client_has_a_room;
            case CLIENT_HAS_NO_ROOM:
                return R.string.error_client_has_no_room;
            case CLIENT_NOT_FOUND_IN_ROOM:
                return R.string.error_client_not_found_in_room;
            case CLIENT_NOT_FOUND:
                return R.string.error_client_not_found;
            case CLIENT_HAS_NO_TURN:
                return R.string.error_client_has_no_turn;
            case CLIENT_HAS_NO_SYMBOL:
                return R.string.error_client_has_no_symbol;

            case ROOM_NAME_EMPTY:
                return R.string.error_room_name_empty;
            case ROOM_NAME_TAKEN:
                return R.string.error_room_name_taken;
            case ROOM_NAME_TOO_LONG:
                return R.string.error_room_name_too_long;
            case ROOM_FULL:
                return R.string.error_room_full;
            case ROOM_NOT_FOUND:
                return R.string.error_room_not_found;
            case ROOM_NOT_EMPTY:
                return R.string.error_room_not_empty;
            case ROOM_PASSWORD_MISMATCH:
                return R.string.error_room_password_mismatch;
            case ROOM_PASSWORD_TOO_LONG:
                return R.string.error_room_password_too_long;
            case ROOM_PASSWORD_NOT_FOUND:
                return R.string.error_room_password_not_found;

            case ENEMY_NOT_FOUND_BUT_SHOULD_BE:
                return R.string.error_enemy_not_found_but_should_be;
            case ENEMY_NOT_FOUND:
                return R.string.error_enemy_not_found;

            case POSITION_ALREADY_OCCUPIED:
                return R.string.error_position_already_occupied;
            case POSITION_OUT_OF_BOUND:
                return R.string.error_position_out_of_bound;
            case THERE_IS_A_WINNER:
                return R.string.error_there_is_a_winner;
        }

        return R.string.error_undefined;
    }

}
