package io.github.archessmn.eng1.leaderboard;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * This class stores the top scores that have been achieved, the usernames of
 * the player who achieved it and any achievements earned in the same 
 * playthrough.
 */
public class Leaderboard {
    // The maximum amount of leaderboard positions the leaderboard can store.
    private int maxCount;
    // The actual amount of leaderboard positions there are.
    private int count;
    private LeaderboardPosition[] leaderboard;
    private FileHandle leaderboardFileDir;

    /**
     * Creates a new leaderboard with 10 spaces and retreives the data from the
     * passed in fileDir.
     * @param leaderboardFileDir The file containing the leaderboard data.
     */
    //public Leaderboard(FileHandle leaderboardFileDir) {
        // this(leaderboardFileDir, 10);
    //}
    
    private Leaderboard(FileHandle leaderboardFileDir, int maxCount) {
        if (leaderboardFileDir == null) {
            throw new IllegalArgumentException("Leaderboard string file directory cannot be null.");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Leaderboard has to have at least 1 position.");
        }
        this.maxCount = maxCount;
        leaderboard = new LeaderboardPosition[count];
        this.leaderboardFileDir = leaderboardFileDir;
        setCount();
    }

    /**
     * Creates a leaderboard with random positions.
     */
    public Leaderboard() {
        ArrayList<CompletedAchievement> achievements1 = new ArrayList<>();
        achievements1.add(new CompletedAchievement(Gdx.files.internal("achievement_icons/icon_1.png"), "achievement 1"));
        achievements1.add(new CompletedAchievement(Gdx.files.internal("achievement_icons/icon_2.png"), "achievement 2"));
        
        this.maxCount = 10;
        leaderboard = new LeaderboardPosition[this.maxCount];
        
        float score = 1111.8f;
        float scoreDelta = 7.0f;
        for (int i = 0; i < 5; i++) {
            leaderboard[i] = new LeaderboardPosition(i, "random username", score, null);
            if (i == 2) {
                leaderboard[i] = new LeaderboardPosition(i, "random username", score, achievements1);
            }
            score -= scoreDelta;
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
