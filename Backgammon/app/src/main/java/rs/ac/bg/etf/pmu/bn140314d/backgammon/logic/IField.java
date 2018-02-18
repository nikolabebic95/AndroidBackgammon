package rs.ac.bg.etf.pmu.bn140314d.backgammon.logic;

import java.io.Serializable;

/**
 * @author Nikola Bebic
 * @version 25-Jan-2017
 */
public interface IField extends Serializable {

    /**
     * Gets the player ID of the field
     * @return Player ID of the field
     */
    PlayerId getPlayerId();

    /**
     * Gets the number of chips player has on the field
     * @return Number of chips player has on the field
     */
    int getNumberOfChips();

    /**
     * Increases number of chips by the given number
     * @param playerId ID of the player who is putting chips
     * @param number Number of chips to be added to field
     */
    void increaseNumberOfChips(PlayerId playerId, int number);

    /**
     * Increases number of chips by one
     * @param playerId ID of the player who is putting chips
     */
    default void increaseNumberOfChips(PlayerId playerId) {
        increaseNumberOfChips(playerId, 1);
    }

    /**
     * Decreases number of chips by the given number
     * @param playerId ID of the player who is removing chips
     * @param number Number of chips to be removed from field
     */
    void decreaseNumberOfChips(PlayerId playerId, int number);

    /**
     * Decreases number of chips by one
     * @param playerId ID of the player who is removing chips
     */
    default void decreaseNumberOfChips(PlayerId playerId) {
        decreaseNumberOfChips(playerId, 1);
    }

    /**
     * Makes a deep copy of the field, and returns it
     * @return Deep copy of the field
     */
    IField makeCopy();
}
