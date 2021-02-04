package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.navigation.Navigation;

import com.github.dawidd6.andttt.R;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

public class GladiatorsFragment extends BaseFragment {
    public static final String TAG = "GladiatorsFragment";
    @BindView(R.id.firstSpinner) Spinner firstSpinner;
    @BindView(R.id.secondSpinner) Spinner secondSpinner;
    @BindArray(R.array.difficulties) String[] difficulties;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gladiators, parent, false);
    }

    @OnClick(R.id.okButton)
    public void onOkButtonClick() {
        launch(firstSpinner.getSelectedItemPosition(), secondSpinner.getSelectedItemPosition());
    }

    private void launch(int firstBotSpinnerID, int secondBotSpinnerID) {
        Bundle bundle = new Bundle();
        bundle.putInt("first-bot-spinner-id", firstBotSpinnerID);
        bundle.putInt("second-bot-spinner-id", secondBotSpinnerID);
        Navigation.findNavController(requireActivity(), R.id.navigation_host_main).navigate(R.id.action_gladiatorsFragment_to_arenaFragment, bundle);
    }
}
