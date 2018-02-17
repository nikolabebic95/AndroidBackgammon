package rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence;

import java.io.Serializable;
import java.util.ArrayList;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.R;

public class Settings implements Serializable {
    private static final ArrayList<Integer> boards = new ArrayList<>();
    private static final ArrayList<Integer> player1Checkers = new ArrayList<>();
    private static final ArrayList<Integer> player2Checkers = new ArrayList<>();
    static {
        boards.add(R.drawable.game_board_red_white_icon);
        boards.add(R.drawable.game_board_brown_yellow_icon);

        player1Checkers.add(R.drawable.yoda);
        player2Checkers.add(R.drawable.stormtrooper);

        player1Checkers.add(R.drawable.fire);
        player2Checkers.add(R.drawable.water);
    }

    private static final String defaultPlayer1Name = "Player 1";
    private static final String defaultPlayer2Name = "Player 2";
    private static final boolean defaultPlayer1Bot = false;
    private static final boolean defaultPlayer2Bot = false;
    private static final int defaultBoardIndex = 0;
    private static final int defaultCheckerIndex = 0;
    private static final boolean defaultSoundOn = true;

    private String player1Name;
    private String player2Name;
    private boolean player1Bot;
    private boolean player2Bot;
    private int boardIndex;
    private int checkerIndex;
    private boolean soundOn;

    public Settings() {
        setDefault();
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public boolean isPlayer1Bot() {
        return player1Bot;
    }

    public void setPlayer1Bot(boolean player1Bot) {
        this.player1Bot = player1Bot;
    }

    public boolean isPlayer2Bot() {
        return player2Bot;
    }

    public void setPlayer2Bot(boolean player2Bot) {
        this.player2Bot = player2Bot;
    }

    public int getBoard() {
        return boards.get(boardIndex);
    }

    public void setBoard(int boardIndex) {
        this.boardIndex = boardIndex;
    }

    public int getPlayer1Checker() {
        return player1Checkers.get(checkerIndex);
    }

    public int getPlayer2Checker() {
        return player2Checkers.get(checkerIndex);
    }

    public void setCheckerIndex(int checkerIndex) {
        this.checkerIndex = checkerIndex;
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
    }

    public void nextBoard() {
        boardIndex = (boardIndex + 1) % boards.size();
    }

    public void prevBoard() {
        boardIndex = (boardIndex + boards.size() - 1) % boards.size();
    }

    public void nextCheckers() {
        checkerIndex = (checkerIndex + 1) % player1Checkers.size();
    }

    public void prevCheckers() {
        checkerIndex = (checkerIndex + player1Checkers.size() - 1) % player1Checkers.size();
    }

    public void setDefault() {
        player1Name = defaultPlayer1Name;
        player2Name = defaultPlayer1Name;
        player1Bot = defaultPlayer1Bot;
        player2Bot = defaultPlayer2Bot;
        boardIndex = defaultBoardIndex;
        checkerIndex = defaultCheckerIndex;
        soundOn = defaultSoundOn;
    }
}
