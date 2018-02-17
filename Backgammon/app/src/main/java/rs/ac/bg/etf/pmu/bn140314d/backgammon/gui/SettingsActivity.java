package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.R;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Persistence;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.Settings;

public class SettingsActivity extends AppCompatActivity {

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 100;
    private static final String BUNDLE_KEY = "settings";
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private Settings settings;

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

    public void onSwitch1Clicked(View view) {
        Switch switch_view = findViewById(R.id.player_1_bot);
        TextView textView = findViewById(R.id.player_1_bot_text);
        if (switch_view.isChecked()) {
            textView.setText(getResources().getText(R.string.cpu));
        } else {
            textView.setText(getResources().getText(R.string.human));
        }
    }

    public void onSwitch2Clicked(View view) {
        Switch switch_view = findViewById(R.id.player_2_bot);
        TextView textView = findViewById(R.id.player_2_bot_text);
        textView.setText(getBotText(switch_view.isChecked()));
    }

    public void onLeftBoardClicked(View view) {
        settings.prevBoard();
        ImageView imageView = findViewById(R.id.board_image);
        imageView.setImageResource(settings.getBoard());
    }

    public void onRightBoardClicked(View view) {
        settings.nextBoard();
        ImageView imageView = findViewById(R.id.board_image);
        imageView.setImageResource(settings.getBoard());
    }

    public void onLeftCheckersClicked(View view) {
        settings.prevCheckers();
        ImageView checker1 = findViewById(R.id.checker_1_image);
        ImageView checker2 = findViewById(R.id.checker_2_image);
        checker1.setImageResource(settings.getPlayer1Checker());
        checker2.setImageResource(settings.getPlayer2Checker());
    }

    public void onRightCheckersClicked(View view) {
        settings.nextCheckers();
        ImageView checker1 = findViewById(R.id.checker_1_image);
        ImageView checker2 = findViewById(R.id.checker_2_image);
        checker1.setImageResource(settings.getPlayer1Checker());
        checker2.setImageResource(settings.getPlayer2Checker());
    }

    public void onSoundSwitchClicked(View view) {
        Switch switch_view = findViewById(R.id.sound);
        TextView textView = findViewById(R.id.sound_text);
        textView.setText(getSoundText(switch_view.isChecked()));
    }

    public void onConfirmClicked(View view) {
        Persistence.saveSettings(settings);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mContentView = findViewById(R.id.fullscreen_content);
        init(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstanceState();
        outState.putSerializable(BUNDLE_KEY, settings);
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
        if (savedInstanceState == null) {
            settings = Persistence.loadSettings();
        } else {
            settings = (Settings)savedInstanceState.get(BUNDLE_KEY);
        }

        EditText player1Name = findViewById(R.id.player_1_name);
        EditText player2Name = findViewById(R.id.player_2_name);
        Switch player1Bot = findViewById(R.id.player_1_bot);
        Switch player2Bot = findViewById(R.id.player_2_bot);
        TextView player1BotText = findViewById(R.id.player_1_bot_text);
        TextView player2BotText = findViewById(R.id.player_2_bot_text);
        ImageView boardImage = findViewById(R.id.board_image);
        ImageView checker1Image = findViewById(R.id.checker_1_image);
        ImageView checker2Image = findViewById(R.id.checker_2_image);
        Switch sound = findViewById(R.id.sound);
        TextView soundText = findViewById(R.id.sound_text);

        player1Name.setText(settings.getPlayer1Name());
        player2Name.setText(settings.getPlayer2Name());
        player1Bot.setChecked(settings.isPlayer1Bot());
        player2Bot.setChecked(settings.isPlayer2Bot());
        player1BotText.setText(getBotText(settings.isPlayer1Bot()));
        player2BotText.setText(getBotText(settings.isPlayer2Bot()));
        boardImage.setImageResource(settings.getBoard());
        checker1Image.setImageResource(settings.getPlayer1Checker());
        checker2Image.setImageResource(settings.getPlayer2Checker());
        sound.setChecked(settings.isSoundOn());
        soundText.setText(getSoundText(settings.isSoundOn()));
    }

    private void saveInstanceState() {
        EditText player1Name = findViewById(R.id.player_1_name);
        EditText player2Name = findViewById(R.id.player_2_name);
        Switch player1Bot = findViewById(R.id.player_1_bot);
        Switch player2Bot = findViewById(R.id.player_2_bot);

        settings.setPlayer1Name(player1Name.getText().toString());
        settings.setPlayer2Name(player2Name.getText().toString());
        settings.setPlayer1Bot(player1Bot.isChecked());
        settings.setPlayer2Bot(player2Bot.isChecked());
    }

    private CharSequence getBotText(boolean isBot) {
        if (isBot) {
            return getResources().getText(R.string.cpu);
        } else {
            return getResources().getText(R.string.human);
        }
    }

    private CharSequence getSoundText(boolean isSoundOn) {
        if (isSoundOn) {
            return getResources().getText(R.string.sound_on);
        } else {
            return getResources().getText(R.string.sound_off);
        }
    }
}
