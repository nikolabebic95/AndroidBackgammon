package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers;

import java.util.Random;

public final class RandomUtility {
    private RandomUtility() {}

    private static Random random = new Random();

    public static Random getRandom() {
        return random;
    }
}
