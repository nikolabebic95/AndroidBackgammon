package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import java.util.ArrayList;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;

public class WaitingForMoveState extends ControllerState {
    public WaitingForMoveState(GameActivity gameActivity) {
        super(gameActivity);
    }

    @Override
    public ArrayList<Integer> getSelectedFields() {
        return gameActivity.getGameModel().getGame().table().getAllIndicesWithPlayer(gameActivity.getGameModel().getCurrentPlayer());
    }
}
