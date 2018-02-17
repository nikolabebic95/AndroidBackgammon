package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers;

import android.graphics.Point;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.ITable;

public final class FieldGeometryUtility {
    private FieldGeometryUtility() {}

    private static final int FIELDS_IN_ROW = ITable.NUMBER_OF_FIELDS / 2;

    public static Point pointFromIndex(int index) {
        if (index < FIELDS_IN_ROW) {
            return new Point(FIELDS_IN_ROW - index - 1, 0);
        } else {
            return new Point(index - FIELDS_IN_ROW, 1);
        }
    }

    public static Point canvasPointFromPoint(Point point, int canvasWidth, int canvasHeight, BoardFeatures boardFeatures) {
        Point ret = new Point(point);
        double checkerWidth = boardFeatures.getCheckerWidth() * canvasWidth;
        double fieldHeight = boardFeatures.getCheckerHeight() * (boardFeatures.getMaxCheckersOnField() * 2 - 1) * canvasHeight;
        double leftOffset = boardFeatures.getLeftOffset() * canvasWidth;
        double upOffset = boardFeatures.getUpOffset() * canvasHeight;
        double middleOffset = boardFeatures.getMiddleOffset() * canvasWidth;

        ret.x *= checkerWidth;
        ret.y *= fieldHeight;
        ret.x += leftOffset;
        ret.y += upOffset;
        if (point.x >= ITable.NUMBER_OF_FIELDS / 4) ret.x += middleOffset;

        return ret;
    }
}
