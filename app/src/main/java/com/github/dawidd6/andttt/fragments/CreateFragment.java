package com.github.dawidd6.andttt.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.dialogs.ErrorDialogFragment;
import com.github.dawidd6.andttt.proto.CreateRoomRequest;
import com.github.dawidd6.andttt.proto.Error;
import com.github.dawidd6.andttt.proto.Request;
import com.github.dawidd6.andttt.proto.Response;

import static com.github.dawidd6.andttt.OnlineActivity.client;

public class CreateFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
        errorDialogFragment.setOnOkClickListener((v) -> {
            errorDialogFragment.dismiss();
        });

        client.setOnResponseListener((response) -> {
            if(response.getTypeCase() != Response.TypeCase.CREATE_ROOM)
                return;

            if(response.getError() != Error.NONE) {
                errorDialogFragment.setText(null);
                errorDialogFragment.setErrorCode(response.getError());
                errorDialogFragment.show(getFragmentManager(), null);
                return;
            }

            getActivity().runOnUiThread(() -> getActivity().onBackPressed());
        });

        Button okButton = view.findViewById(R.id.okButton);
        okButton.setOnClickListener((v) -> {
            EditText editText = view.findViewById(R.id.addressEdit);
            Request request = Request.newBuilder()
                    .setCreateRoom(CreateRoomRequest.newBuilder()
                            .setName(editText.getText().toString()))
                    .build();

            client.send(request);
        });
    }
}
