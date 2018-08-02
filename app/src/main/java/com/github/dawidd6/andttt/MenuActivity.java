package com.github.dawidd6.andttt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

@SuppressWarnings("unused")
public class MenuActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void switchActivity(Class c) {
        final Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public void onClickPlaySingle(View view) {
        switchActivity(SingleActivity.class);
    }

    public void onClickPlayLocal(View view) {
        switchActivity(LocalActivity.class);
    }

    public void onClickPlayBluetooth(View view) {
        if(BluetoothAdapter.getDefaultAdapter() == null)
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_LONG).show();
        else {

            new AlertDialog.Builder(this, theme_dialog)
                    .setTitle("Pick role")
                    .setCancelable(true)
                    .setItems(R.array.dialog_items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("Which", Integer.toString(which));
                            startActivity(new Intent(getBaseContext(), BluetoothActivity.class).putExtra("mode", which));
                        }
                    })
                    .show();
        }
    }

    public void onClickSettings(View view) {
        switchActivity(SettingsActivity.class);
    }
}
