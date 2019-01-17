package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.OnClick;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.activities.MainActivity;
import com.github.dawidd6.andttt.bots.Bot;
import com.github.dawidd6.andttt.bots.EasyBot;
import com.github.dawidd6.andttt.bots.HardBot;
import com.github.dawidd6.andttt.bots.MediumBot;

public class DifficultyFragment extends BaseFragment {
    public static final String TAG = "DifficultyFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_difficulty, parent, false);
    }

    @OnClick(R.id.easyButton)
    public void onEasyButtonClick() {
        launch(new EasyBot());
    }

    @OnClick(R.id.mediumButton)
    public void onMediumButtonClick() {
        launch(new MediumBot());
    }

    @OnClick(R.id.hardButton)
    public void onHardButtonClick() {
        launch(new HardBot());
    }

    private void launch(Bot bot) {
        SingleFragment singleFragment = new SingleFragment();
        singleFragment.setBot(bot);
        MainActivity.switchFragment(getFragmentManager(), singleFragment, true);
    }
}
