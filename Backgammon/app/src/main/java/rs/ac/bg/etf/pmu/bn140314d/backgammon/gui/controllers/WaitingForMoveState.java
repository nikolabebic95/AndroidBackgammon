package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameModel;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.BoardFeatures;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.GeometryUtility;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.ITable;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.PlayerId;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.players.Dice;

public class WaitingForMoveState extends ControllerState {
    private Dice dice;

    private static final double DIST_THRESHOLD = 100;

    public WaitingForMoveState(GameActivity gameActivity) {
        super(gameActivity);
        dice = gameActivity.getGameModel().getDice();
    }

    @Override
    public ArrayList<Integer> getSelectedFields() {
        GameModel gameModel = gameActivity.getGameModel();
        return gameModel.getGame().calculateAllStartingIndices(gameModel.getCurrentPlayer(), dice, gameModel.getPlayed());
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
            Rect rect;
            if (field == -1) {
                rect = GeometryUtility.barRectFromPlayerId(gameActivity.getGameModel().getCurrentPlayer(), width, height, boardFeatures);
            } else {
                Point point = GeometryUtility.pointFromIndex(field);
                rect = GeometryUtility.rectFromPoint(point, width, height, boardFeatures);
            }

            double dist = GeometryUtility.distance(target, rect);
            if (dist < minDist && dist < DIST_THRESHOLD) {
                minDist = dist;
                minField = field;
            }
        }

        if (minField != -2) {
            ITable table = gameActivity.getGameModel().getGame().table();
            PlayerId playerId = gameActivity.getGameModel().getCurrentPlayer();

            if (minField == -1) {
                if (playerId == PlayerId.FIRST) {
                    table.setPlayerOneBar(table.getPlayerOneBar() - 1);
                } else if (playerId == PlayerId.SECOND) {
                    table.setPlayerTwoBar(table.getPlayerTwoBar() - 1);
                }
            } else {
                table.getField(minField).decreaseNumberOfChips(gameActivity.getGameModel().getCurrentPlayer());
            }

            gameActivity.setController(new CheckerDragState(gameActivity, this, minField, x, y));
        }
    }

    Dice getDice() {
        return dice;
    }
}
