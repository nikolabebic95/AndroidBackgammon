package rs.ac.bg.etf.pmu.bn140314d.backgammon.logic;

import java.util.ArrayList;

/**
 * @author Nikola Bebic
 * @version 26-Jan-2017
 */
public class Table implements ITable {

    // region Private fields

    private final IField[] fields = new IField[ITable.NUMBER_OF_FIELDS];

    private int playerOneBar = 0;
    private int playerTwoBar = 0;

    private int playerOneOff = 0;
    private int playerTwoOff = 0;

    // endregion

    // region Constructors

    private Table() {
    }

    public Table(IFieldFactory fieldFactory) {
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fieldFactory.create();
        }
    }

    // endregion

    // region Helpers

    private boolean canBePlaced(PlayerId playerId, int index) {
        // The chip can be placed on the field if and only if:
        //   1) The field is empty
        //   2) The field contains some chips from the current player
        //   3) The field contains only one chip from the opposing player
        return fields[index].getPlayerId() == PlayerId.NONE || fields[index].getPlayerId() == playerId || fields[index].getNumberOfChips() == 1;
    }

    private boolean canHit(PlayerId playerId, int index) {
        return fields[index].getPlayerId() != PlayerId.NONE && fields[index].getPlayerId() != playerId && fields[index].getNumberOfChips() == 1;
    }

    private void hit(PlayerId playerId) {
        if (playerId == PlayerId.FIRST) {
            playerTwoBar++;
        } else {
            playerOneBar++;
        }
    }

    private boolean tryPutChip(PlayerId playerId, int index) {
        if (!canBePlaced(playerId, index)) {
            return false;
        }

        if (canHit(playerId, index)) {
            this.fields[index].decreaseNumberOfChips(playerId);
            hit(playerId);
        }

        this.fields[index].increaseNumberOfChips(playerId);

        return true;
    }

    // endregion

    // region ITable implementation

    @Override
    public boolean canBearOff(PlayerId playerId) {
        if (playerId == PlayerId.FIRST && playerOneBar != 0) {
            return false;
        } else if (playerId == PlayerId.SECOND && playerTwoBar != 0) {
            return false;
        }

        int offset = playerId == PlayerId.SECOND ? ITable.NUMBER_OF_FIELDS / 4 : 0;
        for (int i = 0; i < ITable.NUMBER_OF_FIELDS * 3 / 4; i++) {
            if (fields[i + offset].getPlayerId() == playerId) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void bearOff(PlayerId playerId) {
        if (playerId == PlayerId.FIRST) {
            playerOneOff++;
        } else {
            playerTwoOff++;
        }
    }

    @Override
    public IField getField(int index) {
        return this.fields[index];
    }

    @Override
    public boolean moveChip(int index, int numberOfFields) {
        PlayerId playerId = this.fields[index].getPlayerId();

        int newIndex;
        if (playerId == PlayerId.FIRST) {
            newIndex = index + numberOfFields;
        }
        else {
            newIndex = index - numberOfFields;
        }

        if (canBearOff(playerId) && (newIndex < 0 || newIndex >= ITable.NUMBER_OF_FIELDS)) {
            bearOff(playerId);
            fields[index].decreaseNumberOfChips(playerId);
            return true;
        }

        if (newIndex < 0 || newIndex >= ITable.NUMBER_OF_FIELDS) {
            return false;
        }

        boolean ret = tryPutChip(playerId, newIndex);
        if (ret) {
            fields[index].decreaseNumberOfChips(playerId);
        }

        return ret;
    }

    @Override
    public boolean spawnFromBar(PlayerId playerId, int numberOfFields) {
        int index;
        if (playerId == PlayerId.FIRST) {
            index = numberOfFields - 1;
        }
        else {
            index = ITable.NUMBER_OF_FIELDS - numberOfFields;
        }

        boolean ret = tryPutChip(playerId, index);

        if (ret) {
            if (playerId == PlayerId.FIRST) {
                playerOneBar--;
            } else {
                playerTwoBar--;
            }
        }

        return ret;
    }

    @Override
    public Table makeCopy() {
        Table ret = new Table();
        for (int i = 0; i < ret.fields.length; i++) {
            ret.fields[i] = this.fields[i].makeCopy();
        }

        ret.playerOneBar = this.playerOneBar;
        ret.playerOneOff = this.playerOneOff;
        ret.playerTwoBar = this.playerTwoBar;
        ret.playerTwoOff = this.playerTwoOff;

        return ret;
    }

    @Override
    public int calculateNumberOfPoints(PlayerId playerId) {
        int ret = 0;

        for (int i = 0; i < ITable.NUMBER_OF_FIELDS; i++) {
            int multiple = playerId == PlayerId.FIRST ? i + 1 : ITable.NUMBER_OF_FIELDS - 1;
            IField field = getField(i);
            if (field.getPlayerId() == playerId) {
                ret += multiple * field.getNumberOfChips();
            }
        }

        if (playerId == PlayerId.FIRST) {
            ret += playerOneBar * (ITable.NUMBER_OF_FIELDS + 1);
        } else {
            ret += playerTwoBar * (ITable.NUMBER_OF_FIELDS + 1);
        }

        return ret;
    }

    @Override
    public int getPlayerOneBar() {
        return playerOneBar;
    }

    @Override
    public int getPlayerTwoBar() {
        return playerTwoBar;
    }

    @Override
    public void setPlayerOneBar(int playerOneBar) {
        this.playerOneBar = playerOneBar;
    }

    @Override
    public void setPlayerTwoBar(int playerTwoBar) {
        this.playerTwoBar = playerTwoBar;
    }

    @Override
    public int getPlayerOneOff() {
        return playerOneOff;
    }

    @Override
    public int getPlayerTwoOff() {
        return playerTwoOff;
    }

    @Override
    public ArrayList<Integer> getAllIndicesWithPlayer(PlayerId playerId) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getPlayerId() == playerId) {
                indices.add(i);
            }
        }

        return indices;
    }

    // endregion
}
