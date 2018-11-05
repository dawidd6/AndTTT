package com.github.dawidd6.andttt.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.github.dawidd6.andttt.R;

public class ErrorDialogFragment extends BaseDialogFragment {
    private View.OnClickListener okButtonListener;
    private int text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_error, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button okButton = view.findViewById(R.id.okButton);
        okButton.setOnClickListener(okButtonListener);

        TextView errorText = view.findViewById(R.id.text);
        errorText.setText(text);
    }

    public void setOnOkClickListener(View.OnClickListener listener) {
        this.okButtonListener = listener;
    }

    public void setText(int text) {
        this.text = text;
    }
}
