package com.github.dawidd6.andttt.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.events.DisconnectEvent;
import com.github.dawidd6.andttt.proto.Error;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class BaseFragment extends Fragment {
    private MaterialDialog loadingDialog;
    private MaterialDialog dialogToShow;
    private MaterialDialog dialogToDismiss;

    protected boolean isOnline;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        if(!isOnline)
            return;

        ConnectivityManager connManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        assert connManager != null;
        connManager.registerNetworkCallback(builder.build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onLost(Network network) {
                super.onLost(network);

                onDisconnect(null);
            }
        });
    }

    public void showError(int content, MaterialDialog.SingleButtonCallback callback) {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.error)
                .content(content)
                .positiveText(R.string.ok)
                .onPositive(callback)
                .build();

        show(dialog);
    }

    public void showInput(int content, int inputType, MaterialDialog.SingleButtonCallback callback) {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.input)
                .content(content)
                .inputType(inputType)
                .input("", "", false, ((d, i) -> {}))
                .positiveText(R.string.ok)
                .onPositive(callback)
                .build();

        show(dialog);
    }

    public void showYesNo(int content, MaterialDialog.SingleButtonCallback yesCallback, MaterialDialog.SingleButtonCallback noCallback) {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.question)
                .content(content)
                .cancelable(false)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive(yesCallback)
                .onNegative(noCallback)
                .build();

        show(dialog);
    }

    public void showLoading(int content) {
        loadingDialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.please_wait)
                .content(content)
                .cancelable(false)
                .progress(true, 0)
                .build();

        show(loadingDialog);
    }

    public void dismissLoading() {
        if(isResumed()) {
            loadingDialog.dismiss();
        } else {
            dialogToDismiss = loadingDialog;
        }
    }

    private void show(MaterialDialog dialog) {
        if(isResumed()) {
            dialog.show();
        } else {
            dialogToShow = dialog;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!isOnline)
            return;
        
        if(dialogToDismiss != null) {
            dialogToDismiss.dismiss();
            dialogToDismiss = null;
        }

        if(dialogToShow != null) {
            dialogToShow.show();
            dialogToShow = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if(!isOnline)
            return;

        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        if(!isOnline)
            return;

        if(isRemoving())
            EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDisconnect(DisconnectEvent event) {
        if(isRemoving())
            return;

        showError(R.string.disconnected, ((dialog, which) -> {
            dialog.dismiss();
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            android.R.animator.fade_in,
                            android.R.animator.fade_out,
                            android.R.animator.fade_in,
                            android.R.animator.fade_out)
                    .replace(R.id.placeholder, new MenuFragment(), MenuFragment.TAG)
                    .commit();
        }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onError(Error error) {
        showError(MainActivity.getErrorStringRes(error), ((dialog, which) -> dialog.dismiss()));
    }
}
