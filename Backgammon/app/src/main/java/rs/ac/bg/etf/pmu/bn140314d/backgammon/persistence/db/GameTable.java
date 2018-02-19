package rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "game_table", foreignKeys = @ForeignKey(entity = PairTable.class,
                                                            parentColumns = "pair_id",
                                                            childColumns = "pair_id",
                                                            onDelete = ForeignKey.CASCADE))
public class GameTable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    private long gameId;

    @ColumnInfo(name = "game_timestamp")
    private long gameTimestamp;

    @ColumnInfo(name = "player_one_name")
    private String playerOneName;

    @ColumnInfo(name = "player_two_name")
    private String playerTwoName;

    @ColumnInfo(name = "winner")
    private String winner;

    @ColumnInfo(name = "player_one_points")
    private int playerOnePoints;

    @ColumnInfo(name = "player_two_points")
    private int playerTwoPoints;

    @ColumnInfo(name = "pair_id")
    private long pairId;

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getGameTimestamp() {
        return gameTimestamp;
    }

    public void setGameTimestamp(long gameTimestamp) {
        this.gameTimestamp = gameTimestamp;
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

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getPlayerOnePoints() {
        return playerOnePoints;
    }

    public void setPlayerOnePoints(int playerOnePoints) {
        this.playerOnePoints = playerOnePoints;
    }

    public int getPlayerTwoPoints() {
        return playerTwoPoints;
    }

    public void setPlayerTwoPoints(int playerTwoPoints) {
        this.playerTwoPoints = playerTwoPoints;
    }

    public long getPairId() {
        return pairId;
    }

    public void setPairId(long pairId) {
        this.pairId = pairId;
    }
}
