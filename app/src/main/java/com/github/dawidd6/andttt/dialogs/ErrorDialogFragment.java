package com.github.dawidd6.andttt.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.proto.Error;

public class ErrorDialogFragment extends BaseDialogFragment {
    private View.OnClickListener okButtonListener;
    private Integer text;
    private Error code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_error, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button okButton = view.findViewById(R.id.okButton);
        okButton.setOnClickListener(okButtonListener);

        TextView errorText = view.findViewById(R.id.errorText);
        TextView codeText = view.findViewById(R.id.codeText);

        if(code != null) {
            codeText.setText(code.toString());
            errorText.setText(getErrorStringRes(code));
        }

        if(text != null)
            errorText.setText(text);
    }

    public int getErrorStringRes(Error error) {
        switch (error) {
            case CLIENT_NAME_EMPTY:
                return R.string.error_client_name_empty;
            case CLIENT_NAME_TAKEN:
                return R.string.error_client_name_taken;
            case CLIENT_HAS_A_ROOM:
                return R.string.error_client_has_a_room;
            case CLIENT_HAS_NO_ROOM:
                return R.string.error_client_has_no_room;
            case CLIENT_NOT_FOUND_IN_ROOM:
                return R.string.error_client_not_found_in_room;
            case CLIENT_NOT_FOUND:
                return R.string.error_client_not_found;
            case CLIENT_HAS_NO_TURN:
                return R.string.error_client_has_no_turn;
            case CLIENT_HAS_NO_SYMBOL:
                return R.string.error_client_has_no_symbol;

            case ROOM_NAME_EMPTY:
                return R.string.error_room_name_empty;
            case ROOM_NAME_TAKEN:
                return R.string.error_room_name_taken;
            case ROOM_FULL:
                return R.string.error_room_full;
            case ROOM_NOT_FOUND:
                return R.string.error_room_not_found;
            case ROOM_NOT_EMPTY:
                return R.string.error_room_not_empty;

            case ENEMY_NOT_FOUND_BUT_SHOULD_BE:
                return R.string.error_enemy_not_found_but_should_be;
            case ENEMY_NOT_FOUND:
                return R.string.error_enemy_not_found;

            case POSITION_ALREADY_OCCUPIED:
                return R.string.error_position_already_occupied;
            case POSITION_OUT_OF_BOUND:
                return R.string.error_position_out_of_bound;
            case THERE_IS_A_WINNER:
                return R.string.error_there_is_a_winner;
        }

        return R.string.error_undefined;
    }

    public void setOnOkClickListener(View.OnClickListener listener) {
        this.okButtonListener = listener;
    }

    public void setText(Integer text) {
        this.text = text;
    }

    public void setErrorCode(Error code) {
        this.code = code;
    }
}
