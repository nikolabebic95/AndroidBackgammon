package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers;

public class BoardFeatures {
    private double leftOffset;
    private double upOffset;
    private double middleOffset;
    private double checkerWidth;
    private double checkerHeight;
    private int maxCheckersOnField;

    public BoardFeatures() {
    }

    public BoardFeatures(double leftOffset, double upOffset, double middleOffset, double checkerWidth, double checkerHeight, int maxCheckersOnField) {
        this.leftOffset = leftOffset;
        this.upOffset = upOffset;
        this.middleOffset = middleOffset;
        this.checkerWidth = checkerWidth;
        this.checkerHeight = checkerHeight;
        this.maxCheckersOnField = maxCheckersOnField;
    }

    public double getLeftOffset() {
        return leftOffset;
    }

    public void setLeftOffset(double leftOffset) {
        this.leftOffset = leftOffset;
    }

    public double getUpOffset() {
        return upOffset;
    }

    public void setUpOffset(double upOffset) {
        this.upOffset = upOffset;
    }

    public double getMiddleOffset() {
        return middleOffset;
    }

    public void setMiddleOffset(double middleOffset) {
        this.middleOffset = middleOffset;
    }

    public double getCheckerWidth() {
        return checkerWidth;
    }

    public void setCheckerWidth(double checkerWidth) {
        this.checkerWidth = checkerWidth;
    }

    public double getCheckerHeight() {
        return checkerHeight;
    }

    public void setCheckerHeight(double checkerHeight) {
        this.checkerHeight = checkerHeight;
    }

    public int getMaxCheckersOnField() {
        return maxCheckersOnField;
    }

    public void setMaxCheckersOnField(int maxCheckersOnField) {
        this.maxCheckersOnField = maxCheckersOnField;
    }
}
