package io.github.archessmn.eng1.achievements;

/**
 * Manages all achievements in the game.
 */
public class AchievementManager {
    private AbstractAchievement[] uncompletedAchievements;
    // private CompletedAchievement[] completedAchievements;
    private float deltaTime = 0.0f;
    private float timeBetweenChecks;
    private boolean isTimeChecksEnabled;

    public AchievementManager(float timeBetweenChecks) {
        if (timeBetweenChecks <= 0) isTimeChecksEnabled = false;
        else isTimeChecksEnabled = true;
        this.timeBetweenChecks = timeBetweenChecks;
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
                checkAllAchievements();
            }
        }
    }

    /**
     * Forcefully checks all the achievements and removes them from uncompleted
     * achievements and adds them to completedAchievements if they have been
     * completed.
     */
    private void checkAllAchievements() {
        AbstractAchievement achievement;
        for (int i = 0; i < uncompletedAchievements.length; i++) {
            achievement = uncompletedAchievements[i];
            if (achievement != null && achievement.checkIfAchieved() == true) {
                onAchievementCompletion(achievement);
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
        uncompletedAchievements[achievement.getID()] = null;
        // get the completed achievements and add it to the arrays of completed achievements.
    }
}
