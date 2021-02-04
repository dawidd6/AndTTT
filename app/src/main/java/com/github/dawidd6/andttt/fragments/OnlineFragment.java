package com.github.dawidd6.andttt.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.navigation.Navigation;

import com.github.dawidd6.andttt.R;
import com.github.dawidd6.andttt.events.SendEvent;
import com.github.dawidd6.andttt.game.Player;
import com.github.dawidd6.andttt.proto.EnemyDisconnectedResponse;
import com.github.dawidd6.andttt.proto.EnemyLeftResponse;
import com.github.dawidd6.andttt.proto.EnemyMovedResponse;
import com.github.dawidd6.andttt.proto.LeaveRoomRequest;
import com.github.dawidd6.andttt.proto.MoveRequest;
import com.github.dawidd6.andttt.proto.MoveResponse;
import com.github.dawidd6.andttt.proto.Request;
import com.github.dawidd6.andttt.proto.Restart;
import com.github.dawidd6.andttt.proto.RestartRequest;
import com.github.dawidd6.andttt.proto.RestartResponse;
import com.github.dawidd6.andttt.proto.StarterPackRequest;
import com.github.dawidd6.andttt.proto.StarterPackResponse;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.github.dawidd6.andttt.activities.OnlineActivity.bus;
import static com.github.dawidd6.andttt.activities.OnlineActivity.dialogManager;
import static com.github.dawidd6.andttt.activities.OnlineActivity.name;

public class OnlineFragment extends BaseGameFragment {
    public static final String TAG = "OnlineFragment";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAllTilesClickable(false);

        player1.setName(name);
        player2.setName("");

        showConclusion(getString(R.string.waiting), Color.BLUE);
        setRestartButtonClickable(false);

        Request request = Request.newBuilder()
                .setStarterPack(StarterPackRequest.newBuilder())
                .build();
        bus.post(new SendEvent(request));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bus.register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bus.unregister(this);

        Request request = Request.newBuilder()
                .setLeaveRoom(LeaveRoomRequest.newBuilder())
                .build();
        bus.post(new SendEvent(request));
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

    public void C_O_W_A_R_D_S() {
        dialogManager.showYesNo(getActivity(), R.string.enemy_left, ((dialog, which) -> {
            dialog.dismiss();

            getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }), ((dialog, which) -> {
            dialog.dismiss();

            Navigation.findNavController(requireActivity(), R.id.navigation_host_online).navigateUp();
        }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnemyLeft(EnemyLeftResponse response) {
        C_O_W_A_R_D_S();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnemyDisconnected(EnemyDisconnectedResponse response) {
        C_O_W_A_R_D_S();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRestart(RestartResponse response) {
        switch (response.getRestart()) {
            case REQUESTED:
                dialogManager.showYesNo(getActivity(), R.string.question_restart, ((dialog, which) -> {
                    dialog.dismiss();

                    Request request = Request.newBuilder()
                            .setRestart(RestartRequest.newBuilder()
                                    .setRestart(Restart.APPROVED))
                            .build();
                    bus.post(new SendEvent(request));


                    Request request1 = Request.newBuilder()
                            .setStarterPack(StarterPackRequest.newBuilder())
                            .build();
                    bus.post(new SendEvent(request1));
                }), ((dialog, which) -> {
                    dialog.dismiss();

                    Request request = Request.newBuilder()
                            .setRestart(RestartRequest.newBuilder()
                                    .setRestart(Restart.DENIED))
                            .build();
                    bus.post(new SendEvent(request));
                }));
                break;
            case APPROVED:
                dialogManager.dismiss();
                Request request = Request.newBuilder()
                        .setStarterPack(StarterPackRequest.newBuilder())
                        .build();
                bus.post(new SendEvent(request));
                break;
            case DENIED:
                dialogManager.showError(getActivity(), R.string.denied_restart, ((dialog, which) -> {
                    dialog.dismiss();
                }));
                break;
        }
    }

    @Override
    public void randomize() {}

    @Override
    public void restartGame() {
        super.restartGame();
        
        setAllTilesClickable(player1.isTurn());

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
        bus.post(new SendEvent(request));
    }

    @Override
    public void onClickRestart(View view) {
        dialogManager.showLoading(getActivity(), R.string.waiting_for_opponent);

        Request request = Request.newBuilder()
                .setRestart(RestartRequest.newBuilder()
                        .setRestart(Restart.REQUESTED))
                .build();
        bus.post(new SendEvent(request));
    }
}