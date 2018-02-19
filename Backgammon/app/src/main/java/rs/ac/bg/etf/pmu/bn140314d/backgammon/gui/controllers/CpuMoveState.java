package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameModel;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.Move;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.PlayerId;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Persistence;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.players.ExpectiMiniMaxBot;

public class CpuMoveState extends ControllerState {
    private ExpectiMiniMaxBot bot = new ExpectiMiniMaxBot();
    {
        bot.setDepth(4);
    }

    public CpuMoveState(GameActivity gameActivity) {
        super(gameActivity);

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Move> asyncTask = new AsyncTask<Void, Void, Move>() {
            @Override
            protected Move doInBackground(Void... voids) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    // Ignore
                }

                return bot.playMove(gameActivity.getGameModel().getGame(), gameActivity.getGameModel().getCurrentPlayer(), gameActivity.getGameModel().getDice());
            }

            @Override
            protected void onPostExecute(Move move) {
                GameModel gameModel = gameActivity.getGameModel();

                gameModel.getGame().tryPlayMove(move);

                gameModel.setCurrentPlayer(gameActivity.getGameModel().getCurrentPlayer().other());
                gameModel.setGameState(GameState.SHOULD_ROLL);
                gameActivity.setController(new WaitingForDiceRollState(gameActivity));
                gameActivity.toggleDiceAndButton();

                if (gameModel.getGame().checkWinner() != PlayerId.NONE) {
                    gameActivity.finishUp();
                } else {
                    Persistence.saveGameModel(gameActivity, gameActivity.getGameModel());
                    gameActivity.getCanvasView().invalidate();
                }
            }
        };

        asyncTask.execute();
    }
}
