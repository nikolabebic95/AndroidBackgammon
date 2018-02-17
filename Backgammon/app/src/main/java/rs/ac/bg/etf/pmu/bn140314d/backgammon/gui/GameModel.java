package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.Game;

public class GameModel {
    private Game game;

    public GameModel(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
