package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.hardware.SensorEvent;
import android.view.View;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Persistence;

public class WaitingForDiceRollState extends ControllerState {
    private int shakeCounter = 0;

    public WaitingForDiceRollState(GameActivity gameActivity) {
        super(gameActivity);
    }

    @Override
    public boolean diceVisible() {
        return false;
    }

    @Override
    public void onDiceRollClicked(View view) {
        rollDice();

        gameActivity.getGameModel().setGameState(GameState.SHOULD_MOVE);
        if (gameActivity.isCurrentPlayerCpu()) gameActivity.setController(new CpuMoveState(gameActivity));
        else gameActivity.setController(new WaitingForMoveState(gameActivity));
        gameActivity.toggleDiceAndButton();
        gameActivity.getCanvasView().invalidate();
        gameActivity.unregisterListener();
        Persistence.saveGameModel(gameActivity, gameActivity.getGameModel());
    }

    @Override
    public boolean usesAccelerometer() {
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        boolean shake = gameActivity.isShake();
        if (shake) shakeCounter++;
        else shakeCounter = 0;

        if (shakeCounter > 2) {
            gameActivity.setController(new DiceRollingState(gameActivity));
            gameActivity.toggleDiceAndButton();
        }
    }
}
