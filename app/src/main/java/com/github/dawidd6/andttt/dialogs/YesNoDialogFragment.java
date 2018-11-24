package com.github.dawidd6.andttt.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.github.dawidd6.andttt.R;

public class YesNoDialogFragment extends BaseDialogFragment {
    private View.OnClickListener yesButtonListener;
    private View.OnClickListener noButtonListener;
    private int text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_yesno, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button yesButton = view.findViewById(R.id.yesButton);
        yesButton.setOnClickListener(yesButtonListener);

        Button noButton = view.findViewById(R.id.noButton);
        noButton.setOnClickListener(noButtonListener);

        TextView questionText = view.findViewById(R.id.addressText);
        questionText.setText(text);
    }

    public void setOnYesClickListener(View.OnClickListener listener) {
        this.yesButtonListener = listener;
    }

    public void setOnNoClickListener(View.OnClickListener listener) {
        this.noButtonListener = listener;
    }

    public void setText(int text) {
        this.text = text;
    }
}
