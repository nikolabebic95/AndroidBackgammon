package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.graphics.Point;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;

public class CheckerDragState extends ControllerState {
    private WaitingForMoveState parent;
    private int startField;
    private float x;
    private float y;

    public CheckerDragState(GameActivity gameActivity, WaitingForMoveState parent, int startField, float x, float y) {
        super(gameActivity);
        this.parent = parent;
        this.startField = startField;
        this.x = x;
        this.y = y;
        gameActivity.getCanvasView().invalidate();
    }

    @Override
    public Point getCurrentChecker() {
        return new Point((int)x, (int)y);
    }

    @Override
    public void onPointerMove(float x, float y) {
        this.x = x;
        this.y = y;
        gameActivity.getCanvasView().invalidate();
    }

    @Override
    public void onPointerUp(float x, float y) {
        gameActivity.setController(parent);
        gameActivity.getCanvasView().invalidate();
    }
}
