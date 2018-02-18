package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.RandomUtility;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.players.Dice;

public abstract class ControllerState {
    protected final GameActivity gameActivity;

    public ControllerState(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    public void onPointerDown(float x, float y) {}

    public void onPointerMove(float x, float y) {}

    public void onPointerUp(float x, float y) {}

    public void onSensorChanged(SensorEvent sensorEvent) {}

    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void onDiceRollClicked(View view) {}

    public boolean diceVisible() {
        return true;
    }

    public ArrayList<Integer> getSelectedFields() {
        return new ArrayList<>();
    }

    public boolean usesAccelerometer() {
        return false;
    }

    public Point getCurrentChecker() {
        return null;
    }

    protected void rollDice() {
        Dice dice = Dice.roll();
        gameActivity.getGameModel().setDice(dice);
        Random random = RandomUtility.getRandom();

        gameActivity.getGameModel().setDiceInverted(random.nextBoolean());
        gameActivity.updateDice();
    }
}
