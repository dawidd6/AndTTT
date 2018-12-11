package com.github.dawidd6.andttt.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import butterknife.ButterKnife;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.dialogs.ErrorDialogFragment;
import com.github.dawidd6.andttt.events.DisconnectEvent;
import com.github.dawidd6.andttt.proto.Error;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class BaseOnlineFragment extends Fragment {
    protected ErrorDialogFragment errorDialogFragment;
    protected DialogFragment savedDialogFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        errorDialogFragment = new ErrorDialogFragment();
        errorDialogFragment.setOnOkClickListener((v) -> {
            errorDialogFragment.dismiss();
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(savedDialogFragment != null) {
            savedDialogFragment.show(getFragmentManager(), null);
            savedDialogFragment = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        if(isRemoving())
            EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDisconnect(DisconnectEvent event) {
        if(isRemoving())
            return;

        errorDialogFragment.setText(R.string.disconnected);
        errorDialogFragment.setOnOkClickListener((v) -> {
            errorDialogFragment.dismiss();
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            android.R.animator.fade_in,
                            android.R.animator.fade_out,
                            android.R.animator.fade_in,
                            android.R.animator.fade_out)
                    .replace(R.id.placeholder, new MenuFragment(), MenuFragment.TAG)
                    .commit();
        });

        if(isResumed()) {
            errorDialogFragment.show(getFragmentManager(), null);
        } else {
            savedDialogFragment = errorDialogFragment;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onError(Error error) {
        errorDialogFragment.setErrorCode(error);
        errorDialogFragment.setOnOkClickListener((v) -> {
            errorDialogFragment.dismiss();
        });

        if(isResumed()) {
            errorDialogFragment.show(getFragmentManager(), null);
        } else {
            savedDialogFragment = errorDialogFragment;
        }
    }
}
