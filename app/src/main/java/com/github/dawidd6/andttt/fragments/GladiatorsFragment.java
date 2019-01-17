package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.activities.MainActivity;
import com.github.dawidd6.andttt.bots.Bot;
import com.github.dawidd6.andttt.bots.EasyBot;
import com.github.dawidd6.andttt.bots.HardBot;
import com.github.dawidd6.andttt.bots.MediumBot;

public class GladiatorsFragment extends BaseFragment {
    public static final String TAG = "GladiatorsFragment";
    @BindView(R.id.firstSpinner) Spinner firstSpinner;
    @BindView(R.id.secondSpinner) Spinner secondSpinner;
    @BindArray(R.array.difficulties) String difficulties[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gladiators, parent, false);
    }

    @OnClick(R.id.okButton)
    public void onOkButtonClick() {
        ArenaFragment fragment = new ArenaFragment();
        fragment.setBots(getSelectedBot(firstSpinner), getSelectedBot(secondSpinner));
        MainActivity.switchFragment(getFragmentManager(), fragment, true);
    }

    private Bot getSelectedBot(Spinner spinner) {
        switch(spinner.getSelectedItemPosition()) {
            case 0:
                return new EasyBot();
            case 1:
                return new MediumBot();
            case 2:
                return new HardBot();
        }

        return null;
    }
}
