package rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DataAccessObject {
    @Insert
    long insertPair(PairTable pairTable);

    @Insert
    long insertGame(GameTable gameTable);

    @Update
    int updatePair(PairTable pairTable);

    @Delete
    int deletePair(PairTable pairTable);

    @Delete
    int deleteGame(GameTable gameTable);

    @Query("DELETE FROM pair_table")
    void deleteAllPairs();

    @Query("SELECT * FROM pair_table")
    List<PairTable> loadAllPairs();

    @Query("SELECT * FROM pair_table WHERE pair_id = :pairId LIMIT 1")
    PairTable loadPairById(long pairId);

    @Query("SELECT * FROM pair_table WHERE player_one_name = :playerOneName AND player_two_name = :playerTwoName OR player_one_name = :playerTwoName AND player_two_name = :playerOneName LIMIT 1")
    PairTable loadPairByNames(String playerOneName, String playerTwoName);

    @Query("SELECT * FROM game_table WHERE pair_id = :pairId ORDER BY game_timestamp DESC")
    List<GameTable> loadAllGamesOfPair(long pairId);
}
