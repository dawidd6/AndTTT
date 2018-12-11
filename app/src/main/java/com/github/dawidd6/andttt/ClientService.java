package com.github.dawidd6.andttt;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import com.github.dawidd6.andttt.events.*;
import com.github.dawidd6.andttt.proto.Error;
import com.github.dawidd6.andttt.proto.Response;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;

public class ClientService extends Service {
    private Socket socket;
    private NotificationManagerCompat notificationManager;
    private int notificationsInc;
    private final int NOTIFICATION_ID = 1;
    public static final String TAG = "Service";
    private final int BUFFER_SIZE = 4096;

    @Override
    public void onDestroy() {
        super.onDestroy();

        notificationManager.cancelAll();

        EventBus.getDefault().unregister(this);

        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = NotificationManagerCompat.from(this);

        EventBus.getDefault().register(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onSend(SendEvent event) {
        if(socket == null)
            return;

        try {
            socket.getOutputStream().write(event.getRequest().toByteArray());
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onConnect(ConnectEvent event) {
        try {
            socket = new Socket(event.getHost(), event.getPort());
            EventBus.getDefault().post(new ConnectSuccessEvent(socket.getRemoteSocketAddress().toString()));

            while(true) {
                try {
                    byte buffer[] = new byte[BUFFER_SIZE];
                    int length = socket.getInputStream().read(buffer);
                    if(length < 0)
                        throw new IOException();
                    buffer = Arrays.copyOfRange(buffer, 0, length);
                    Response response = Response.parseFrom(buffer);
                    dispatch(response);
                } catch (IOException e) {
                    e.printStackTrace();
                    if(socket == null)
                        return;

                    socket.close();
                    socket = null;
                    EventBus.getDefault().post(new DisconnectEvent());
                    break;
                }
            }

            Log.i(TAG, "disconnect");
        } catch (IOException e) {
            EventBus.getDefault().post(new ConnectFailEvent());
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotify(NotifyEvent event) {
        Intent i = Objects.requireNonNull(getPackageManager()
                .getLaunchIntentForPackage(getPackageName()))
                .setPackage(null)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent pendingIntent = PendingIntent.getActivity(ClientService.this, 0, i, 0);

        Notification notification = new NotificationCompat.Builder(ClientService.this, "default")
                .setContentIntent(pendingIntent)
                .setContentTitle(event.getTitle())
                .setContentText(event.getText())
                .setSmallIcon(R.drawable.ic_status_icon)
                .setAutoCancel(!event.isPersistent())
                .setOngoing(event.isPersistent())
                .build();

        if(event.isPersistent()) {
            notificationManager.cancel(0);
            notificationManager.notify(0, notification);
        } else {
            notificationManager.notify(++notificationsInc, notification);
        }
    }

    private void dispatch(Response response) {
        if(response.getError() != Error.NONE) {
            EventBus.getDefault().post(response.getError());
            return;
        }

        switch(response.getTypeCase()) {
            case REGISTER_NAME:
                EventBus.getDefault().post(response.getRegisterName());
                break;
            case CREATE_ROOM:
                EventBus.getDefault().post(response.getCreateRoom());
                break;
            case JOIN_ROOM:
                EventBus.getDefault().post(response.getJoinRoom());
                break;
            case STARTER_PACK:
                EventBus.getDefault().post(response.getStarterPack());
                break;
            case MOVE:
                EventBus.getDefault().post(response.getMove());
                break;
            case ENEMY_DISCONNECTED:
                EventBus.getDefault().post(response.getEnemyDisconnected());
                break;
            case ENEMY_LEFT:
                EventBus.getDefault().post(response.getEnemyLeft());
                break;
            case ENEMY_MOVED:
                EventBus.getDefault().post(response.getEnemyMoved());
                break;
            case RESTART:
                EventBus.getDefault().post(response.getRestart());
                break;
            case GET_ROOMS:
                EventBus.getDefault().post(response.getGetRooms());
                break;
            case LEAVE_ROOM:
                EventBus.getDefault().post(response.getLeaveRoom());
                break;
            case UNRECOGNIZED:
                EventBus.getDefault().post(response.getUnrecognized());
                break;
        }

    }
}
