package com.github.dawidd6.andttt.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.OnClick;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.activities.MainActivity;
import com.github.dawidd6.andttt.ai.AI;
import com.github.dawidd6.andttt.ai.HighAI;
import com.github.dawidd6.andttt.ai.LowAI;
import com.github.dawidd6.andttt.ai.MediumAI;

public class DifficultyFragment extends BaseFragment {
    public static final String TAG = "DifficultyFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_difficulty, parent, false);
    }

    @OnClick(R.id.lowButton)
    public void onLowButtonClick() {
        launch(new LowAI());
    }

    @OnClick(R.id.mediumButton)
    public void onMediumButtonClick() {
        launch(new MediumAI());
    }

    @OnClick(R.id.highButton)
    public void onHighButtonClick() {
        launch(new HighAI());
    }

    private void launch(AI ai) {
        SingleFragment singleFragment = new SingleFragment();
        singleFragment.setAI(ai);
        MainActivity.switchFragment(getFragmentManager(), singleFragment, true);
    }
}
