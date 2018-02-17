package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.view.MotionEvent;
import android.view.View;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;

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
}
