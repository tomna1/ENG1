package io.github.archessmn.eng1.headless.achievements;

import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.core.WorldStats;
import io.github.archessmn.eng1.achievements.AchievementManager;
import io.github.archessmn.eng1.achievements.JamPackedAchievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JamPackedAchievementTest {
    final AchievementManager validManager = mock(AchievementManager.class);
    
    @Test
    public void defaultInit() {
        WorldStats worldStats = mock(WorldStats.class);

        JamPackedAchievement achievement = new JamPackedAchievement(validManager, worldStats);
        assertEquals(worldStats, achievement.getWorldStats(), 
        "worldStats should be the same as defined in the constructor");
    }

    @Test
    public void throwsOnNullWorldStats() {
        WorldStats nullWorldStats = null;

        assertThrows(IllegalArgumentException.class, () -> new JamPackedAchievement(validManager, nullWorldStats), 
        "Should throw IllegalArgumentException when worldStats is null");
    }

    @Test
    public void achievedWhenTotalBuildingsPlacedEqualsMax() {
        WorldStats worldStats = mock(WorldStats.class);
        when(worldStats.getTotalBuildingsPlaced()).thenReturn(WorldStats.MAX_POSSIBLE_BUILDINGS);

        JamPackedAchievement achievement = new JamPackedAchievement(validManager, worldStats);
        
        assertEquals(true, achievement.checkIfAchieved(), 
        "checkIfAchieved should return true when max possible buildings have been built");
    }

    @Test
    public void notAchievedWhenTotalBuildingPlacedDoesntEqualMax() {
        WorldStats worldStats = mock(WorldStats.class);
        when(worldStats.getTotalBuildingsPlaced()).thenReturn(WorldStats.MAX_POSSIBLE_BUILDINGS-1);

        JamPackedAchievement achievement = new JamPackedAchievement(validManager, worldStats);

        assertEquals(false, achievement.checkIfAchieved(), 
        "checkIfAchieved should return false when max possible buildings have not been built");

        when(worldStats.getTotalBuildingsPlaced()).thenReturn(WorldStats.MAX_POSSIBLE_BUILDINGS+1);
        assertEquals(false, achievement.checkIfAchieved(), 
        "checkIfAchieved should return false when max possible buildings have not been built");
    }
}
