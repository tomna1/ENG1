package io.github.archessmn.eng1.achievements;

import io.github.archessmn.eng1.WorldStats;

/**
 * This achievement is earned by placing the maximum amount of buildings possible.
 */
public class JamPackedAchievement extends AbstractAchievement {
    private WorldStats worldStats;
    
    public JamPackedAchievement(AchievementManager manager, WorldStats worldStats) {
        super(manager, 3, 
        "Jam Packed",
        "Earned by placing the maximum number of buildings.",
        "achievement_icons/icon_3.png",
        true);
        if (worldStats == null) throw new IllegalArgumentException("worldstats cannot be null");
        this.worldStats = worldStats;
    }

    public boolean checkIfAchieved() {
        if (worldStats.getTotalBuildingsPlaced() == WorldStats.MAX_POSSIBLE_BUILDINGS) return true;
        return false;
    }
}
