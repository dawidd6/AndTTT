package com.github.dawidd6.andttt.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.dialogs.ErrorDialogFragment;
import com.github.dawidd6.andttt.proto.Error;
import com.github.dawidd6.andttt.proto.RegisterNameRequest;
import com.github.dawidd6.andttt.proto.Request;
import com.github.dawidd6.andttt.proto.Response;

import static com.github.dawidd6.andttt.OnlineActivity.client;
import static com.github.dawidd6.andttt.OnlineActivity.name;

public class ConnectFragment extends BaseFragment {
    private Button okButton;
    private EditText addressEdit;
    private EditText nameEdit;
    private ErrorDialogFragment errorDialogFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connect, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        errorDialogFragment = new ErrorDialogFragment();
        errorDialogFragment.setOnOkClickListener((v) -> {
            errorDialogFragment.dismiss();
        });

        addressEdit = view.findViewById(R.id.addressEdit);
        nameEdit = view.findViewById(R.id.nameEdit);

        client.setOnConnectFailedListener(() -> {
            errorDialogFragment.setText(R.string.connection_failed);
            errorDialogFragment.show(getFragmentManager(), null);
        });
        client.setOnConnectSuccessfulListener(() -> {
            register();
        });
        client.setOnResponseListener((response) -> {
            if(response.getTypeCase() != Response.TypeCase.REGISTER_NAME)
                return;

            if(response.getError() != Error.NONE) {
                errorDialogFragment.setText(null);
                errorDialogFragment.setErrorCode(response.getError());
                errorDialogFragment.show(getFragmentManager(), null);
                return;
            }

            name = response.getRegisterName().getName();
            ((MainActivity)getActivity()).switchFragments(new RoomsFragment(), false);
        });

        okButton = view.findViewById(R.id.okButton);
        okButton.setOnClickListener((v) -> {
            connect();
            register();
        });
    }

    private void connect() {
        if(!client.isConnected()) {
            String address = addressEdit.getText().toString();
            if (address.isEmpty())
                address = addressEdit.getHint().toString();
            String split[] = address.split(":");
            client.connect(split[0], Integer.valueOf(split[1]));
        }
    }

    private void register() {
        if(client.isConnected()) {
            String name = nameEdit.getText().toString();
            if (name.isEmpty())
                name = nameEdit.getHint().toString();
            Request request = Request.newBuilder()
                    .setRegisterName(RegisterNameRequest.newBuilder()
                            .setName(name))
                    .build();
            client.send(request);
        }
    }
}
