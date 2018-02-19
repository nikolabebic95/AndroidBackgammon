package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.hardware.SensorEvent;
import android.media.MediaPlayer;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.R;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Persistence;

public class DiceRollingState extends ControllerState {
    private MediaPlayer mediaPlayer;

    public DiceRollingState(GameActivity gameActivity) {
        super(gameActivity);

        if (gameActivity.getSettings().isSoundOn()) {
            mediaPlayer = MediaPlayer.create(gameActivity, R.raw.dice);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    @Override
    public boolean usesAccelerometer() {
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (!gameActivity.isShake()) {
            if (gameActivity.getSettings().isSoundOn()) mediaPlayer.stop();
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
