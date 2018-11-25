package com.github.dawidd6.andttt.fragments;

import android.os.Bundle;
import android.app.Fragment;

public abstract class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
