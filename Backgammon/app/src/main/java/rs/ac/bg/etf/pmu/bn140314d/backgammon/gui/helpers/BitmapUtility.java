package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers;

import android.graphics.Bitmap;

public final class BitmapUtility {
    private BitmapUtility() {}

    public static Bitmap scaleBitmap(Bitmap original, int width, int height) {
        Bitmap scaled = Bitmap.createScaledBitmap(original, width, height, false);
        original.recycle();
        return scaled;
    }
}
