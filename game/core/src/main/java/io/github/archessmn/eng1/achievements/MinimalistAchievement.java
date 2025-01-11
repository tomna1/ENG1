package io.github.archessmn.eng1.achievements;

import io.github.archessmn.eng1.core.WorldStats;

/**
 * This achievement is earned by placing a total of 5 building at the end of the game
 * no more, no less.
 */
public class MinimalistAchievement extends AbstractAchievement {
    private WorldStats worldStats;
    
    public MinimalistAchievement(AchievementManager manager, WorldStats worldStats) {
        super(manager, 4, 
        "Minimalist",
        "Earned by placing a total of 5 buildings.",
        "achievement_icons/icon_2.png",
        true);
        if (worldStats == null) throw new IllegalArgumentException("worldstats cannot be null");
        this.worldStats = worldStats;
    }

    public boolean checkIfAchieved() {
        if (worldStats.getTotalBuildingsPlaced() == 5) return true;
        return false;
    }
}
