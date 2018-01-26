package rs.ac.bg.etf.pmu.bn140314d.backgammon.players;

import java.util.ArrayList;
import java.util.Random;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.*;

/**
 * @author Nikola Bebic
 * @version 26-Jan-2017
 */
public class RandomBot implements IPlayer {

    @Override
    public Move playMove(IGame game, PlayerId myId, Dice dice) {
        Random random = new Random();

        ArrayList<Move> moves = game.calculateAllPossibleMoves(myId, dice);

        if (moves.size() == 0) {
            return null;
        }

        return moves.get(random.nextInt(moves.size()));
    }
}
