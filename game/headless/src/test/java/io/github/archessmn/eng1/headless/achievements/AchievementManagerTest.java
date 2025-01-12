package io.github.archessmn.eng1.headless.achievements;

import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.satisfaction.SatisfactionStats;
import io.github.archessmn.eng1.core.WorldStats;
import io.github.archessmn.eng1.achievements.AchievementManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class AchievementManagerTest {
    final WorldStats validWorldStats = mock(WorldStats.class);
    final SatisfactionStats validSatisfactionStats = mock(SatisfactionStats.class);
    final float validTimeBetweenChecks = 1.0f;

    @Test
    public void defaultInit() {
        AchievementManager manager = new AchievementManager(validWorldStats, validSatisfactionStats, validTimeBetweenChecks);
        assertEquals(validTimeBetweenChecks, manager.getTimeBetweenChecks(), 
        "timeBetweenChecks should be as defined in the constructor");
        assertEquals(true, manager.areTimeChecksEnabled(), 
        "Time checks should be enabled is timeBetweenChecks is greater than 0");

    }

    @Test
    public void timeBetweenChecksZeroOrLess() {
        float validTimeBetweenChecks = 0.0f;
        AchievementManager manager = new AchievementManager(validWorldStats, validSatisfactionStats, validTimeBetweenChecks);
        assertEquals(false, manager.areTimeChecksEnabled(), 
        "Time checks should not be enabled if timeBetweenChecks is 0");

        validTimeBetweenChecks = -5.0f;
        manager = new AchievementManager(validWorldStats, validSatisfactionStats, validTimeBetweenChecks);
        assertEquals(false, manager.areTimeChecksEnabled(), 
        "Time checks should not be enabled if timeBetweenChecks is less than 0");
    }

    @Test
    public void throwsOnNullWorldStatsInit() {
        WorldStats nullWorldStats = null;
        assertThrows(IllegalArgumentException.class, () -> new AchievementManager(nullWorldStats, validSatisfactionStats, validTimeBetweenChecks), 
        "Should throw IllegalArgumentException when worldStats is null");
    }

    @Test
    public void throwsOnNullSatisfactionStatsInit() {
        SatisfactionStats nullSatisfactionStats = null;
        assertThrows(IllegalArgumentException.class, () -> new AchievementManager(validWorldStats, nullSatisfactionStats, validTimeBetweenChecks), 
        "Should throw IllegalArgumentException when satisfactionStats is null");
    }
}
