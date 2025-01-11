package io.github.archessmn.eng1.achievements;

import io.github.archessmn.eng1.SatisfactionStats;

/**
 * Earned by getting at least 10 total satisfaction
 */
public class SatisfierAchievement extends AbstractAchievement {
    SatisfactionStats satisfactionStats;
    
    public SatisfierAchievement(AchievementManager manager, SatisfactionStats satisfactionStats) {
        super(manager, 5, 
        "Satisfier",
        "Earned by getting at least 10 satisfaction.",
        "achievement_icons/icon_2.png",
        true);
        if (satisfactionStats == null) throw new IllegalArgumentException("Satisfaction stats cannot be null");
        this.satisfactionStats = satisfactionStats;
    }

    public boolean checkIfAchieved() {
        if (satisfactionStats.getCurrentSatisfaction() >= 10) return true;
        return false;
    }

    public SatisfactionStats getSatisfactionStats() {
        return satisfactionStats;
    }
}
