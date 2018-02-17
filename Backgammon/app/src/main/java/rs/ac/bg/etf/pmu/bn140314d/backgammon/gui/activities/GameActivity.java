package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.R;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.CanvasView;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameModel;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers.ControllerState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.FieldFactory;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.Game;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.Table;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Persistence;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Settings;

public class GameActivity extends AppCompatActivity {

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 100;

    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private Settings settings;

    private GameModel gameModel;
    private ControllerState controller;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mContentView = findViewById(R.id.fullscreen_content);

        init(savedInstanceState);

        // TODO: Refactor
        Game game = new Game();
        game.start(new Table(new FieldFactory()));
        gameModel = new GameModel(game);

        CanvasView canvasView = findViewById(R.id.canvas_view);
        canvasView.setGameActivity(this);
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

    private void init(Bundle savedInstanceState) {
        settings = Persistence.loadSettings(this);

        loadInstanceState();
    }

    private void loadInstanceState() {
        TextView player1 = findViewById(R.id.player_1_text_view);
        TextView player2 = findViewById(R.id.player_2_text_view);

        player1.setText(settings.getPlayer1Name());
        player2.setText(settings.getPlayer2Name());
    }

    public Settings getSettings() {
        return settings;
    }

    public ControllerState getController() {
        return controller;
    }

    public GameModel getGameModel() {
        return gameModel;
    }
}
