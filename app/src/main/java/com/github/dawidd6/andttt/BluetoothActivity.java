package com.github.dawidd6.andttt;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

// https://developer.android.com/guide/topics/connectivity/bluetooth#ConnectDevices

public class BluetoothActivity extends BaseActivity {

    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> pairedDevices;
    IntentFilter intentFilter;
    String permissions[] = {Manifest.permission.ACCESS_COARSE_LOCATION};
    int role;
    LinearLayout devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        role = getIntent().getExtras().getInt("mode");

        switch(role) {
            case 0:
                Log.i("MODE", "Host");
                /*Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300); // seconds
                startActivity(discoverableIntent);
                dialog.show();*/

                new AlertDialog.Builder(this, theme_dialog)
                        .setTitle("Waiting for opponent")
                        .setCancelable(false)
                        .setView(new ProgressBar(this))
                        .show();
                break;
            case 1:
                Log.i("MODE", "Guest");
                setContentView(R.layout.activity_bluetooth);
                break;
        }

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = bluetoothAdapter.getBondedDevices();
        intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        devices = findViewById(R.id.devices_layout);
        registerReceiver(receiver, intentFilter);

        requestPermissions();
        setBluetoothEnabled(true);

        if(bluetoothAdapter.isDiscovering())
            bluetoothAdapter.cancelDiscovery();

        if(pairedDevices.size() > 0) {
            for(BluetoothDevice device : pairedDevices) {
                addDevice(device.getName(), device.getAddress());
                Log.i("BLUETOOTH", "paired device found");
            }
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            switch(Objects.requireNonNull(intent.getAction())){
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    Log.i("BLUETOOTH", "discovery started");
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Log.i("BLUETOOTH", "discovery finished");
                    break;
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    addDevice(device.getName(), device.getAddress());
                    Log.i("BLUETOOTH", "device found");
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        setBluetoothEnabled(false);
        unregisterReceiver(receiver);
    }

    public void onClickDevice(View view) {
        TextView text = view.findViewById(R.id.device_address);
        Log.i("onClickDevice", text.getText().toString());
    }

    public void addDevice(String name, String address) {
        View v = View.inflate(this, R.layout.device,null);
        TextView n = v.findViewById(R.id.device_name);
        TextView a = v.findViewById(R.id.device_address);
        n.setText(name);
        a.setText(address);
        devices.addView(v);
    }

    public void setBluetoothEnabled(boolean enabled) {
        if(enabled) {
            /*Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);*/
            bluetoothAdapter.enable();
            Log.i("BLUETOOTH", "enabled");
        } else if(bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
            Log.i("BLUETOOTH", "disabled");
        }
    }

    public void requestPermissions() {
        for(int i = 0; i < permissions.length; i++) {
            if(ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String [] {permissions[i]}, i);
        }
    }
}
