package com.github.dawidd6.andttt.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.proto.Error;

public class InputDialogFragment extends BaseDialogFragment {
    private int textRes;
    private int inputType;
    @BindView(R.id.text) TextView text;
    @BindView(R.id.edit) EditText edit;
    private OnClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_input, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text.setText(textRes);
        edit.setInputType(inputType);
    }

    @OnClick(R.id.okButton)
    public void onOkButtonClick(View v) {
        listener.onClick(v, edit.getText().toString());
    }

    public void set(int textRes, int inputType, OnClickListener listener) {
        this.listener = listener;
        this.textRes = textRes;
        this.inputType = inputType;
    }

    public interface OnClickListener {
        void onClick(View view, String input);
    }
}
