package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.R;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameHistoryAdapter;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Persistence;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db.AppDatabase;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db.DataAccessObject;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db.GameTable;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db.PairTable;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameHistoryActivity extends AppCompatActivity {

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    // Delayed removal of status and navigation bar
    // Note that some of these constants are new as of API 16 (Jelly Bean)
    // and API 19 (KitKat). It is safe to use them, as they are inlined
    // at compile-time and do nothing on earlier devices.
    private final Runnable mHidePart2Runnable = () -> mContentView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LOW_PROFILE |
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    public void onClearClicked(View view) {
        long pairId = Persistence.loadPairId();
        AppDatabase appDatabase = Persistence.getAppDatabase(this);
        DataAccessObject dao = appDatabase.dataAccessObject();

        dao.deletePair(dao.loadPairById(pairId));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_history);

        mContentView = findViewById(R.id.fullscreen_content);

        init();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    void init() {
        long pairId = Persistence.loadPairId();

        ListView listView = findViewById(R.id.list);

        AppDatabase appDatabase = Persistence.getAppDatabase(this);
        DataAccessObject dao = appDatabase.dataAccessObject();
        List<GameTable> gameTables = dao.loadAllGamesOfPair(pairId);

        GameHistoryAdapter adapter = new GameHistoryAdapter(this, gameTables);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(((adapterView, view, position, id) -> {
            PairTable pairTable = dao.loadPairById(pairId);
            if (pairTable.getPlayerOneName().equals(gameTables.get(position).getWinner())) {
                pairTable.setPlayerOneVictories(pairTable.getPlayerOneVictories() - 1);
            } else {
                pairTable.setPlayerTwoVictories(pairTable.getPlayerTwoVictories() - 1);
            }

            dao.updatePair(pairTable);
            dao.deleteGame(gameTables.get(position));
            init();
            return true;
        }));
    }
}
