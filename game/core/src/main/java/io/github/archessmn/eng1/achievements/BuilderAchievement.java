package io.github.archessmn.eng1.achievements;

import io.github.archessmn.eng1.WorldStats;

/**
 * This achievement is earned by building at least 10 buildings.
 */
public class BuilderAchievement extends AbstractAchievement {
    private WorldStats worldStats;
    
    public BuilderAchievement(AchievementManager manager, WorldStats worldStats) {
        super(manager, 1, 
        "Builder",
        "Earned by building at least 10 buildings.",
        "achievement_icons/icon_3.png");
        if (worldStats == null) throw new IllegalArgumentException("worldstats cannot be null");
        this.worldStats = worldStats;
    }

    public boolean checkIfAchieved() {
        if (worldStats.getTotalBuildingsPlaced() >= 10) return true;
        return false;
    }
}
