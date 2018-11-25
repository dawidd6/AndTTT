package com.github.dawidd6.andttt;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import com.github.dawidd6.andttt.dialogs.*;
import com.github.dawidd6.andttt.fragments.ClientFragment;
import com.github.dawidd6.andttt.fragments.ConnectFragment;

import java.util.Objects;

public class OnlineActivity extends MainActivity {
    public static ClientFragment client;
    public static String name;
    private NotificationManagerCompat notificationManager;
    private ErrorDialogFragment errorDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        errorDialogFragment = new ErrorDialogFragment();
        notificationManager = NotificationManagerCompat.from(this);

        if(savedInstanceState == null) {
            client = new ClientFragment();
            client.setOnDisconnectListener(() -> {
                if (isFinishing())
                    return;

                errorDialogFragment.setText(R.string.disconnected);
                errorDialogFragment.setOnOkClickListener((v) -> {
                    errorDialogFragment.dismiss();
                    finish();
                });
                errorDialogFragment.show(getFragmentManager(), null);
            });

            name = "";

            switchFragments(getFragmentManager(), new ConnectFragment(), false);
        }

        Log.i("name", name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(isChangingConfigurations())
            return;

        client.disconnect();
        client.destroy();
        client = null;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(isChangingConfigurations())
            return;

        if(isFinishing())
            return;

        if(!client.isConnected())
            return;

        Intent intent = Objects.requireNonNull(getPackageManager()
                .getLaunchIntentForPackage(getPackageName()))
                .setPackage(null)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(this, "default")
                .setContentIntent(pendingIntent)
                .setContentText(client.getAddress())
                .setContentTitle(getString(R.string.connected)) // TODO localization
                .setSmallIcon(R.drawable.ic_status_icon)
                .setOngoing(true)
                .build();

        notificationManager.cancel(0);
        notificationManager.notify(0, notification);
    }

    @Override
    protected void onResume() {
        super.onResume();

        notificationManager.cancel(0);
    }
}
