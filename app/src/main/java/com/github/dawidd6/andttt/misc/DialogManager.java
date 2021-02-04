package com.github.dawidd6.andttt.misc;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.dawidd6.andttt.R;

public class DialogManager {
    private MaterialDialog dialog;

    public void showError(Context context,
                          int content,
                          MaterialDialog.SingleButtonCallback callback) {
        dismiss();

        dialog = new MaterialDialog.Builder(context)
                .title(R.string.error)
                .content(content)
                .positiveText(R.string.ok)
                .onPositive(callback)
                .build();

        dialog.show();
    }

    public void showInput(Context context,
                          int content,
                          int inputType,
                          MaterialDialog.SingleButtonCallback callback) {
        dismiss();

        dialog = new MaterialDialog.Builder(context)
                .title(R.string.input)
                .content(content)
                .inputType(inputType)
                .input("", "", false, ((d, i) -> {}))
                .positiveText(R.string.ok)
                .onPositive(callback)
                .build();

        dialog.show();
    }

    public void showYesNo(Context context,
                          int content,
                          MaterialDialog.SingleButtonCallback yesCallback,
                          MaterialDialog.SingleButtonCallback noCallback) {
        dismiss();

        dialog = new MaterialDialog.Builder(context)
                .title(R.string.question)
                .content(content)
                .cancelable(false)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive(yesCallback)
                .onNegative(noCallback)
                .build();

        dialog.show();
    }

    public void showLoading(Context context,
                            int content) {
        dismiss();

        dialog = new MaterialDialog.Builder(context)
                .title(R.string.please_wait)
                .content(content)
                .cancelable(false)
                .progress(true, 0)
                .build();

        dialog.show();
    }
    
    public void dismiss() {
        if(dialog != null) {
            dialog.dismiss();
        }
    }
}
