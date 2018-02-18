package rs.ac.bg.etf.pmu.bn140314d.backgammon.players;

import java.io.Serializable;
import java.util.Random;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.RandomUtility;

/**
 * @author Nikola Bebic
 * @version 26-Jan-2017
 */
public class Dice implements Serializable {

    // region Private fields

    private int smallerDie;
    private int greaterOrEqualDie;

    // endregion

    // region Public interface

    public int getSmallerDie() {
        return smallerDie;
    }

    public int getGreaterOrEqualDie() {
        return greaterOrEqualDie;
    }

    // endregion

    // region Dice roll implementation
    public static final int DICE_MAX = 6;

    public Dice(int smallerDie, int greaterOrEqualDie) {
        this.smallerDie = smallerDie;
        this.greaterOrEqualDie = greaterOrEqualDie;
    }

    public static Dice roll() {
        Random randomGenerator = RandomUtility.getRandom();
        int small = randomGenerator.nextInt(DICE_MAX) + 1;
        int big = randomGenerator.nextInt(DICE_MAX) + 1;
        if (small > big) {
            int temp = small;
            small = big;
            big = temp;
        }

        return new Dice(small, big);
    }

    // endregion
}
