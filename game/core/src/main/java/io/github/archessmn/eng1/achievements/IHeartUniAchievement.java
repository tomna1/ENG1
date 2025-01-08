package io.github.archessmn.eng1.achievements;

import io.github.archessmn.eng1.SatisfactionStats;

/**
 * This achievement is earned by  maintaining a student satisfaction of over
 * 80% for 3 straight minutes.
 */
public class IHeartUniAchievement extends AbstractAchievement {
    private SatisfactionStats satisfactionStats;
    
    public IHeartUniAchievement(AchievementManager manager, SatisfactionStats satisfactionStats) {
        super(manager, 2, 
        "I heart Uni",
        "Earned by maintaining student satisfaction over 80% for over 3 minutes.",
        "achievement_icons/icon_1.png");
        if (satisfactionStats == null) throw new IllegalArgumentException("satisfaction stats cannot be null.");
        this.satisfactionStats = satisfactionStats;
    }

    public boolean checkIfAchieved() {
        if (satisfactionStats.getPercentageSatisfaction() > 80.0f) return true;
        return false;
    }
}
