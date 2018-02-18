package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.BoardFeatures;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.FieldGeometryUtility;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.players.Dice;

public class WaitingForMoveState extends ControllerState {
    private Dice dice;
    private ArrayList<Integer> played = new ArrayList<>();

    private static final double DIST_THRESHOLD = 100;

    public WaitingForMoveState(GameActivity gameActivity) {
        super(gameActivity);
        dice = gameActivity.getGameModel().getDice();
    }

    @Override
    public ArrayList<Integer> getSelectedFields() {
        return gameActivity.getGameModel().getGame().table().getAllIndicesWithPlayer(gameActivity.getGameModel().getCurrentPlayer());
    }

    @Override
    public void onPointerDown(float x, float y) {
        ArrayList<Integer> fields = getSelectedFields();

        Point target = new Point((int)x, (int)y);

        int width = gameActivity.getCanvasView().getWidth();
        int height = gameActivity.getCanvasView().getHeight();
        BoardFeatures boardFeatures = gameActivity.getSettings().getBoardFeatures();

        double minDist = Double.MAX_VALUE;
        int minField = -2;

        for (Integer field : fields) {
            Point point = FieldGeometryUtility.pointFromIndex(field);
            Rect rect = FieldGeometryUtility.rectFromPoint(point, width, height, boardFeatures);
            double dist = FieldGeometryUtility.distance(target, rect);
            if (dist < minDist && dist < DIST_THRESHOLD) {
                minDist = dist;
                minField = field;
            }
        }

        if (minField != -2) {
            // TODO: Remove checker from game
            gameActivity.setController(new CheckerDragState(gameActivity, this, minField, x, y));
        }
    }
}
