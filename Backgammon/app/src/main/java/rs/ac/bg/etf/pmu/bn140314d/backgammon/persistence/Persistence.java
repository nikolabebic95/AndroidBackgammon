package rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence;

/**
 * Utility class used for accessing persistent data
 */
public final class Persistence {
    /**
     * Prevent instantiating of the utility class
     */
    private Persistence() {}

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

    public static Settings loadSettings() {
        // TODO: Actually load from persistent memory

        Settings settings = new Settings();
        settings.setBoard(0);
        settings.setCheckerIndex(0);
        settings.setPlayer1Name("Player 1");
        settings.setPlayer2Name("Player 2");
        settings.setPlayer1Bot(false);
        settings.setPlayer2Bot(false);
        settings.setSoundOn(true);
        return settings;
    }

    public static void saveSettings(Settings settings) {
        // TODO: Implementation
    }
}
