package com.github.dawidd6.andttt.dialogs;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import butterknife.BindView;
import com.github.dawidd6.andttt.R;

public class LoadingDialogFragment extends BaseDialogFragment {
    private int text;
    @BindView(R.id.waitText) TextView waitText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_loading, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        waitText.setText(text);
    }

    public void setText(int text) {
        this.text = text;
    }
}
