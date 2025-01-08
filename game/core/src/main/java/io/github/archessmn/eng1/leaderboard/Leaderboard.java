package io.github.archessmn.eng1.leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

/**
 * This class stores the top 5 scores that have been achieved, the usernames of
 * the player who achieved it and any achievements earned in the same 
 * playthrough.
 */
public class Leaderboard {
    // The maximum amount of leaderboard positions the leaderboard can store.
    private int maxCount;
    // The actual amount of leaderboard positions that are occupied.
    private int count;
    private LeaderboardPosition[] leaderboard;
    private String leaderboardPrefsDir;
    private Json json = new Json();

    /**
     * Create a new leaderboard with the default file path. The default file path
     * is "%UserProfile%"/.prefs/ENG1/leaderboard".
     */
    public Leaderboard() {
        this("ENG1/leaderboard");
    }

    /**
     * Creates a leaderboard based on the Prefs stored in 
     * "%UserProfile%/.prefs/leaderboardPrefsDir"
     * @param leaderboardPrefsDir The directory of the leaderboard file to read
     * and write from.
     */
    public Leaderboard(String leaderboardPrefsDir) {
        this.maxCount = 5;
        this.leaderboard = new LeaderboardPosition[maxCount];
        this.leaderboardPrefsDir = leaderboardPrefsDir;
        readFromFile();
    }
    
    /**
     * Reads from the leaderboardFile and setups up the leaderboard accordingly.
     */
    private void readFromFile() {
        Preferences prefs = Gdx.app.getPreferences(leaderboardPrefsDir);
        String text;
        LeaderboardPosition record;
        for (int i = 0; i < maxCount; i++) {
            // Gets the string stored in the file, converts it to a
            // LeaderboardPosition object then stores it in the leaderboard.
            text = prefs.getString("pos"+Integer.toString(i), "default");
            if (text.equals("default")) continue;
            record = json.fromJson(LeaderboardPosition.class, text);
            leaderboard[i] = record;
        }
        setCount();
    }

    /**
     * Sets the count attribute to the correct amount by checking how many
     * records in the leaderboard are not null.
     */
    private void setCount() {
        count = 0;
        for (int i = 0; i < maxCount; i++) {
            if (leaderboard[i] == null) return;
            count++;
        }
    }

    /**
     * Writes the current leaderboard to the file specified in constructor.
     */
    public void writeToFile() {
        setCount();
        Preferences prefs = Gdx.app.getPreferences(leaderboardPrefsDir);
        String jsonText;
        for (int i = 0; i < count; i++) {
            jsonText = json.prettyPrint(json.toJson(leaderboard[i]));
            prefs.putString("pos"+Integer.toString(i), jsonText);
        }
        prefs.flush();
    }

    /**
     * Adds a position to its valid position in the leaderboard based on its 
     * score and adjusts the other positions in the leaderboard accordingly.
     * To save these changes, call the {@link #writeToFile()} method. Will
     * not add the position to the leaderboard if its score is less than all
     * currently held positions.
     * @param position The position to add. 
     * @return true if the positions was added to the leaderboard and false
     * if it was not.
     */
    public boolean addPosition(LeaderboardPosition position) {
        for (int i = 0; i < maxCount; i++) {
            if (leaderboard[i] == null || position.compareTo(leaderboard[i]) > 0) {
                // Moves the previous placements one back and adds the new position.
                for (int j = maxCount-1; j > i; j--) {
                    leaderboard[j] = leaderboard[j-1];
                }
                leaderboard[i] = position;
                return true;
            }
        }
        return false;
    }

    /**
     * Returns how many valid LeaderboardPosition objects there are in the
     * leaderboard. If returns 3, that means 1st, 2nd and 3rd are all valid
     * but there are no positions beyond that even if the maxCount was greater.
     * @return Amount of valid leaderboard positions.
     */
    public int getCount() {
        setCount();
        return count;
    }

    /**
     * Returns the maximum amount of leaderboard positions this leaderboard can
     * hold which is defined in the constructor.
     * @return Max amount of leaderboard positions.
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     * Returns the LeaderboardPosition value at a certain index in the
     * leaderboard. 0 if the 1st in the leaderboard. Will return null if pos
     * is out of bounds and no value is present in leaderboard.
     * @param pos Position of record in leaderboard. 0 is 1st, 5 is 6th.
     * @return The data associated with that position in the leaderboard.
     */
    public LeaderboardPosition getLeaderboardPos(int pos) {
        if (pos < 0 || pos >= leaderboard.length || leaderboard[pos] == null) {
            return null;
        }
        return new LeaderboardPosition(leaderboard[pos]);    
    }
}
