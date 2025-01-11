package io.github.archessmn.eng1.headless.achievements;

import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.SatisfactionStats;
import io.github.archessmn.eng1.achievements.AchievementManager;
import io.github.archessmn.eng1.achievements.IHeartUniAchievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IHeartUniAchievementTest {
    final AchievementManager manager = mock(AchievementManager.class);

    @Test
    public void defaultInit() {
        SatisfactionStats stats = mock(SatisfactionStats.class);
        IHeartUniAchievement achievement = new IHeartUniAchievement(manager, stats);
        assertEquals(stats, achievement.getSatisfactionStats(), 
        "Satisfaction stats should be the same as defined in constructor");
    }

    @Test
    public void throwsOnNullSatisfactionStatsInit() {
        SatisfactionStats nullStats = null;
        assertThrows(IllegalArgumentException.class, () -> new IHeartUniAchievement(manager, nullStats), 
        "Should throw IllegalArgumentException when satisfactionStats is null");
    }

    @Test
    public void achievedReturnsTrueUponCondition() {
        SatisfactionStats stats = mock(SatisfactionStats.class);
        when(stats.timeSinceGreaterThanPercent(80.0f, 180)).thenReturn(180.0f);
        IHeartUniAchievement achievement = new IHeartUniAchievement(manager, stats);
        assertEquals(true, achievement.checkIfAchieved(), 
        "achievement should have been achieved when satisfaction has been above 80.0f for 3 mins");

        when(stats.timeSinceGreaterThanPercent(80.0f, 180)).thenReturn(300.0f);
        assertEquals(true, achievement.checkIfAchieved(), 
        "achievement should have been achieved when satisfaction has been above 80.0f for more than 3 mins");
    }

    @Test
    public void achievedReturnsFalseWhenNotMet() {
        SatisfactionStats stats = mock(SatisfactionStats.class);
        when(stats.timeSinceGreaterThanPercent(80.0f, 180)).thenReturn(179.0f);
        IHeartUniAchievement achievement = new IHeartUniAchievement(manager, stats);
        assertEquals(false, achievement.checkIfAchieved(), 
        "achievement should not have been achieved when satisfaction has been above 80.0f for less than 3 mins");

        when(stats.timeSinceGreaterThanPercent(80.0f, 180)).thenReturn(100.0f);
        assertEquals(false, achievement.checkIfAchieved(), 
        "achievement should not have been achieved when satisfaction has been above 80.0f for less than 3 mins");
    }
}
