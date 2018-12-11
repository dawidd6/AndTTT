package com.github.dawidd6.andttt.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.ClientService;
import com.github.dawidd6.andttt.R;

public class MenuFragment extends Fragment {
    public static final String TAG = "MenuFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, parent, false);
        getActivity().stopService(new Intent(getActivity(), ClientService.class));
        ButterKnife.bind(this, view);

        // workaround for destroying RoomsFragment
        Fragment f = getFragmentManager().findFragmentByTag(RoomsFragment.TAG);
        if(f != null)
            getFragmentManager().beginTransaction().remove(f).commit();

        return view;
    }

    @OnClick(R.id.localButton)
    public void onLocalButtonClick() {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(LocalFragment.TAG)
                .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out,
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(R.id.placeholder, new LocalFragment(), LocalFragment.TAG)
                .commit();
    }

    @OnClick(R.id.singleButton)
    public void onSingleButtonClick() {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(SingleFragment.TAG)
                .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out,
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(R.id.placeholder, new SingleFragment(), SingleFragment.TAG)
                .commit();
    }

    @OnClick(R.id.onlineButton)
    public void onOnlineButtonClick() {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(ConnectFragment.TAG)
                .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out,
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(R.id.placeholder, new ConnectFragment(), ConnectFragment.TAG)
                .commit();
    }

    @OnClick(R.id.settingsButton)
    public void onSettingsButtonClick() {
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(SettingsFragment.TAG)
                .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out,
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(R.id.placeholder, new SettingsFragment(), SettingsFragment.TAG)
                .commit();
    }
}
