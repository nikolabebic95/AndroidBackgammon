package rs.ac.bg.etf.pmu.bn140314d.backgammon.logic;

/**
 * @author Nikola Bebic
 * @version 25-Jan-2017
 */
public class Move {

    // Bar is represented with -1 in the chipIndices array

    /**
     * ID of the player on the move
     */
    public PlayerId playerId;

    /**
     * Array of indices from where the chips will be moved
     */
    public int[] chipIndices;

    /**
     * Array of moves containing the information how many fields the chips will be moved
     */
    public int[] chipMoves;

    /**
     * Length of the arrays
     */
    public int numberOfMovedChips;

    public Move(PlayerId playerId, int numberOfMovedChips) {
        this.playerId = playerId;
        this.numberOfMovedChips = numberOfMovedChips;
        this.chipIndices = new int[numberOfMovedChips];
        this.chipMoves = new int[numberOfMovedChips];
    }
}
