package rs.ac.bg.etf.pmu.bn140314d.backgammon.logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Nikola Bebic
 * @version 25-Jan-2017
 */
public interface ITable extends Serializable {

    /**
     * Total number of fields on the table
     */
    int NUMBER_OF_FIELDS = 24;

    /**
     * Total number of checkers per player
     */
    int NUMBER_OF_CHECKERS_PER_PLAYER = 15;

    /**
     * Checks whether the player can bear off his checkers
     * @param playerId Player ID
     * @return Whether the player can bear off his checkers
     */
    boolean canBearOff(PlayerId playerId);

    /**
     * Bears off one of the players checkers
     * @param playerId Player ID
     */
    void bearOff(PlayerId playerId);

    /**
     * Gets the field at the specified index
     * Player 1 moves towards 23, player 2 moves towards 0
     * @param index Index of field
     * @return IField object at the index
     */
    IField getField(int index);

    /**
     * Moves one chip
     * @param index Index of the chip to be moved
     * @param numberOfFields Number of fields chip is moved
     * @return True if the move was successfull, false otherwise
     */
    boolean moveChip(int index, int numberOfFields);

    /**
     * Spawns one chip from bar
     * @param playerId Player ID
     * @param numberOfFields Number of fields chip is moved from the beginning
     * @return True if the spawn was successful, false otherwise
     */
    boolean spawnFromBar(PlayerId playerId, int numberOfFields);

    /**
     * Makes a deep copy of the table, and returns it
     * @return Deep copy of the table
     */
    ITable makeCopy();

    /**
     * Calculates the number of points for the given player
     * @param playerId Player ID
     * @return Number of points of the player
     */
    int calculateNumberOfPoints(PlayerId playerId);

    /**
     * Gets the number of chips on the bar for player 1
     * @return Number of player 1 chips on the bar
     */
    int getPlayerOneBar();

    /**
     * Gets the number of chips on the bar for player 2
     * @return Number of player 2 chips on the bar
     */
    int getPlayerTwoBar();

    /**
     * Sets the number of chips on the bar for player 1
     * @param playerOneBar Number of player 1 chips on the bar
     */
    void setPlayerOneBar(int playerOneBar);

    /**
     * Sets the number of chips on the bar for player 2
     * @param playerTwoBar Number of player 2 chips on the bar
     */
    void setPlayerTwoBar(int playerTwoBar);

    /**
     * Gets the number of chips player 1 has born off
     * @return Number of born off chips for player 1
     */
    int getPlayerOneOff();

    /**
     * Gets the number of chips player 2 has born off
     * @return Number of born off chips for player 2
     */
    int getPlayerTwoOff();

    /**
     * Calculates all indices of the table where the player has any chips
     * @param playerId Player ID
     * @return ArrayList object containing all indices where the player has any chips
     */
    ArrayList<Integer> getAllIndicesWithPlayer(PlayerId playerId);
}
