package com.github.dawidd6.andttt.fragments;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import com.github.dawidd6.andttt.ClientService;
import com.github.dawidd6.andttt.MainActivity;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.dialogs.ErrorDialogFragment;
import com.github.dawidd6.andttt.dialogs.LoadingDialogFragment;
import com.github.dawidd6.andttt.dialogs.YesNoDialogFragment;
import com.github.dawidd6.andttt.events.DisconnectEvent;
import com.github.dawidd6.andttt.events.SendEvent;
import com.github.dawidd6.andttt.game.Player;
import com.github.dawidd6.andttt.proto.*;
import com.github.dawidd6.andttt.proto.Error;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class OnlineFragment extends BaseGameFragment {
    public static final String TAG = "OnlineFragment";
    private YesNoDialogFragment yesNoDialogFragment;
    private LoadingDialogFragment loadingDialogFragment;
    private ErrorDialogFragment errorDialogFragment;
    private DialogFragment savedDialogFragment;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAllTilesClickable(false);

        loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setText(R.string.waiting_for_opponent);

        yesNoDialogFragment = new YesNoDialogFragment();
        errorDialogFragment = new ErrorDialogFragment();

        player1.setName(getArguments().getString("name"));
        player2.setName("");

        showConclusion(getString(R.string.waiting), Color.BLUE);
        restartButton.setClickable(false);
        restartButton.setAlpha(0.5f);

        Request request = Request.newBuilder()
                .setStarterPack(StarterPackRequest.newBuilder())
                .build();
        EventBus.getDefault().post(new SendEvent(request));
    }

    @Override
    public void onStart() {
        super.onStart();

        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        if(isRemoving())
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(loadingDialogFragment != null && loadingDialogFragment.isResumed())
            loadingDialogFragment.dismiss();

        if(savedDialogFragment != null) {
            savedDialogFragment.show(getFragmentManager(), null);
            savedDialogFragment = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Request request = Request.newBuilder()
                .setLeaveRoom(LeaveRoomRequest.newBuilder())
                .build();
        EventBus.getDefault().post(new SendEvent(request));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDisconnect(DisconnectEvent event) {
        if(isRemoving())
            return;

        errorDialogFragment.setText(R.string.disconnected);
        errorDialogFragment.setOnOkClickListener((v) -> {
            errorDialogFragment.dismiss();
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            android.R.animator.fade_in,
                            android.R.animator.fade_out,
                            android.R.animator.fade_in,
                            android.R.animator.fade_out)
                    .replace(R.id.placeholder, new MenuFragment(), MenuFragment.TAG)
                    .commit();
        });

        if(isResumed()) {
            errorDialogFragment.show(getFragmentManager(), null);
        } else {
            savedDialogFragment = errorDialogFragment;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onError(Error error) {
        errorDialogFragment.setErrorCode(error);
        errorDialogFragment.setOnOkClickListener((v) -> {
            errorDialogFragment.dismiss();
        });

        if(isResumed()) {
            errorDialogFragment.show(getFragmentManager(), null);
        } else {
            savedDialogFragment = errorDialogFragment;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStarterPack(StarterPackResponse response) {
        player1.setSymbol(response.getMySymbol());
        player1.setTurn(response.getMyTurn());

        player2.setName(response.getEnemyName());
        player2.setSymbol(response.getEnemySymbol());
        player2.setTurn(response.getEnemyTurn());

        restartGame();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMove(MoveResponse response) {
        makeMove(player1, player2, response.getPosition());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnemyMoved(EnemyMovedResponse response) {
        makeMove(player2, player1, response.getPosition());
        if(game.isPlaying())
            hideConclusion();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnemyLeft(EnemyLeftResponse response) {
        yesNoDialogFragment.setText(R.string.enemy_left);
        yesNoDialogFragment.setOnYesClickListener((v) -> {
            yesNoDialogFragment.dismiss();

            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        });
        yesNoDialogFragment.setOnNoClickListener((v) -> {
            yesNoDialogFragment.dismiss();

            getActivity().onBackPressed();
        });

        if(isResumed()) {
            yesNoDialogFragment.show(getFragmentManager(), null);
        } else {
            savedDialogFragment = yesNoDialogFragment;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnemyDisconnected(EnemyDisconnectedResponse response) {
        yesNoDialogFragment.setText(R.string.enemy_left);
        yesNoDialogFragment.setOnYesClickListener((v) -> {
            yesNoDialogFragment.dismiss();

            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        });
        yesNoDialogFragment.setOnNoClickListener((v) -> {
            yesNoDialogFragment.dismiss();

            getActivity().onBackPressed();
        });

        if(isResumed()) {
            yesNoDialogFragment.show(getFragmentManager(), null);
        } else {
            savedDialogFragment = yesNoDialogFragment;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRestart(RestartResponse response) {
        switch (response.getRestart()) {
            case REQUESTED:
                yesNoDialogFragment.setText(R.string.question_restart);
                yesNoDialogFragment.setOnYesClickListener((v) -> {
                    yesNoDialogFragment.dismiss();

                    Request request = Request.newBuilder()
                            .setRestart(RestartRequest.newBuilder()
                                    .setRestart(Restart.APPROVED))
                            .build();
                    EventBus.getDefault().post(new SendEvent(request));


                    Request request1 = Request.newBuilder()
                            .setStarterPack(StarterPackRequest.newBuilder())
                            .build();
                    EventBus.getDefault().post(new SendEvent(request1));

                });
                yesNoDialogFragment.setOnNoClickListener((v) -> {
                    yesNoDialogFragment.dismiss();

                    Request request = Request.newBuilder()
                            .setRestart(RestartRequest.newBuilder()
                                    .setRestart(Restart.DENIED))
                            .build();
                    EventBus.getDefault().post(new SendEvent(request));

                });

                if(isResumed()) {
                    yesNoDialogFragment.show(getFragmentManager(), null);
                } else {
                    savedDialogFragment = yesNoDialogFragment;
                }
                break;
            case APPROVED:
                if(isResumed())
                    loadingDialogFragment.dismiss();
                Request request = Request.newBuilder()
                        .setStarterPack(StarterPackRequest.newBuilder())
                        .build();
                EventBus.getDefault().post(new SendEvent(request));
                break;
            case DENIED:
                errorDialogFragment.setOnOkClickListener((v) -> {
                    errorDialogFragment.dismiss();
                });
                errorDialogFragment.setText(R.string.denied_restart);

                if(isResumed()) {
                    loadingDialogFragment.dismiss();
                    errorDialogFragment.show(getFragmentManager(), null);
                } else {
                    savedDialogFragment = errorDialogFragment;
                }
                break;
        }
    }

    @Override
    public void randomize() {}

    @Override
    public void restartGame() {
        super.restartGame();
        
        setAllTilesClickable(player1.isTurn());
        restartButton.setClickable(false);
        restartButton.setAlpha(0.5f);

        if(player1.isTurn())
            hideConclusion();
        else
            showConclusion(getString(R.string.waiting), Color.BLUE);
    }

    @Override
    protected void makeMove(Player playerWithTurn, Player playerWithoutTurn, int i) {
        super.makeMove(playerWithTurn, playerWithoutTurn, i);

        if(game.isPlaying()) {
            setAllTilesClickable(player1.isTurn());

            if (player2.isTurn())
                showConclusion(getString(R.string.waiting), Color.BLUE);
        } else {
            restartButton.setClickable(true);
            restartButton.setAlpha(1.0f);
        }
    }

    @Override
    public void onClickTile(View view) {
        setAllTilesClickable(false);

        int i = Character.getNumericValue(getResources().getResourceEntryName(view.getId()).charAt(1));
        Request request = Request.newBuilder()
                .setMove(MoveRequest.newBuilder()
                        .setPosition(i))
                .build();
        EventBus.getDefault().post(new SendEvent(request));
    }

    @Override
    public void onClickRestart(View view) {
        loadingDialogFragment.show(getFragmentManager(), null);

        Request request = Request.newBuilder()
                .setRestart(RestartRequest.newBuilder()
                        .setRestart(Restart.REQUESTED))
                .build();
        EventBus.getDefault().post(new SendEvent(request));
    }
}