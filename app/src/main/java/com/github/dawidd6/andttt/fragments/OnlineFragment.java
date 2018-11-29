package com.github.dawidd6.andttt.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.dialogs.ErrorDialogFragment;
import com.github.dawidd6.andttt.dialogs.LoadingDialogFragment;
import com.github.dawidd6.andttt.dialogs.YesNoDialogFragment;
import com.github.dawidd6.andttt.game.Player;
import com.github.dawidd6.andttt.proto.Error;
import com.github.dawidd6.andttt.proto.*;

import static com.github.dawidd6.andttt.OnlineActivity.client;
import static com.github.dawidd6.andttt.OnlineActivity.name;

public class OnlineFragment extends BaseGameFragment {
    private ErrorDialogFragment errorDialogFragment;
    private YesNoDialogFragment yesNoDialogFragment;
    private LoadingDialogFragment loadingDialogFragment;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAllTilesClickable(false);

        loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setText(R.string.waiting_for_opponent);
        yesNoDialogFragment = new YesNoDialogFragment();
        errorDialogFragment = new ErrorDialogFragment();
        errorDialogFragment.setOnOkClickListener((v) -> {
            errorDialogFragment.dismiss();
        });

        player1.setName(name);
        player2.setName("");

        showConclusion(getString(R.string.waiting), Color.BLUE);
        restartButton.setClickable(false);
        restartButton.setAlpha(0.5f);

        client.setOnResponseListener(this::dispatch);

        Request request = Request.newBuilder()
                .setStarterPack(StarterPackRequest.newBuilder())
                .build();
        client.send(request);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Request request = Request.newBuilder()
                .setLeaveRoom(LeaveRoomRequest.newBuilder())
                .build();
        client.send(request);
    }

    private void dispatch(Response response) {
        if(response.getError() != Error.NONE) {
            errorDialogFragment.setText(null);
            errorDialogFragment.setErrorCode(response.getError());
            errorDialogFragment.show(getFragmentManager(), null);
            return;
        }
        
        switch (response.getTypeCase()) {
            case STARTER_PACK:
                StarterPackResponse starter = response.getStarterPack();
                getActivity().runOnUiThread(() -> {
                    player1.setSymbol(starter.getMySymbol());
                    player1.setTurn(starter.getMyTurn());

                    player2.setName(starter.getEnemyName());
                    player2.setSymbol(starter.getEnemySymbol());
                    player2.setTurn(starter.getEnemyTurn());

                    restartGame();
                });
                break;
            case MOVE:
                getActivity().runOnUiThread(() -> {
                    makeMove(player1, player2, response.getMove().getPosition());
                });
                break;
            case ENEMY_MOVED:
                getActivity().runOnUiThread(() -> {
                    makeMove(player2, player1, response.getEnemyMoved().getPosition());
                    if(game.isPlaying())
                        hideConclusion();
                });
                break;
            case ENEMY_LEFT:
                case ENEMY_DISCONNECTED:
                    yesNoDialogFragment.setText(R.string.enemy_left);
                    yesNoDialogFragment.setOnYesClickListener((v) -> {
                        yesNoDialogFragment.dismiss();
                    });
                    yesNoDialogFragment.setOnNoClickListener((v) -> {
                        yesNoDialogFragment.dismiss();

                        getActivity().runOnUiThread(() -> getActivity().onBackPressed());
                    });
                    yesNoDialogFragment.show(getFragmentManager(),null);
                    break;
            case RESTART:
                switch (response.getRestart().getRestart()) {
                    case REQUESTED:
                        yesNoDialogFragment.setText(R.string.question_restart);
                        yesNoDialogFragment.setOnYesClickListener((v) -> {
                            yesNoDialogFragment.dismiss();

                            Request request = Request.newBuilder()
                                    .setRestart(RestartRequest.newBuilder()
                                            .setRestart(Restart.APPROVED))
                                    .build();
                            client.send(request);

                            Request request1 = Request.newBuilder()
                                    .setStarterPack(StarterPackRequest.newBuilder()).build();
                            client.send(request1);
                        });
                        yesNoDialogFragment.setOnNoClickListener((v) -> {
                            yesNoDialogFragment.dismiss();

                            Request request = Request.newBuilder()
                                    .setRestart(RestartRequest.newBuilder()
                                            .setRestart(Restart.DENIED))
                                    .build();
                            client.send(request);
                        });
                        yesNoDialogFragment.show(getFragmentManager(),null);
                        break;
                    case APPROVED:
                        loadingDialogFragment.dismiss();
                        Request request = Request.newBuilder()
                                .setStarterPack(StarterPackRequest.newBuilder()).build();
                        client.send(request);
                        break;
                    case DENIED:
                        loadingDialogFragment.dismiss();
                        errorDialogFragment.setText(R.string.denied_restart);
                        errorDialogFragment.show(getFragmentManager(), null);
                        break;
                    default:
                        break;
                }
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
        client.send(request);
    }

    @Override
    public void onClickRestart(View view) {
        loadingDialogFragment.show(getFragmentManager(), null);

        Request request = Request.newBuilder()
                .setRestart(RestartRequest.newBuilder()
                        .setRestart(Restart.REQUESTED))
                .build();
        client.send(request);
    }
}