package com.github.dawidd6.andttt.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.github.dawidd6.andttt.R;

public class YesNoDialogFragment extends BaseDialogFragment {
    private View.OnClickListener yesButtonListener;
    private View.OnClickListener noButtonListener;
    private int text;
    @BindView(R.id.yesButton) Button yesButton;
    @BindView(R.id.noButton) Button noButton;
    @BindView(R.id.questionText) TextView questionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_yesno, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        yesButton.setOnClickListener(yesButtonListener);
        noButton.setOnClickListener(noButtonListener);
        questionText.setText(text);
    }

    @OnClick(R.id.yesButton)
    public void onYesButtonClick(View v) {
        if(yesButtonListener != null)
            yesButtonListener.onClick(v);
    }

    @OnClick(R.id.yesButton)
    public void onNoButtonClick(View v) {
        if(noButtonListener != null)
            noButtonListener.onClick(v);
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
