package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.R;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.DiceImagesHelper;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.RandomUtility;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.players.Dice;

public abstract class ControllerState {
    protected final GameActivity gameActivity;

    public ControllerState(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    public boolean onTouchListener(View view, MotionEvent motionEvent) {
        return false;
    }

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

    protected void rollDice() {
        Dice dice = Dice.roll();
        gameActivity.getGameModel().setDice(dice);
        Random random = RandomUtility.getRandom();

        ImageView dice1 = gameActivity.findViewById(R.id.dice1);
        ImageView dice2 = gameActivity.findViewById(R.id.dice2);

        if (random.nextBoolean()) {
            dice1.setImageResource(DiceImagesHelper.getDiceResource(dice.getSmallerDie()));
            dice2.setImageResource(DiceImagesHelper.getDiceResource(dice.getGreaterOrEqualDie()));
        } else {
            dice1.setImageResource(DiceImagesHelper.getDiceResource(dice.getGreaterOrEqualDie()));
            dice2.setImageResource(DiceImagesHelper.getDiceResource(dice.getSmallerDie()));
        }
    }
}
