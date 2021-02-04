package com.github.dawidd6.andttt.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.events.SendEvent;
import com.github.dawidd6.andttt.proto.CreateRoomRequest;
import com.github.dawidd6.andttt.proto.CreateRoomResponse;
import com.github.dawidd6.andttt.proto.Request;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.github.dawidd6.andttt.activities.OnlineActivity.bus;
import static com.github.dawidd6.andttt.activities.OnlineActivity.dialogManager;


public class CreateFragment extends BaseFragment {
    public static final String TAG = "CreateFragment";
    private boolean isProtectedChecked;
    @BindView(R.id.roomEdit) EditText roomEdit;
    @BindView(R.id.passwordEdit) EditText passwordEdit;
    @BindView(R.id.passwordText) TextView passwordText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        passwordEdit.setVisibility(View.GONE);
        passwordText.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bus.register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bus.unregister(this);
    }

    @OnClick(R.id.okButton)
    public void onOkButtonClick() {
        dialogManager.showLoading(getActivity(), R.string.creating);

        Request request = Request.newBuilder()
                .setCreateRoom(CreateRoomRequest.newBuilder()
                        .setName(roomEdit.getText().toString())
                        .setPassword(isProtectedChecked ? passwordEdit.getText().toString() : ""))
                .build();

        bus.post(new SendEvent(request));
    }

    @OnCheckedChanged(R.id.protectedCheck)
    public void onProtectedChecked(CompoundButton button, boolean checked) {
        isProtectedChecked = checked;

        passwordEdit.setVisibility(checked ? View.VISIBLE : View.GONE);
        passwordText.setVisibility(checked ? View.VISIBLE : View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateRoom(CreateRoomResponse response) {
        dialogManager.dismiss();
        Navigation.findNavController(requireActivity(), R.id.navigation_host_online).popBackStack();
    }
}
