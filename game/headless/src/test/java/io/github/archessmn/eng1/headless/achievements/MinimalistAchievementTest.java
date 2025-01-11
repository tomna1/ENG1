package io.github.archessmn.eng1.headless.achievements;

import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.WorldStats;
import io.github.archessmn.eng1.achievements.AchievementManager;
import io.github.archessmn.eng1.achievements.MinimalistAchievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MinimalistAchievementTest {
    final AchievementManager validManager = mock(AchievementManager.class);

    @Test
    public void defaultInit() {
        WorldStats worldStats = mock(WorldStats.class);

        MinimalistAchievement achievement = new MinimalistAchievement(validManager, worldStats);

        assertEquals(worldStats, achievement.getWorldStats(), 
        "worldStats should be the same as defined in the constructor");
    }

    @Test
    public void throwOnNullWorldStatsInit() {
        WorldStats nullWorldStats = null;

        assertThrows(IllegalArgumentException.class, () -> new MinimalistAchievement(validManager, nullWorldStats), 
        "Should throw IllegalArgumentException when worldStats is null");
    }

    @Test
    public void achievedWhenTotalBuildingPlacedEqualsFive() {
        WorldStats worldStats = mock(WorldStats.class);
        when(worldStats.getTotalBuildingsPlaced()).thenReturn(5);

        MinimalistAchievement achievement = new MinimalistAchievement(validManager, worldStats);

        assertEquals(true, achievement.checkIfAchieved(), 
        "Should return true when total building placed equals 5");
    }

    @Test
    public void notAchievedWhenTotalBuildPlacedNotEqualsFive() {
        WorldStats worldStats = mock(WorldStats.class);
        when(worldStats.getTotalBuildingsPlaced()).thenReturn(4);

        MinimalistAchievement achievement = new MinimalistAchievement(validManager, worldStats);

        assertEquals(false, achievement.checkIfAchieved(), 
        "Achievement should not be achieved if total building placed doesnt equal 5");

        when(worldStats.getTotalBuildingsPlaced()).thenReturn(6);
        assertEquals(false, achievement.checkIfAchieved(), 
        "Achievement should not be achieved if total building placed doesnt equal 5");
    }
}
