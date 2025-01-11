package io.github.archessmn.eng1.headless.achievements;

import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.achievements.AbstractAchievement;
import io.github.archessmn.eng1.achievements.AchievementManager;
import io.github.archessmn.eng1.headless.AbstractHeadlessGdxTest;
import io.github.archessmn.eng1.leaderboard.CompletedAchievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class AbstractAchievementTest extends AbstractHeadlessGdxTest {
    final int validID = 1;
    final String validTitle = "title";
    final String validDescription = "description";
    final String validIconPath = "tests/achievement_icons/icon_1.png";
    final boolean validCheckOnlyAtEnd = false;
    
    @Test
    public void defaultInit() {
        AchievementManager manager = mock(AchievementManager.class);
        
        AbstractAchievement achievement = new AbstractAchievement(manager, validID,
        validTitle, validDescription,
        validIconPath, validCheckOnlyAtEnd) {
            @Override
            public boolean checkIfAchieved() {
                return false;
            }
        };

        assertEquals(validID, achievement.getID(), 
        "ID should be as defined in the constructor");
        assertEquals(validTitle, achievement.getTitle(), 
        "title should be as defined in the constructor.");
        assertEquals(validDescription, achievement.getDescription(), 
        "description should be as defined in the constructor");
        assertEquals(validIconPath, achievement.getIconPath(), 
        "IconPath should be as defined in the constructor");
        assertEquals(validCheckOnlyAtEnd, achievement.getOnlyCheckAtEnd(), 
        "onlyCheckAtEnd should be as defined in the constructor");
    }

    @Test
    public void throwsOnNullManagerInit() {
        AchievementManager manager = null;

        assertThrows(IllegalArgumentException.class, () -> new AbstractAchievement(manager, validID,
        validTitle, validDescription,
        validIconPath, validCheckOnlyAtEnd) {
            @Override
            public boolean checkIfAchieved() {
                return false;
            }
        });
    }

    @Test
    public void throwsOnNullTitleInit() {
        AchievementManager manager = mock(AchievementManager.class);
        String nullTitle = null;

        assertThrows(IllegalArgumentException.class, () -> new AbstractAchievement(manager, validID,
        nullTitle, validDescription,
        validIconPath, validCheckOnlyAtEnd) {
            @Override
            public boolean checkIfAchieved() {
                return false;
            }
        });
    }

    @Test
    public void throwsOnEmptyTitleInit() {
        AchievementManager manager = mock(AchievementManager.class);
        String emptyTitle = "";

        assertThrows(IllegalArgumentException.class, () -> new AbstractAchievement(manager, validID,
        emptyTitle, validDescription,
        validIconPath, validCheckOnlyAtEnd) {
            @Override
            public boolean checkIfAchieved() {
                return false;
            }
        });
    }


    @Test
    public void throwsOnNullDescriptionInit() {
        AchievementManager manager = mock(AchievementManager.class);
        String nullDescription = null;

        assertThrows(IllegalArgumentException.class, () -> new AbstractAchievement(manager, validID,
        validTitle, nullDescription,
        validIconPath, validCheckOnlyAtEnd) {
            @Override
            public boolean checkIfAchieved() {
                return false;
            }
        });
    }

    @Test
    public void throwsOnEmptyDescriptionInit() {
        AchievementManager manager = mock(AchievementManager.class);
        String emptyDescription = "";

        assertThrows(IllegalArgumentException.class, () -> new AbstractAchievement(manager, validID,
        validTitle, emptyDescription,
        validIconPath, validCheckOnlyAtEnd) {
            @Override
            public boolean checkIfAchieved() {
                return false;
            }
        });
    }

    @Test
    public void throwsOnNullIconPathInit() {
        AchievementManager manager = mock(AchievementManager.class);
        String nullIconPath = null;

        assertThrows(IllegalArgumentException.class, () -> new AbstractAchievement(manager, validID,
        validTitle, validDescription,
        nullIconPath, validCheckOnlyAtEnd) {
            @Override
            public boolean checkIfAchieved() {
                return false;
            }
        });
    }

    @Test
    public void throwsOnEmptyIconPathInit() {
        AchievementManager manager = mock(AchievementManager.class);
        String emptyIconPath = "";

        assertThrows(IllegalArgumentException.class, () -> new AbstractAchievement(manager, validID,
        validTitle, validDescription,
        emptyIconPath, validCheckOnlyAtEnd) {
            @Override
            public boolean checkIfAchieved() {
                return false;
            }
        });
    }

    @Test
    public void returnValidCompletedAchievement() {
        AchievementManager manager = mock(AchievementManager.class);

        AbstractAchievement achievement = new AbstractAchievement(manager, validID,
        validTitle, validDescription,
        validIconPath, validCheckOnlyAtEnd) {
            @Override
            public boolean checkIfAchieved() {
                return false;
            }
        };

        CompletedAchievement completedAchievement = achievement.getCompletedAchievement();
        assertEquals(validTitle, completedAchievement.getTitle(), 
        "title should be the same completedAchievement and achievement");
        assertEquals(validDescription, completedAchievement.getDescription(), 
        "title should be the same completedAchievement and achievement");
        assertEquals(validIconPath, completedAchievement.getIconPath(), 
        "title should be the same completedAchievement and achievement");
    }
}
