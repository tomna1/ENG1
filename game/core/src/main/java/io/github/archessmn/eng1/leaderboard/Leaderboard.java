package io.github.archessmn.eng1.leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * This class stores the top 5 scores that have been achieved, the usernames of
 * the player who achieved it and any achievements earned in the same 
 * playthrough.
 */
public class Leaderboard {
    // The maximum amount of leaderboard positions the leaderboard can store.
    private int maxCount;
    // The actual amount of leaderboard positions there are.
    private int count;
    private LeaderboardPosition[] leaderboard;
    private String leaderboardPrefsDir;

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
     * Writes the current leaderboard to the file specified in constructor.
     */
    public void writeToFile() {
        Preferences prefs = Gdx.app.getPreferences(leaderboardPrefsDir);
        setCount();
        for (int i = 0; i < count; i++) {
            prefs.putString("pos"+Integer.toString(leaderboard[i].getPosition()), leaderboard[i].toString());
        }
        prefs.flush();
    }

    /**
     * Reads from the leaderboardFile and setups up the leaderboard accordingly.
     */
    private void readFromFile() {
        Preferences prefs = Gdx.app.getPreferences(leaderboardPrefsDir);
        String s;
        LeaderboardPosition record;
        for (int i = 0; i < maxCount; i++) {
            // Gets the string stored in the file, converts it to a
            // LeaderboardPosition object then stores it in the leaderboard.
            s = prefs.getString("pos"+Integer.toString(i), "default");
            if (s.equals("default")) continue;
            record = LeaderboardPosition.fromString(s);
            if (record == null) continue;
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
     * Returns how many valid LeaderboardPosition objects there are in the
     * leaderboard. If returns 3, that means 1st, 2nd and 3rd are all valid
     * but there are no positions beyond that even if the maxCount was greater.
     * @return Amount of valid leaderboard positions.
     */
    public int getCount() {
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
