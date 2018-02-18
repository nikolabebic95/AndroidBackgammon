package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameModel;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.BoardFeatures;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.FieldGeometryUtility;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.IField;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.ITable;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.PlayerId;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Persistence;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.players.Dice;

public class CheckerDragState extends ControllerState {
    private WaitingForMoveState parent;
    private int startField;
    private float x;
    private float y;

    private final ArrayList<Integer> possibleMoves = new ArrayList<>();

    private static final double DIST_THRESHOLD = 500;

    public CheckerDragState(GameActivity gameActivity, WaitingForMoveState parent, int startField, float x, float y) {
        super(gameActivity);
        this.parent = parent;
        this.startField = startField;
        this.x = x;
        this.y = y;
        initPossibleMoves();
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
        int field = calculateCurrentMove();
        GameModel gameModel = gameActivity.getGameModel();

        gameModel.getGame().table().getField(field).increaseNumberOfChips(gameActivity.getGameModel().getCurrentPlayer());

        if (field != startField) {
            gameModel.play(Math.abs(field - startField));
        }

        if (checkIfShouldPlayMore()) gameActivity.setController(parent);
        else {
            gameModel.getPlayed().clear();
            gameModel.setCurrentPlayer(gameActivity.getGameModel().getCurrentPlayer().other());
            gameModel.setGameState(GameState.SHOULD_ROLL);
            gameActivity.setController(new WaitingForDiceRollState(gameActivity));
            gameActivity.toggleDiceAndButton();
            Persistence.saveGameModel(gameActivity, gameActivity.getGameModel());
        }

        gameActivity.getCanvasView().invalidate();
    }

    @Override
    public ArrayList<Integer> getSelectedFields() {
        ArrayList<Integer> ret = new ArrayList<>();
        ret.add(calculateCurrentMove());
        return ret;
    }

    private void initPossibleMoves() {
        possibleMoves.add(startField);
        PlayerId playerId = gameActivity.getGameModel().getCurrentPlayer();

        Dice dice = parent.getDice();
        ArrayList<Integer> played = gameActivity.getGameModel().getPlayed();

        ArrayList<Integer> remaining = computeRemaining(dice, played);

        ITable table = gameActivity.getGameModel().getGame().table();

        remaining.forEach(move -> {
            if (playerId == PlayerId.FIRST && isValidMove(table.getField(startField + move), playerId)) {
                possibleMoves.add(startField + move);
            } else if (playerId == PlayerId.SECOND && isValidMove(table.getField(startField - move), playerId)) {
                possibleMoves.add(startField - move);
            }
        });
    }

    private boolean checkIfShouldPlayMore() {
        Dice dice = parent.getDice();
        ArrayList<Integer> played = gameActivity.getGameModel().getPlayed();

        if (dice.getSmallerDie() == dice.getGreaterOrEqualDie()) {
            return played.size() < 4;
        } else {
            return played.size() < 2;
        }
    }

    private int calculateCurrentMove() {
        Point target = new Point((int)x, (int)y);

        int width = gameActivity.getCanvasView().getWidth();
        int height = gameActivity.getCanvasView().getHeight();
        BoardFeatures boardFeatures = gameActivity.getSettings().getBoardFeatures();


        double minDist = Double.MAX_VALUE;
        int minField = startField;

        for (Integer field : possibleMoves) {
            Point point = FieldGeometryUtility.pointFromIndex(field);
            Rect rect = FieldGeometryUtility.rectFromPoint(point, width, height, boardFeatures);
            double dist = FieldGeometryUtility.distance(target, rect);
            if (dist < minDist && dist < DIST_THRESHOLD) {
                minDist = dist;
                minField = field;
            }
        }

        return minField;
    }

    private static ArrayList<Integer> computeRemaining(Dice dice, ArrayList<Integer> played) {
        ArrayList<Integer> ret = new ArrayList<>();

        if (dice.getSmallerDie() == dice.getGreaterOrEqualDie()) {
            ret.add(dice.getSmallerDie());
        } else {
            if (!played.contains(dice.getSmallerDie())) ret.add(dice.getSmallerDie());
            if (!played.contains(dice.getGreaterOrEqualDie())) ret.add(dice.getGreaterOrEqualDie());
        }

        return ret;
    }

    private static boolean isValidMove(IField field, PlayerId playerId) {
        return field.getNumberOfChips() < 2 || field.getPlayerId() == playerId;
    }
}
