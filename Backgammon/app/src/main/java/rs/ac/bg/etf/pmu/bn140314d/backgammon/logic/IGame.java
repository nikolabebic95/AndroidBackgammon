package rs.ac.bg.etf.pmu.bn140314d.backgammon.logic;


import java.util.ArrayList;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.players.Dice;

/**
 * @author Nikola Bebic
 * @version 26-Jan-2017
 */
public interface IGame {

    int STARTING_NUMBER_OF_POINTS = 167;

    /**
     * Prepares the object for the game start
     * @param newTable Clean table object that will be used by the game
     */
    void start(ITable newTable);

    /**
     * Checks for winner of the game
     * @return ID of the winner, or PlayerId.NONE if there is no winner
     */
    PlayerId checkWinner();

    /**
     * Tries to play the move
     * @param move Move to be played
     * @return True if the move was played, false if it is not valid and not played
     */
    boolean tryPlayMove(Move move);

    /**
     * Gets the number of points for the first player
     * @return Number of points
     */
    int playerOnePoints();

    /**
     * Gets the number of points fot the second player
     * @return Number of points
     */
    int playerTwoPoints();

    /**
     * Calculates all possible moves for the player
     * @param playerId Player ID
     * @param dice Dice roll
     * @return Array of all possible moves
     */
    ArrayList<Move> calculateAllPossibleMoves(PlayerId playerId, Dice dice);

    /**
     * Makes a deep copy of the game and returns it
     * @return Deep copy of the game
     */
    IGame makeCopy();

    /**
     * Gets the game table
     * @return The game table
     */
    ITable table();
}
