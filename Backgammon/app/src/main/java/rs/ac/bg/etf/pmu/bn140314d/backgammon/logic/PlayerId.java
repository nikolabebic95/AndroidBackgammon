package rs.ac.bg.etf.pmu.bn140314d.backgammon.logic;

/**
 * @author Nikola Bebic
 * @version 26-Jan-2017
 */
public enum PlayerId {
    /**
     * Represents an empty field
     */
    NONE,

    /**
     * Represents the field where the first player has at least one chip
     */
    FIRST,
    /**
     * Represents the field whre the second player has at least one chip
     */
    SECOND;

    /**
     * Returns the opposite player ID
     * @return Opposite player ID
     */
    public PlayerId other() {
        if (this == PlayerId.FIRST) {
            return PlayerId.SECOND;
        } else {
            return PlayerId.FIRST;
        }
    }

    /**
     * Returns the String representation of the player
     * @return String representation of the player
     */
    public String toString() {
        if (this == PlayerId.FIRST) {
            return "Player One";
        } else {
            return "Player Two";
        }
    }
}
