package io.github.archessmn.eng1.leaderboard;

import java.util.ArrayList;

/**
 * This class holds all the necessary information that is needed for each 
 * position on the leaderboard.
 */
public class LeaderboardPosition implements Comparable<LeaderboardPosition> {
    private String username;
    private float score;
    private ArrayList<CompletedAchievement> completedAchievements;

    /**
     * @param username The username of the player. Cannot be null.
     * @param score The score the player got.
     * @param achievements The achievement the player got. If null then no
     * achievements were achieved.
     */
    public LeaderboardPosition(String username, float score, ArrayList<CompletedAchievement> achievements) {
        if (username == null) throw new IllegalArgumentException("username cannot be null");
        this.username = username;
        this.score = score;
        this.completedAchievements = achievements;
        if (achievements == null) {
            this.completedAchievements = new ArrayList<>();
        }
    }

    public LeaderboardPosition(LeaderboardPosition pos) {
        if (pos == null) throw new IllegalArgumentException("argument cannot be null");
        this.username = pos.username;
        this.score = pos.score;
        this.completedAchievements = new ArrayList<>(pos.completedAchievements);
    }

    // ====SHOULD NOT BE USED ONLY FOR JSON SERIALISATION===
    public LeaderboardPosition() {
        completedAchievements = new ArrayList<>();
    }

    /**
     * Adds this completed achievement to the list of completed achievement. Will
     * not add the achievement if it is already in the list.
     * @param achievement
     * @return true if added and false if not.
     */
    public boolean addCompletedAchievement(CompletedAchievement achievement) {       
        if (completedAchievements.contains(achievement)) return false;
        this.completedAchievements.add(new CompletedAchievement(achievement));
        return true;
    }

    /**
     * Returns the username of the player who played this game.
     * @return Username of player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the score this player got at the end of their game.
     * @return Score achieved at the end of the game.
     */
    public float getScore() {
        return score;
    }

    /**
     * Returns a deep copy of the achievements that were earned during this game.
     * If the player earned no achievements throughout the game then this will
     * have a size of 0. It will never be null.
     * @return Achievements earned throughout the game.
     */
    public ArrayList<CompletedAchievement> getAchievements() {
        return new ArrayList<>(completedAchievements);
    }

    public CompletedAchievement getAchievement(int index) {
        if (index < 0 || index >= completedAchievements.size()) return null;
        return completedAchievements.get(index);
    }

    public int getAchievementCount() {
        return completedAchievements.size();
    }

    @Override
    public int compareTo(LeaderboardPosition position) {
        return ((int)this.score) - ((int)position.score);
    }
}
