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

    /**
     * @return Returns the username, score and achievements of the
     * record each separated by a colon. If username = "example", 
     * score = 235.7 and 2 achievements then output is 
     * "example:235.7:achievement1.toString(),achievement2.toString()".
     * If no achievements then the achievement section marked as "null".
     */
    public String toLeaderboardString() {
        String output = username + ":" + Float.toString(score) + ":";
        if (completedAchievements.size() == 0) {
            output = output + "null";
            return output;
        }
        for (int i = 0; i < completedAchievements.size(); i++) {
            if (output.endsWith(":") == false) {
                output = output + "~";
            }
            output = output + completedAchievements.get(i).toLeaderboardString();
        }
        return output;
    }

    /**
     * Will return a LeaderboardPosition object if a valid string is passed into
     * it. A valid string is one that was created using the {@link #toString()}
     * method.
     * @param string A string created using the {@link #toString()} method.
     * Cannot be null
     * @return The LeaderboardPosition object created from parsing through
     * the string.
     */
    static LeaderboardPosition fromLeaderboardString(String string) {
        if (string == null) throw new IllegalArgumentException("string cannot be null");
        
        String[] split = string.split(":");
        if (split.length != 3) {
            return null;
        }

        String username = split[0];

        // Calculates the score
        float score;
        try {
            score = Float.parseFloat(split[1]);
        } catch (NumberFormatException e) {
            return null;
        }

        // Calculate the achievements
        ArrayList<CompletedAchievement> achievements = new ArrayList<>();
        if (split[2].strip().equals("null")) {
            return new LeaderboardPosition(username, score, achievements);
        } else {
            String[] split2 = split[2].split("~");
            for (int i = 0; i < split2.length; i++) {
                CompletedAchievement achievement = CompletedAchievement.fromLeaderboardString(split2[i]);  
                if (achievement == null) continue;
                else achievements.add(achievement);  
            }
        }

        return new LeaderboardPosition(username, score, achievements);
    }
}
