package rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class used for accessing persistent data
 */
public final class Persistence {
    /**
     * Prevent instantiating of the utility class
     */
    private Persistence() {}

    private static final String PREFERENCES_FILE_NAME = "preferences.xml";

    /**
     * Checks if there is already a started game
     * @return Whether a started game exists
     */
    public static boolean checkIfStartedGameExists() {
        // TODO: Implementation
        return false;
    }

    /**
     * Erases the current game from the database
     */
    public static void clearCurrentGame() {
        // TODO: Implementation
    }

    public static Settings loadSettings(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        Settings settings = new Settings();

        settings.setPlayer1Name(preferences.getString(Settings.player1NamePreference, Settings.defaultPlayer1Name));
        settings.setPlayer2Name(preferences.getString(Settings.player2NamePreference, Settings.defaultPlayer2Name));
        settings.setPlayer1Bot(preferences.getBoolean(Settings.player1BotPreference, Settings.defaultPlayer1Bot));
        settings.setPlayer2Bot(preferences.getBoolean(Settings.player2BotPreference, Settings.defaultPlayer2Bot));
        settings.setBoardIndex(preferences.getInt(Settings.boardIndexPreference, Settings.defaultBoardIndex));
        settings.setCheckerIndex(preferences.getInt(Settings.checkerIndexPreference, Settings.defaultCheckerIndex));
        settings.setSoundOn(preferences.getBoolean(Settings.soundOnPreference, Settings.defaultSoundOn));

        return settings;
    }

    public static void saveSettings(Context context, Settings settings) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(Settings.player1NamePreference, settings.getPlayer1Name());
        editor.putString(Settings.player2NamePreference, settings.getPlayer2Name());
        editor.putBoolean(Settings.player1BotPreference, settings.isPlayer1Bot());
        editor.putBoolean(Settings.player2BotPreference, settings.isPlayer2Bot());
        editor.putInt(Settings.boardIndexPreference, settings.getBoardIndex());
        editor.putInt(Settings.checkerIndexPreference, settings.getCheckerIndex());
        editor.putBoolean(Settings.soundOnPreference, settings.isSoundOn());

        editor.apply();
    }
}
