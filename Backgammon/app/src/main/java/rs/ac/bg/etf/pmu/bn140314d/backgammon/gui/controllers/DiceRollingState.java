package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.hardware.SensorEvent;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Persistence;

public class DiceRollingState extends ControllerState {
    public DiceRollingState(GameActivity gameActivity) {
        super(gameActivity);
    }

    @Override
    public boolean usesAccelerometer() {
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (!gameActivity.isShake()) {
            rollDice();

            gameActivity.getGameModel().setGameState(GameState.SHOULD_MOVE);
            if (gameActivity.isCurrentPlayerCpu()) gameActivity.setController(new CpuMoveState(gameActivity));
            else gameActivity.setController(new WaitingForMoveState(gameActivity));
            gameActivity.toggleDiceAndButton();
            gameActivity.getCanvasView().invalidate();
            gameActivity.unregisterListener();
            Persistence.saveGameModel(gameActivity, gameActivity.getGameModel());
        } else {
            rollDice();
        }
    }
}
