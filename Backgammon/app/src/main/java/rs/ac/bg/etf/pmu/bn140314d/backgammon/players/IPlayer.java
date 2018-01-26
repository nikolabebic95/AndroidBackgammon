package rs.ac.bg.etf.pmu.bn140314d.backgammon.players;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.*;

/**
 * @author Nikola Bebic
 * @version 26-Jan-2017
 */
public interface IPlayer {

    /**
     * Function which all players should implement, to play the game
     * @param game Game object
     * @param myId ID of the current player
     * @param dice Dice roll the player got
     * @return Move object the player has chosen
     */
    Move playMove(IGame game, PlayerId myId, Dice dice);
}
