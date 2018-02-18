package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.hardware.SensorEvent;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;

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
            gameActivity.setController(new WaitingForMoveState(gameActivity));
            gameActivity.toggleDiceAndButton();
            gameActivity.getCanvasView().invalidate();
            gameActivity.unregisterListener();
        } else {
            rollDice();
        }
    }
}
