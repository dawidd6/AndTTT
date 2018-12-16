package com.github.dawidd6.andttt;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import com.github.dawidd6.andttt.fragments.MenuFragment;
import com.github.dawidd6.andttt.proto.Error;

public class MainActivity extends Activity {
    public static boolean isNightModeEnabled;
    public static boolean isAnimationEnabled;
    public static boolean isStatusBarEnabled;
    public static boolean isMaximizationEnabled;
    public static int animation_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        isStatusBarEnabled = prefs.getBoolean("show_status_bar", false);
        isNightModeEnabled = prefs.getBoolean("night_mode", false);
        isAnimationEnabled = prefs.getBoolean("animations", true);
        isMaximizationEnabled = prefs.getBoolean("maximization", true);

        if(isAnimationEnabled)
            animation_duration = getResources().getInteger(R.integer.animation_duration);
        else
            animation_duration = 0;

        if(isStatusBarEnabled)
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        else
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setTheme(isNightModeEnabled ? R.style.theme_dark : R.style.theme_light);
        setContentView(R.layout.activity);

        if(savedInstanceState != null)
            return;

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out,
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                .replace(R.id.placeholder, new MenuFragment(), MenuFragment.TAG)
                .commit();
    }

    public static int getErrorStringRes(Error error) {
        switch (error) {
            case CLIENT_NAME_EMPTY:
                return R.string.error_client_name_empty;
            case CLIENT_NAME_TAKEN:
                return R.string.error_client_name_taken;
            case CLIENT_NAME_TOO_LONG:
                return R.string.error_client_name_too_long;
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
            case ROOM_NAME_TOO_LONG:
                return R.string.error_room_name_too_long;
            case ROOM_FULL:
                return R.string.error_room_full;
            case ROOM_NOT_FOUND:
                return R.string.error_room_not_found;
            case ROOM_NOT_EMPTY:
                return R.string.error_room_not_empty;
            case ROOM_PASSWORD_MISMATCH:
                return R.string.error_room_password_mismatch;
            case ROOM_PASSWORD_TOO_LONG:
                return R.string.error_room_password_too_long;
            case ROOM_PASSWORD_NOT_FOUND:
                return R.string.error_room_password_not_found;

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

}
