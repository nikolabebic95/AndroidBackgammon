package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers;

import java.util.ArrayList;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.R;

public final class DiceImagesHelper {
    private DiceImagesHelper() {}

    private static ArrayList<Integer> images = new ArrayList<>();
    static {
        images.add(R.drawable.dice_1);
        images.add(R.drawable.dice_2);
        images.add(R.drawable.dice_3);
        images.add(R.drawable.dice_4);
        images.add(R.drawable.dice_5);
        images.add(R.drawable.dice_6);
    }

    public static int getDiceResource(int index) {
        return images.get(index - 1);
    }
}
