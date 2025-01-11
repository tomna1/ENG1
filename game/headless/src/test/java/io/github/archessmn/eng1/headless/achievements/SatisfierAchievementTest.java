package io.github.archessmn.eng1.headless.achievements;

import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.SatisfactionStats;
import io.github.archessmn.eng1.achievements.AchievementManager;
import io.github.archessmn.eng1.achievements.SatisfierAchievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SatisfierAchievementTest {
    final AchievementManager validManager = mock(AchievementManager.class);

    @Test
    public void defaultInit() {
        SatisfactionStats stats = mock(SatisfactionStats.class);

        SatisfierAchievement achievement = new SatisfierAchievement(validManager, stats);

        assertEquals(stats, achievement.getSatisfactionStats(), 
        "Satisfaction stats should be the same as defined in the constructor");
    }

    @Test
    public void throwsOnNullSatisfactionStats() {
        SatisfactionStats nullStats = null;

        assertThrows(IllegalArgumentException.class, () -> new SatisfierAchievement(validManager, nullStats),
        "Should throw IllegalArgumentException when satisfactionStats is null");
    }

    @Test
    public void achievementWhenSatisfactionAtLeastTen() {
        SatisfactionStats stats = mock(SatisfactionStats.class);
        when(stats.getCurrentSatisfaction()).thenReturn(10.0f);

        SatisfierAchievement achievement = new SatisfierAchievement(validManager, stats);

        assertEquals(true, achievement.checkIfAchieved(), 
        "Achievement should be achieved when satisfaction is 10");

        when(stats.getCurrentSatisfaction()).thenReturn(20.0f);
        assertEquals(true, achievement.checkIfAchieved(),
        "Achievement should be achieved when satisfaction is greater than 10");
    }

    @Test
    public void notAchievedWhenSatisfactionLessThanTen() {
        SatisfactionStats stats = mock(SatisfactionStats.class);
        when(stats.getCurrentSatisfaction()).thenReturn(9.9f);

        SatisfierAchievement achievement = new SatisfierAchievement(validManager, stats);

        assertEquals(false, achievement.checkIfAchieved(), 
        "Achievement should not be achieved if satisfaction is less than 10");

        when(stats.getCurrentSatisfaction()).thenReturn(5.0f);
        assertEquals(false, achievement.checkIfAchieved(), 
        "Achievement should not be achieved if satisfaction is less than 10");
    }
}
