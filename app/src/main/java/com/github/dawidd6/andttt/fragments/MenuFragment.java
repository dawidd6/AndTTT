package com.github.dawidd6.andttt.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.ClientService;
import com.github.dawidd6.andttt.R;

public class MenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, parent, false);
        getActivity().stopService(new Intent(getActivity(), ClientService.class));
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.localButton)
    public void onLocalButtonClick() {
        MainActivity.switchFragments(getFragmentManager(), new LocalFragment(), true);
    }

    @OnClick(R.id.singleButton)
    public void onSingleButtonClick() {
        MainActivity.switchFragments(getFragmentManager(), new SingleFragment(), true);
    }

    @OnClick(R.id.onlineButton)
    public void onOnlineButtonClick() {
        MainActivity.switchFragments(getFragmentManager(), new ConnectFragment(), true);
    }

    @OnClick(R.id.settingsButton)
    public void onSettingsButtonClick() {
        MainActivity.switchFragments(getFragmentManager(), new SettingsFragment(), true);
    }
}
