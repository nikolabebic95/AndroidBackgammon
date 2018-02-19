package rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "pair_table")
public class PairTable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pair_id")
    private long pairId;

    @ColumnInfo(name = "player_one_name")
    private String playerOneName;

    @ColumnInfo(name = "player_two_name")
    private String playerTwoName;

    @ColumnInfo(name = "player_one_victories")
    private int playerOneVictories;

    @ColumnInfo(name = "player_two_victories")
    private int playerTwoVictories;

    public long getPairId() {
        return pairId;
    }

    public void setPairId(long pairId) {
        this.pairId = pairId;
    }

    public String getPlayerOneName() {
        return playerOneName;
    }

    public void setPlayerOneName(String playerOneName) {
        this.playerOneName = playerOneName;
    }

    public String getPlayerTwoName() {
        return playerTwoName;
    }

    public void setPlayerTwoName(String playerTwoName) {
        this.playerTwoName = playerTwoName;
    }

    public int getPlayerOneVictories() {
        return playerOneVictories;
    }

    public void setPlayerOneVictories(int playerOneVictories) {
        this.playerOneVictories = playerOneVictories;
    }

    public int getPlayerTwoVictories() {
        return playerTwoVictories;
    }

    public void setPlayerTwoVictories(int playerTwoVictories) {
        this.playerTwoVictories = playerTwoVictories;
    }
}
