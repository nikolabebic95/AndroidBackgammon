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
    void insertPair(PairTable pairTable);

    @Insert
    void insertGame(GameTable gameTable);

    @Update
    void updatePair(PairTable pairTable);

    @Update
    void updateGame(GameTable gameTable);

    @Delete
    void deletePair(PairTable pairTable);

    @Delete
    void deleteGame(GameTable gameTable);

    @Query("SELECT * FROM pair_table")
    List<PairTable> loadAllPairs();

    @Query("SELECT * FROM pair_table WHERE pair_id = :pairId")
    PairTable loadPairById(int pairId);

    @Query("SELECT * FROM pair_table WHERE player_one_name = :playerOneName AND player_two_name = :playerTwoName OR player_one_name = :playerTwoName AND player_two_name = :playerOneName")
    PairTable loadPairByNames(String playerOneName, String playerTwoName);

    @Query("SELECT * FROM game_table WHERE pair_id = :pairId ORDER BY game_timestamp DESC")
    List<GameTable> loadAllGamesOfPair(int pairId);
}
