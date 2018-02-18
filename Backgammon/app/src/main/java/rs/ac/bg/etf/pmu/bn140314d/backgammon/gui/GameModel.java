package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui;

import java.io.Serializable;
import java.util.ArrayList;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.Game;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.PlayerId;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.players.Dice;

public class GameModel implements Serializable {
    private Game game;
    private Dice dice;
    private PlayerId currentPlayer;
    private GameState gameState;
    private ArrayList<Integer> played = new ArrayList<>();

    public GameModel(Game game) {
        this.game = game;
        currentPlayer = PlayerId.FIRST;
        gameState = GameState.SHOULD_ROLL;
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

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public ArrayList<Integer> getPlayed() {
        return played;
    }

    public void play(int move) {
        played.add(move);
    }
}
