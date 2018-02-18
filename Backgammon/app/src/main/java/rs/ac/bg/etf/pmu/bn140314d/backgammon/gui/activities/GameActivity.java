package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers.ControllerState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers.WaitingForDiceRollState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.controllers.WaitingForMoveState;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.DiceImagesHelper;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Persistence;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Settings;

public class GameActivity extends AppCompatActivity {

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 100;

    private static final int SAMPLING_PERIOD_MICROSECONDS = 20000;

    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private Settings settings;

    private GameModel gameModel;
    private ControllerState controller;
    private CanvasView canvasView;

    private SensorManager sensorManager;

    private int shakesCounter = 0;
    private int periodCounter = 0;
    private boolean shake = false;

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            float acceleration = (float) Math.sqrt((double) x * x + y * y + z * z);

            periodCounter++;
            if (acceleration > 15) shakesCounter++;
            if (periodCounter == 25) {
                shake = shakesCounter > 5;
                shakesCounter = 0;
                periodCounter = 0;
                controller.onSensorChanged(sensorEvent);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            controller.onAccuracyChanged(sensor, i);
        }
    };

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

    public void onDiceRollClicked(View view) {
        controller.onDiceRollClicked(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mContentView = findViewById(R.id.fullscreen_content);

        init(savedInstanceState);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        registerListener();

        canvasView = findViewById(R.id.canvas_view);
        canvasView.setGameActivity(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (controller.usesAccelerometer()) {
            registerListener();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (controller.usesAccelerometer()) {
            unregisterListener();
        }
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
        gameModel = Persistence.loadGameModel(this);

        loadInstanceState();
    }

    private void loadInstanceState() {
        TextView player1 = findViewById(R.id.player_1_text_view);
        TextView player2 = findViewById(R.id.player_2_text_view);
        player1.setText(settings.getPlayer1Name());
        player2.setText(settings.getPlayer2Name());

        if (gameModel.getGameState() == GameState.SHOULD_ROLL) controller = new WaitingForDiceRollState(this);
        else controller = new WaitingForMoveState(this);

        toggleDiceAndButton();
        updateDice();
    }

    public Settings getSettings() {
        return settings;
    }

    public ControllerState getController() {
        return controller;
    }

    public void setController(ControllerState controller) {
        this.controller = controller;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public CanvasView getCanvasView() {
        return canvasView;
    }

    public boolean isShake() {
        return shake;
    }

    public void registerListener() {
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SAMPLING_PERIOD_MICROSECONDS);
    }

    public void unregisterListener() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    public void toggleDiceAndButton() {
        View dice = findViewById(R.id.dice);
        View button = findViewById(R.id.roll_dice);
        boolean diceVisible = controller.diceVisible();

        if (diceVisible) {
            dice.setVisibility(View.VISIBLE);
            button.setVisibility(View.INVISIBLE);
        } else {
            dice.setVisibility(View.INVISIBLE);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void updateDice() {
        if (gameModel.getDice() != null) {
            ImageView dice1 = findViewById(R.id.dice1);
            ImageView dice2 = findViewById(R.id.dice2);

            if (gameModel.isDiceInverted()) {
                dice1.setImageResource(DiceImagesHelper.getDiceResource(gameModel.getDice().getSmallerDie()));
                dice2.setImageResource(DiceImagesHelper.getDiceResource(gameModel.getDice().getGreaterOrEqualDie()));
            } else {
                dice1.setImageResource(DiceImagesHelper.getDiceResource(gameModel.getDice().getGreaterOrEqualDie()));
                dice2.setImageResource(DiceImagesHelper.getDiceResource(gameModel.getDice().getSmallerDie()));
            }
        }
    }
}
