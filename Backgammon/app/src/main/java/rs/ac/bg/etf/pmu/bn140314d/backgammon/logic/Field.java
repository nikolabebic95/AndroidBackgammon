package rs.ac.bg.etf.pmu.bn140314d.backgammon.logic;

/**
 * @author Nikola Bebic
 * @version 25-Jan-2017
 */
public class Field implements IField {

    // region Private fields

    private PlayerId playerId = PlayerId.NONE;
    private int numberOfChips = 0;

    // endregion

    // region IField implementation

    @Override
    public PlayerId getPlayerId() {
        return playerId;
    }

    @Override
    public int getNumberOfChips() {
        return numberOfChips;
    }

    @Override
    public void increaseNumberOfChips(PlayerId playerId, int number) {
        this.numberOfChips += number;
        this.playerId = playerId;
    }

    @Override
    public void decreaseNumberOfChips(PlayerId playerId, int number) {
        this.numberOfChips -= number;
        if (this.numberOfChips == 0) {
            this.playerId = PlayerId.NONE;
        }
    }

    @Override
    public Field makeCopy() {
        Field ret = new Field();
        ret.playerId = this.playerId;
        ret.numberOfChips = this.numberOfChips;

        return ret;
    }

    // endregion
}
