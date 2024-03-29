package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.R;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.HallOfFameAdapter;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Persistence;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db.AppDatabase;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db.DataAccessObject;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db.PairTable;

public class HallOfFameActivity extends AppCompatActivity {

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 100;
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
        AppDatabase appDatabase = Persistence.getAppDatabase(this);
        DataAccessObject dao = appDatabase.dataAccessObject();
        dao.deleteAllPairs();
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);
        mContentView = findViewById(R.id.fullscreen_content);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
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

    private void init() {
        ListView listView = findViewById(R.id.list);

        AppDatabase appDatabase = Persistence.getAppDatabase(this);
        DataAccessObject dao = appDatabase.dataAccessObject();
        List<PairTable> pairTables = dao.loadAllPairs();

        HallOfFameAdapter adapter = new HallOfFameAdapter(this, pairTables);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Persistence.savePairId(pairTables.get(position).getPairId());
            Intent intent = new Intent(HallOfFameActivity.this, GameHistoryActivity.class);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener(((adapterView, view, position, id) -> {
            dao.deletePair(pairTables.get(position));
            init();
            return true;
        }));
    }
}
