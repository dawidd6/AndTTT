package com.github.dawidd6.andttt.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.Client;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.dialogs.ErrorDialogFragment;
import com.github.dawidd6.andttt.dialogs.LoadingDialogFragment;

import static com.github.dawidd6.andttt.MainActivity.client;
import static com.github.dawidd6.andttt.MainActivity.errorDialogFragment;
import static com.github.dawidd6.andttt.MainActivity.loadingDialogFragment;

public class ConnectFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connect, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        errorDialogFragment.setOnOkClickListener((v) -> {
            errorDialogFragment.dismiss();
        });

        client.setOnConnectSuccessfulListener(() -> {
            Log.i("CONNECT", "SUCCESS");
            try {
                Thread.sleep(500);
                loadingDialogFragment.dismiss();
                client.setOnConnectSuccessfulListener(null);
                client.setOnConnectFailedListener(null);
                ((MainActivity)getActivity()).switchFragments(new NamingFragment(), false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        client.setOnConnectFailedListener(() -> {
            Log.i("CONNECT", "FAILED");
            try {
                Thread.sleep(500);
                loadingDialogFragment.dismiss();
                errorDialogFragment.setText(R.string.connection_failed);
                errorDialogFragment.show(getFragmentManager(), null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Button connectButton = view.findViewById(R.id.connectButton);
        connectButton.setOnClickListener((v) -> {
            EditText host = view.findViewById(R.id.nick);
            EditText port = view.findViewById(R.id.port);

            loadingDialogFragment.setText(R.string.connecting);
            loadingDialogFragment.show(getFragmentManager(), null);

            client.connect(host.getText().toString(), Integer.valueOf(port.getText().toString()));
        });
    }
}
