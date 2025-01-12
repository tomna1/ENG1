package io.github.archessmn.eng1.headless.achievements;

import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.WorldStats;
import io.github.archessmn.eng1.achievements.AchievementManager;
import io.github.archessmn.eng1.achievements.BuilderAchievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BuilderAchievementTest {
    @Test
    public void defaultInit() {
        AchievementManager manager = mock(AchievementManager.class);
        WorldStats worldStats = mock(WorldStats.class);

        BuilderAchievement achievement = new BuilderAchievement(manager, worldStats);

        assertEquals(worldStats, achievement.getWorldStats(),
        "worldStats should be as defined in the constructor");
    }

    @Test
    public void throwsOnNullWorldStatsInit() {
        AchievementManager manager = mock(AchievementManager.class);
        WorldStats nullWorldStats = null;

        assertThrows(IllegalArgumentException.class, () -> new BuilderAchievement(manager, nullWorldStats), 
        "Should throw IllegalArgumentException on null worldStats");
    }

    @Test
    public void achievedWhenTotalBuildingsPlacedGreaterThanTen() {
        AchievementManager manager = mock(AchievementManager.class);
        WorldStats worldStats = mock(WorldStats.class);
        when(worldStats.getTotalBuildingsPlaced()).thenReturn(10);

        BuilderAchievement achievement = new BuilderAchievement(manager, worldStats);

        assertEquals(true, achievement.checkIfAchieved(), 
        "Achievement should be achievement when totalBuildingsPlaced at least 10.");

        when(worldStats.getTotalBuildingsPlaced()).thenReturn(20);
        assertEquals(true, achievement.checkIfAchieved(), 
        "Achievement should be achievement when totalBuildingsPlaced at least 10.");
    }

    @Test
    public void notAchievedWhenTotalBuildingsLessThanTen() {
        AchievementManager manager = mock(AchievementManager.class);
        WorldStats worldStats = mock(WorldStats.class);
        when(worldStats.getTotalBuildingsPlaced()).thenReturn(9);

        BuilderAchievement achievement = new BuilderAchievement(manager, worldStats);

        assertEquals(false, achievement.checkIfAchieved(), 
        "Achievement should be achievement when totalBuildingsPlaced at least 10.");

        when(worldStats.getTotalBuildingsPlaced()).thenReturn(5);
        assertEquals(false, achievement.checkIfAchieved(), 
        "Achievement should be achievement when totalBuildingsPlaced at least 10.");
    }
}
