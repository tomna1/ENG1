package io.github.archessmn.eng1.achievements;

import java.util.ArrayList;

import io.github.archessmn.eng1.core.WorldStats;
import io.github.archessmn.eng1.leaderboard.CompletedAchievement;
import io.github.archessmn.eng1.satisfaction.SatisfactionStats;

/**
 * Manages all achievements in the game.
 */
public class AchievementManager {
    private WorldStats worldStats; // References to worldstats to make it easy to create achievements.
    private SatisfactionStats satisfactionStats; // Reference to satisfaction stats to make it easy to create achievements.
    
    private ArrayList<AbstractAchievement> uncompletedRuntimeAchievements = new ArrayList<>();
    private ArrayList<AbstractAchievement> uncompletedEndAchievements = new ArrayList<>();
    private ArrayList<CompletedAchievement> completedAchievements = new ArrayList<>();
    private float deltaTime = 0.0f; // Used for managing time between update calls.
    private float timeBetweenChecks;
    private boolean isTimeChecksEnabled;

    public AchievementManager(WorldStats worldStats, SatisfactionStats satisfactionStats, float timeBetweenChecks) {
        if (worldStats == null) throw new IllegalArgumentException("World stats cannot be null");
        if (satisfactionStats == null) throw new IllegalArgumentException("satisfaction stats cannot be null");
        this.worldStats = worldStats;
        this.satisfactionStats = satisfactionStats;
        
        if (timeBetweenChecks <= 0) isTimeChecksEnabled = false;
        else isTimeChecksEnabled = true;
        this.timeBetweenChecks = timeBetweenChecks;

        createAchievements();
    }

    private void createAchievements() {
        addAchievement(new BuilderAchievement(this, worldStats));
        addAchievement(new IHeartUniAchievement(this, satisfactionStats));
        addAchievement(new JamPackedAchievement(this, worldStats));
        addAchievement(new MinimalistAchievement(this, worldStats));
        addAchievement(new SatisfierAchievement(this, satisfactionStats));
    }

    private void addAchievement(AbstractAchievement achievement) {
        if (achievement.getOnlyCheckAtEnd()) uncompletedEndAchievements.add(achievement);
        else uncompletedRuntimeAchievements.add(achievement);
    }

    /**
     * Should be called every frame. Will check if all uncompleted achievements
     * have been achievements forcefully if timeBetweenChecks has passed.
     * @param deltaTime
     */
    public void update(float deltaTime) {
        if (isTimeChecksEnabled) {
            this.deltaTime += deltaTime;
            if (this.deltaTime >= timeBetweenChecks) {
                checkAllRuntimeAchievements();
                this.deltaTime = 0.0f;
            }
        }
    }

    /**
     * This method should be called when the game ends. Will check all achievements with
     * {@link AbstractAchievement#getOnlyCheckAtEnd()} being true.  
     */
    public void onGameEnd() {
        checkAllRuntimeAchievements();
        checkAllEndAchievements();
    }

    /**
     * Forcefully checks all the achievements and removes them from uncompleted
     * achievements and adds them to completedAchievements if they have been
     * completed.
     */
    private void checkAllRuntimeAchievements() {
        AbstractAchievement achievement;
        for (int i = 0; i < uncompletedRuntimeAchievements.size(); i++) {
            achievement = uncompletedRuntimeAchievements.get(i);
            if (achievement != null && achievement.checkIfAchieved() == true) {
                onAchievementCompletion(achievement);
                i--;
            }
        }
    }

    private void checkAllEndAchievements() {
        AbstractAchievement achievement;
        for (int i = 0; i < uncompletedEndAchievements.size(); i++) {
            achievement = uncompletedEndAchievements.get(i);
            if (achievement != null && achievement.checkIfAchieved() == true) {
                onAchievementCompletion(achievement);
                i--;
            }
        }
    }

    /**
     * This should be called by an achievement when it has been completed. It
     * will remove if from the list of uncompleted achievements and add it to
     * the list of completed achievements.
     * @param achievement
     */
    public void onAchievementCompletion(AbstractAchievement achievement) {
        if (achievement.checkIfAchieved() == false) return;
        removeAchievement(achievement);
        completedAchievements.add(achievement.getCompletedAchievement());
    }

    /**
     * Removes the achievement from either the list of end achievements or runtime
     * achievements
     * @param achievement The achievement to remove.
     * @return true if successful and false if not.
     */
    private boolean removeAchievement(AbstractAchievement achievement) {
        // Checks all runtime achievement for achievement with id.
        for (int i = 0; i < uncompletedRuntimeAchievements.size(); i++) {
            if (uncompletedRuntimeAchievements.get(i).getID() == achievement.getID()) {
                uncompletedRuntimeAchievements.remove(i);
                return true;
            }
        }
        // Checks all end achievement for achievement with id.
        for (int i = 0; i < uncompletedEndAchievements.size(); i++) {
            if (uncompletedEndAchievements.get(i).getID() == achievement.getID()) {
                uncompletedEndAchievements.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<CompletedAchievement> getCompletedAchievements() {
        return completedAchievements;
    }

    public int getCompletedAchievementCount() {
        return completedAchievements.size();
    }
}
