package com.github.dawidd6.andttt.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.events.ConnectEvent;
import com.github.dawidd6.andttt.events.ConnectFailEvent;
import com.github.dawidd6.andttt.events.ConnectSuccessEvent;
import com.github.dawidd6.andttt.events.ConnectTimeoutEvent;
import com.github.dawidd6.andttt.events.DisconnectEvent;
import com.github.dawidd6.andttt.events.SendEvent;
import com.github.dawidd6.andttt.proto.Error;
import com.github.dawidd6.andttt.proto.RegisterNameResponse;
import com.github.dawidd6.andttt.proto.Response;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.Objects;

import static com.github.dawidd6.andttt.activities.OnlineActivity.bus;

public class ClientService extends Service {
    public static final String TAG = "Service";

    private Socket socket;
    private NotificationManagerCompat notificationManager;

    @Override
    public void onDestroy() {
        super.onDestroy();

        notificationManager.cancelAll();

        bus.unregister(this);

        if(socket != null) {
            try {
                socket.close();
                socket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = NotificationManagerCompat.from(this);

        bus.register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onSend(SendEvent event) {
        if(socket == null)
            return;

        byte[] data = event.getRequest().toByteArray();
        ByteBuffer buffer = ByteBuffer.allocate(4 + data.length);
        buffer.putInt(data.length);
        buffer.put(data);

        try {
            socket.getOutputStream().write(buffer.array());
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterName(RegisterNameResponse response) {
        showNotification();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onConnect(ConnectEvent event) {
        try {
            int timeout = 5000;
            socket = new Socket();
            socket.connect(new InetSocketAddress(event.getHost(), event.getPort()), timeout);
            bus.post(new ConnectSuccessEvent(socket.getRemoteSocketAddress().toString()));

            while (true) {
                try {
                    ByteBuffer buffer;

                    // read first 4 bytes to get the length of data
                    buffer = ByteBuffer.allocate(4);
                    if (socket.getInputStream().read(buffer.array()) < 0)
                        throw new IOException();

                    // read the rest of bytes to get data
                    buffer = ByteBuffer.allocate(buffer.getInt());
                    if (socket.getInputStream().read(buffer.array()) < 0)
                        throw new IOException();

                    Response response = Response.parseFrom(buffer);
                    dispatch(response);
                } catch (IOException e) {
                    e.printStackTrace();
                    if(socket != null) {
                        socket.close();
                        socket = null;
                        bus.post(new DisconnectEvent());
                    }
                    break;
                }
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            bus.post(new ConnectTimeoutEvent());
        } catch (IOException e) {
            e.printStackTrace();
            bus.post(new ConnectFailEvent());
        }
    }

    private void showNotification() {
        Intent i = Objects.requireNonNull(getPackageManager()
                .getLaunchIntentForPackage(getPackageName()))
                .setPackage(null)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent defaultPendingIntent = PendingIntent.getActivity(this, 0, i, 0);

        String channelID = "channel-online";
        String channelName = "Channel Online";

        Intent intentAction = new Intent("andttt-online-leave");
        PendingIntent leavePendingIntent = PendingIntent.getBroadcast(this,1, intentAction, PendingIntent.FLAG_ONE_SHOT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mgr = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW);
            Objects.requireNonNull(mgr).createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(ClientService.this, channelID)
                .addAction(R.drawable.ic_status_icon, getString(R.string.leave), leavePendingIntent)
                .setContentIntent(defaultPendingIntent)
                .setContentTitle(getString(R.string.connected))
                .setContentText(socket.getRemoteSocketAddress().toString())
                .setSmallIcon(R.drawable.ic_status_icon)
                .setAutoCancel(false)
                .setOngoing(true)
                .build();

        notificationManager.cancel(0);
        notificationManager.notify(0, notification);
    }

    private void dispatch(Response response) {
        if(response.getError() != Error.NONE) {
            bus.post(response.getError());
            return;
        }

        switch(response.getTypeCase()) {
            case REGISTER_NAME:
                bus.post(response.getRegisterName());
                break;
            case CREATE_ROOM:
                bus.post(response.getCreateRoom());
                break;
            case JOIN_ROOM:
                bus.post(response.getJoinRoom());
                break;
            case STARTER_PACK:
                bus.post(response.getStarterPack());
                break;
            case MOVE:
                bus.post(response.getMove());
                break;
            case ENEMY_DISCONNECTED:
                bus.post(response.getEnemyDisconnected());
                break;
            case ENEMY_LEFT:
                bus.post(response.getEnemyLeft());
                break;
            case ENEMY_MOVED:
                bus.post(response.getEnemyMoved());
                break;
            case RESTART:
                bus.post(response.getRestart());
                break;
            case GET_ROOMS:
                bus.post(response.getGetRooms());
                break;
            case LEAVE_ROOM:
                bus.post(response.getLeaveRoom());
                break;
            case UNRECOGNIZED:
                bus.post(response.getUnrecognized());
                break;
        }

    }
}
