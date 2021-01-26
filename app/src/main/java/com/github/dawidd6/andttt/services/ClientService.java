package com.github.dawidd6.andttt.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.events.*;
import com.github.dawidd6.andttt.proto.Error;
import com.github.dawidd6.andttt.proto.Response;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

import static com.github.dawidd6.andttt.activities.OnlineActivity.bus;

public class ClientService extends Service {
    public static final String TAG = "Service";
    private final int BUFFER_SIZE = 4096;
    private final int timeout = 5000;

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

        byte data[] = event.getRequest().toByteArray();
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

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onConnect(ConnectEvent event) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(event.getHost(), event.getPort()), timeout);
            bus.post(new ConnectSuccessEvent(socket.getRemoteSocketAddress().toString()));
            showNotification();

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

        PendingIntent pendingIntent = PendingIntent.getActivity(ClientService.this, 0, i, 0);

        String channelID = "channel-online";
        String channelName = "Channel Online";
        int importance = NotificationManagerCompat.IMPORTANCE_LOW;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(ClientService.this, channelID)
                .setContentIntent(pendingIntent)
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
