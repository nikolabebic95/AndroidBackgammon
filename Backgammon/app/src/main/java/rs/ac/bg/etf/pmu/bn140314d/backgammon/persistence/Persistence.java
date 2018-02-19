package rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.GameModel;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.FieldFactory;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.Game;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.Table;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db.AppDatabase;

/**
 * Utility class used for accessing persistent data
 */
public final class Persistence {
    /**
     * Prevent instantiating of the utility class
     */
    private Persistence() {}

    private static final String PREFERENCES_FILE_NAME = "preferences.xml";
    private static final String GAME_MODEL_FILE_NAME = "game_model.bin";

    private static AppDatabase appDatabase;

    private static Settings cachedSettings;
    private static GameModel cachedGameModel;

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "backgammondb").allowMainThreadQueries().build();
        }

        return appDatabase;
    }

    /**
     * Checks if there is already a started game
     * @return Whether a started game exists
     */
    public static boolean checkIfStartedGameExists(Context context) {
        if (cachedGameModel != null) return true;

        File file = new File(context.getFilesDir(), GAME_MODEL_FILE_NAME);
        return file.exists();
    }

    /**
     * Erases the current game from the database
     */
    public static void clearCurrentGame(Context context) {
        cachedGameModel = null;
        context.deleteFile(GAME_MODEL_FILE_NAME);
    }

    /**
     * Loads the settings from the persistent preferences
     * @param context Context used for obtaining the preferences object
     * @return Loaded settings
     */
    public static Settings loadSettings(Context context) {
        if (cachedSettings == null) {
            SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
            Settings settings = new Settings();

            settings.setPlayer1Name(preferences.getString(Settings.player1NamePreference, Settings.defaultPlayer1Name));
            settings.setPlayer2Name(preferences.getString(Settings.player2NamePreference, Settings.defaultPlayer2Name));
            settings.setPlayer1Bot(preferences.getBoolean(Settings.player1BotPreference, Settings.defaultPlayer1Bot));
            settings.setPlayer2Bot(preferences.getBoolean(Settings.player2BotPreference, Settings.defaultPlayer2Bot));
            settings.setBoardIndex(preferences.getInt(Settings.boardIndexPreference, Settings.defaultBoardIndex));
            settings.setCheckerIndex(preferences.getInt(Settings.checkerIndexPreference, Settings.defaultCheckerIndex));
            settings.setSoundOn(preferences.getBoolean(Settings.soundOnPreference, Settings.defaultSoundOn));

            cachedSettings = settings;

            return settings;
        } else {
            return cachedSettings;
        }
    }

    /**
     * Saves the settings to the persistent preferences
     * @param context Context used for obtaining the preferences object
     * @param settings Settings to save
     */
    public static void saveSettings(Context context, Settings settings) {
        cachedSettings = settings;
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

    public static GameModel loadGameModel(Context context) {
        if (cachedGameModel == null) {
            if (checkIfStartedGameExists(context)) {
                try (FileInputStream fis = context.openFileInput(GAME_MODEL_FILE_NAME); ObjectInputStream ois = new ObjectInputStream(fis)) {
                    cachedGameModel = (GameModel)ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                return cachedGameModel;
            } else {
                Game game = new Game();
                game.start(new Table(new FieldFactory()));
                cachedGameModel = new GameModel(game);
                return cachedGameModel;
            }
        } else {
            return cachedGameModel;
        }
    }

    public static void saveGameModel(Context context, GameModel gameModel) {
        cachedGameModel = gameModel;
        try (FileOutputStream fos = context.openFileOutput(GAME_MODEL_FILE_NAME, Context.MODE_PRIVATE); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
