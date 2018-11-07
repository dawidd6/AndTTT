package com.github.dawidd6.andttt.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.dialogs.ErrorDialogFragment;
import com.github.dawidd6.andttt.dialogs.LoadingDialogFragment;
import com.github.dawidd6.andttt.proto.ERROR;
import com.github.dawidd6.andttt.proto.Message;
import com.github.dawidd6.andttt.proto.TYPE;

import static com.github.dawidd6.andttt.MainActivity.client;
import static com.github.dawidd6.andttt.MainActivity.errorDialogFragment;
import static com.github.dawidd6.andttt.MainActivity.loadingDialogFragment;

public class NamingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_naming, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        client.setOnMessageReceivedListener((message) -> {
            if(message.getType() != TYPE.REGISTER_NAME)
                return;

            try {
                Thread.sleep(500);
                if(message.getErr() == ERROR.NONE) {
                    Log.i("REGISTER_NAME", "ok");
                    loadingDialogFragment.dismiss();
                    ((MainActivity)getActivity()).switchFragments(new RoomServiceFragment(), false);
                } else {
                    Log.i("REGISTER_NAME", "fail");
                    if(message.getErr() == ERROR.NAME_ALREADY_TAKEN)
                        errorDialogFragment.setText(R.string.error_name_already_taken);
                    else if(message.getErr() == ERROR.NAME_MUST_NOT_BE_EMPTY)
                        errorDialogFragment.setText(R.string.error_name_must_not_be_empty);
                    else
                        errorDialogFragment.setText(R.string.error_unexpected);
                    errorDialogFragment.show(getFragmentManager(), null);
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });

        errorDialogFragment.setOnOkClickListener((v) -> {
            errorDialogFragment.dismiss();
        });

        Button enterButton = view.findViewById(R.id.enterButton);
        enterButton.setOnClickListener((v) -> {
            loadingDialogFragment.show(getFragmentManager(), null);

            EditText nick = view.findViewById(R.id.nick);
            Message message = Message.newBuilder()
                    .setType(TYPE.REGISTER_NAME)
                    .setName(nick.getText().toString())
                    .build();

            client.send(message);
        });
    }
}
