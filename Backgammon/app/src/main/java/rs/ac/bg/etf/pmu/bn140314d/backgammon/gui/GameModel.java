package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.Game;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.PlayerId;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.players.Dice;

public class GameModel {
    private Game game;
    private Dice dice;
    private PlayerId currentPlayer;

    public GameModel(Game game) {
        this.game = game;
        currentPlayer = PlayerId.FIRST;
    }

    public Game getGame() {
        return game;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public PlayerId getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerId currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
