package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers;

import android.graphics.Point;
import android.graphics.Rect;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.ITable;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.PlayerId;

public final class GeometryUtility {
    private GeometryUtility() {}

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

    public static Point barPointFromPlayerId(PlayerId playerId, int canvasWidth, int canvasHeight, BoardFeatures boardFeatures) {
        Point ret = new Point();
        double checkerWidth = boardFeatures.getCheckerWidth() * canvasWidth;
        double checkerHeight = boardFeatures.getCheckerHeight() * canvasHeight;
        double fieldHeight = boardFeatures.getCheckerHeight() * (boardFeatures.getMaxCheckersOnField() - 1) * canvasHeight;
        double leftOffset = boardFeatures.getLeftOffset() * canvasWidth;
        double upOffset = boardFeatures.getUpOffset() * canvasHeight;
        double middleOffset = boardFeatures.getMiddleOffset() * canvasWidth;

        ret.x = (int)(leftOffset + checkerWidth * ITable.NUMBER_OF_FIELDS / 4 + middleOffset / 2 - checkerWidth / 2);
        ret.y = (int)(upOffset + fieldHeight);

        if (playerId == PlayerId.SECOND) ret.y += checkerHeight;

        return ret;
    }

    public static Rect barRectFromPlayerId(PlayerId playerId, int canvasWidth, int canvasHeight, BoardFeatures boardFeatures) {
        Point upperLeft = new Point();
        double checkerWidth = boardFeatures.getCheckerWidth() * canvasWidth;
        double fieldHeight = boardFeatures.getCheckerHeight() * boardFeatures.getMaxCheckersOnField() * canvasHeight;
        double leftOffset = boardFeatures.getLeftOffset() * canvasWidth;
        double upOffset = boardFeatures.getUpOffset() * canvasHeight;
        double middleOffset = boardFeatures.getMiddleOffset() * canvasWidth;

        upperLeft.x = (int)(leftOffset + checkerWidth * ITable.NUMBER_OF_FIELDS / 4);
        upperLeft.y = (int)upOffset;

        if (playerId == PlayerId.SECOND) upperLeft.y += fieldHeight;

        Point lowerRight = new Point(upperLeft);
        lowerRight.x += middleOffset;
        lowerRight.y += fieldHeight;

        return new Rect(upperLeft.x, upperLeft.y, lowerRight.x, lowerRight.y);
    }

    public static Rect rectFromPoint(Point point, int canvasWidth, int canvasHeight, BoardFeatures boardFeatures) {
        Point upperLeft = new Point(point);
        double checkerWidth = boardFeatures.getCheckerWidth() * canvasWidth;
        double fieldHeight = boardFeatures.getCheckerHeight() * boardFeatures.getMaxCheckersOnField() * canvasHeight;
        double leftOffset = boardFeatures.getLeftOffset() * canvasWidth;
        double upOffset = boardFeatures.getUpOffset() * canvasHeight;
        double middleOffset = boardFeatures.getMiddleOffset() * canvasWidth;

        upperLeft.x *= checkerWidth;
        upperLeft.y *= fieldHeight;
        upperLeft.x += leftOffset;
        upperLeft.y += upOffset;
        if (point.x >= ITable.NUMBER_OF_FIELDS / 4) upperLeft.x += middleOffset;

        Point lowerRight = new Point(upperLeft);
        lowerRight.x += checkerWidth;
        lowerRight.y += fieldHeight;

        return new Rect(upperLeft.x, upperLeft.y, lowerRight.x, lowerRight.y);
    }

    public static double distance(Point point, Rect rect) {
        int cx = Math.max(Math.min(point.x, rect.right), rect.left);
        int cy = Math.max(Math.min(point.y, rect.bottom), rect.top);

        int dx = point.x - cx;
        int dy = point.y - cy;

        return Math.sqrt(dx * dx + dy * dy);
    }
}
