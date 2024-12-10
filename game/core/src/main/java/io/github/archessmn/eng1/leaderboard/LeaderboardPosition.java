package io.github.archessmn.eng1.leaderboard;

import java.util.ArrayList;

/**
 * This class holds all the necessary information that is needed for each 
 * position on the leaderboard.
 */
public class LeaderboardPosition {
    // If 0 that means 1st. If 5 then 6th.
    private int position;
    private String username;
    private float score;
    private ArrayList<CompletedAchievement> completedAchievements;

    public LeaderboardPosition(int position, String username, float score, ArrayList<CompletedAchievement> achievements) {
        this.position = position;
        this.username = username;
        this.score = score;
        this.completedAchievements = achievements;
        if (this.completedAchievements == null) {
            this.completedAchievements = new ArrayList<>();
        }
    }

    public LeaderboardPosition(LeaderboardPosition pos) {
        this.username = pos.username;
        this.score = pos.score;
        this.completedAchievements = new ArrayList<>(completedAchievements);
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

    public int getPosition() {
        return position;
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
}
