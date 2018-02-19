package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameModel;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.BoardFeatures;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.GeometryUtility;
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
    private long startMillis;
    private boolean wasInside;
    private Point start;

    private final ArrayList<Integer> possibleMoves = new ArrayList<>();

    private static final double DIST_THRESHOLD = 500;
    private static final long MILLIS_THRESHOLD = 1000;
    private static final double INSIDE_THRESHOLD = 200;

    public CheckerDragState(GameActivity gameActivity, WaitingForMoveState parent, int startField, float x, float y) {
        super(gameActivity);
        this.parent = parent;
        this.startField = startField;
        this.x = x;
        this.y = y;
        start = new Point((int)x, (int)y);
        startMillis = System.currentTimeMillis();
        wasInside = true;
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

        GameModel gameModel = gameActivity.getGameModel();
        ITable table = gameModel.getGame().table();
        PlayerId playerId = gameModel.getCurrentPlayer();

        if (table.canBearOff(playerId) && wasInside) {
            if (System.currentTimeMillis() - startMillis > MILLIS_THRESHOLD) {
                ArrayList<Integer> remaining = computeRemaining(gameModel.getDice(), gameModel.getPlayed());

                for (Integer item : remaining) {
                    if (playerId == PlayerId.FIRST && startField + item >= ITable.NUMBER_OF_FIELDS || playerId == PlayerId.SECOND && startField - item < 0) {
                        table.bearOff(playerId);
                        gameActivity.updateScore();
                        gameModel.play(item);

                        if (checkIfShouldPlayMore()) gameActivity.setController(parent);
                        else {
                            gameModel.getPlayed().clear();
                            gameModel.setCurrentPlayer(gameActivity.getGameModel().getCurrentPlayer().other());
                            gameModel.setGameState(GameState.SHOULD_ROLL);
                            gameActivity.setController(new WaitingForDiceRollState(gameActivity));
                            gameActivity.toggleDiceAndButton();
                        }

                        Persistence.saveGameModel(gameActivity, gameActivity.getGameModel());
                        gameActivity.getCanvasView().invalidate();
                        break;
                    }
                }
            } else {
                double dist = GeometryUtility.distance(new Point((int)x, (int)y), start);
                if (dist > INSIDE_THRESHOLD) wasInside = false;
            }
        }
    }

    @Override
    public void onPointerUp(float x, float y) {
        int field = calculateCurrentMove();
        GameModel gameModel = gameActivity.getGameModel();
        PlayerId playerId = gameModel.getCurrentPlayer();
        ITable table = gameModel.getGame().table();

        if (field != -1) {
            if (table.getField(field).getPlayerId() == playerId.other()) {
                if (playerId == PlayerId.FIRST) {
                    table.setPlayerTwoBar(table.getPlayerTwoBar() + 1);
                } else if (playerId == PlayerId.SECOND) {
                    table.setPlayerOneBar(table.getPlayerOneBar() + 1);
                }

                table.getField(field).decreaseNumberOfChips(playerId.other());
            }

            table.getField(field).increaseNumberOfChips(playerId);
        }
        else {
            if (playerId == PlayerId.FIRST) {
                table.setPlayerOneBar(table.getPlayerOneBar() + 1);
            } else if (playerId == PlayerId.SECOND) {
                table.setPlayerTwoBar(table.getPlayerTwoBar() + 1);
            }
        }

        if (field != startField) {
            if (startField == -1) {
                if (playerId == PlayerId.FIRST) {
                    gameModel.play(field - 1);
                } else if (playerId == PlayerId.SECOND) {
                    gameModel.play(ITable.NUMBER_OF_FIELDS - field);
                }
            } else {
                gameModel.play(Math.abs(field - startField));
            }
        }

        if (checkIfShouldPlayMore()) gameActivity.setController(parent);
        else {
            gameModel.getPlayed().clear();
            gameModel.setCurrentPlayer(gameActivity.getGameModel().getCurrentPlayer().other());
            gameModel.setGameState(GameState.SHOULD_ROLL);
            gameActivity.setController(new WaitingForDiceRollState(gameActivity));
            gameActivity.toggleDiceAndButton();
        }

        Persistence.saveGameModel(gameActivity, gameActivity.getGameModel());
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
            if (startField == -1) {
                if (playerId == PlayerId.FIRST && isValidMove(table.getField(move - 1), playerId)) {
                    possibleMoves.add(move - 1);
                } else if (playerId == PlayerId.SECOND && isValidMove(table.getField(ITable.NUMBER_OF_FIELDS - move), playerId)) {
                    possibleMoves.add(ITable.NUMBER_OF_FIELDS - move);
                }
            } else if (playerId == PlayerId.FIRST && startField + move < ITable.NUMBER_OF_FIELDS && isValidMove(table.getField(startField + move), playerId)) {
                possibleMoves.add(startField + move);
            } else if (playerId == PlayerId.SECOND && startField - move >= 0 && isValidMove(table.getField(startField - move), playerId)) {
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
