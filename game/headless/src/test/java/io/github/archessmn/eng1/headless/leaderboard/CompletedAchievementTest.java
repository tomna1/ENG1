package io.github.archessmn.eng1.headless.leaderboard;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import io.github.archessmn.eng1.headless.AbstractHeadlessGdxTest;
import io.github.archessmn.eng1.leaderboard.CompletedAchievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompletedAchievementTest extends AbstractHeadlessGdxTest {
    @Test
    public void defaultInit() {
        final String validPath = "tests/achievement_icons/icon_1.png";
        final FileHandle validFile = Gdx.files.internal(validPath);
        if (validFile.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validTitle = "title1";
        final String validDescription = "description1";

        final CompletedAchievement achievement = new CompletedAchievement(validPath, validTitle, validDescription);

        assertEquals(validPath, achievement.getIconPath(), 
            "Achievement must return the same path as that defined in its constructor.");
        assertEquals(validFile, achievement.getIconFile(), 
            "Achievement must return the same path as that defined in its constructor.");
        assertEquals(validDescription, achievement.getDescription(), 
            "Achievement must return the same desciption as that defined in the constructor.");
        assertEquals(validTitle, achievement.getTitle(), 
            "Achievement must return the same title as defined in the constructor.");
    }

    @Test
    public void nullDescriptionInit() {
        final String validPath = "tests/achievement_icons/icon_1.png";
        final FileHandle validFile = Gdx.files.internal(validPath);
        if (validFile.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validTitle = "title1";
        final String nullDescription = null;

        final CompletedAchievement achievement = new CompletedAchievement(validPath, validTitle, nullDescription);

        assertEquals("", achievement.getDescription(), 
            "Description should be an empty string if null in constructor.");
    }

    @Test
    public void throwsOnNullTitleInit() {
        final String validPath = "tests/achievement_icons/icon_1.png";
        final FileHandle validFile = Gdx.files.internal(validPath);
        if (validFile.exists() == false) throw new IllegalArgumentException("file must exist");
        final String nullTitle = null;
        final String validDescription = "description1";

        assertThrows(IllegalArgumentException.class, () -> new CompletedAchievement(validPath, nullTitle, validDescription),
        "Should throw IllegalArgumentException on null title");
    }

    @Test
    public void throwsOnEmptyTitleInit() {
        final String validPath = "tests/achievement_icons/icon_1.png";
        final FileHandle validFile = Gdx.files.internal(validPath);
        if (validFile.exists() == false) throw new IllegalArgumentException("file must exist");
        final String emptyTitle = "";
        final String validDescription = "description1";

        assertThrows(IllegalArgumentException.class, () -> new CompletedAchievement(validPath, emptyTitle, validDescription),
        "Should throw IllegalArgumentException on null title");
    }

    @Test
    public void throwsOnNullFilePathInit() {
        final String nullPath = null;
        final String validDescription = "description1";
        final String validTitle = "title1";

        assertThrows(IllegalArgumentException.class, () -> new CompletedAchievement(nullPath, validTitle, validDescription),
        "Should throw IllegalArgumentException on null path");
    }

    @Test
    public void throwsOnInvalidFilePath() {
        final String invalidPath = "tests/doesnt_exist";
        final FileHandle invalidFile = Gdx.files.internal(invalidPath);
        if (invalidFile.exists()) throw new IllegalArgumentException("FilePath should not exist");
        final String validTitle = "title1";
        final String validDescription = "desc";

        assertThrows(IllegalArgumentException.class, () -> new CompletedAchievement(invalidPath, validTitle, validDescription),
        "Should throw IllegalArgumentException on invalid path");
    }

    @Test
    public void validCopyConstructor() {
        final String validPath = "tests/achievement_icons/icon_1.png";
        final FileHandle validFile = Gdx.files.internal(validPath);
        if (validFile.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validTitle = "title1";
        final String validDescription = "description1";

        final CompletedAchievement achievement = new CompletedAchievement(validPath, validTitle, validDescription);
        final CompletedAchievement achievement2 = new CompletedAchievement(achievement);

        assertEquals(achievement.getDescription(), achievement2.getDescription(), 
            "description should be the same in both objects.");
        assertEquals(achievement.getTitle(), achievement2.getTitle(), 
            "title should be the same in both objects.");
        assertEquals(achievement.getIconPath(), achievement2.getIconPath(), 
            "Icon path should be the same in both objects.");
    }

    @Test
    public void throwsOnNullAchievementCopyConstructor() {
        final CompletedAchievement nullAchievement = null;

        assertThrows(IllegalArgumentException.class, () -> new CompletedAchievement(nullAchievement),
            "Should throw IllegalArgumentException on null achievement.");
    }

    @Test
    public void achievementEqualsToItself() {
        final String validPath = "tests/achievement_icons/icon_1.png";
        final String validTitle = "title1";
        final String validDescription = "description1";
        final CompletedAchievement achievement = new CompletedAchievement(validPath, validTitle, validDescription);
        
        assertEquals(achievement, achievement, 
            "Achievement should be equal to itself.");
    }

    @Test
    public void achievementEqualsToSameAchievement() {
        final String validPath = "tests/achievement_icons/icon_1.png";
        final String validTitle = "title1";
        final String validDescription = "description1";
        final CompletedAchievement achievement1 = new CompletedAchievement(validPath, validTitle, validDescription);
        final CompletedAchievement achievement2 = new CompletedAchievement(validPath, validTitle, validDescription);

        assertEquals(achievement1, achievement2,
            "achievement should be equal to an achievement with same attributes.");
    }

    @Test
    public void achievementsWithDifferentPathsNotEqual() {
        final String validPath1 = "tests/achievement_icons/icon_1.png";
        final String validPath2 = "tests/achievement_icons/icon_2.png";
        final String validTitle = "title1";
        final String validDescription = "description1";
        final CompletedAchievement achievement1 = new CompletedAchievement(validPath1, validTitle, validDescription);
        final CompletedAchievement achievement2 = new CompletedAchievement(validPath2, validTitle, validDescription);

        assertNotEquals(achievement1, achievement2,
            "Achievements with different paths should not be equal");
    }

    @Test
    public void achievementsWithDifferentTitlesNotEqual() {
        final String validPath = "tests/achievement_icons/icon_1.png";
        final String validTitle1 = "title1";
        final String validTitle2 = "title2";
        final String validDescription = "description1";
        final CompletedAchievement achievement1 = new CompletedAchievement(validPath, validTitle1, validDescription);
        final CompletedAchievement achievement2 = new CompletedAchievement(validPath, validTitle2, validDescription);

        assertNotEquals(achievement1, achievement2,
            "Achievements with different titles should not be equal");
    }

    @Test
    public void achievementsWithDifferentDescriptionsNotEqual() {
        final String validPath = "tests/achievement_icons/icon_1.png";
        final String validTitle = "title1";
        final String validDescription1 = "description1";
        final String validDescription2 = "description2";
        final CompletedAchievement achievement1 = new CompletedAchievement(validPath, validTitle, validDescription1);
        final CompletedAchievement achievement2 = new CompletedAchievement(validPath, validTitle, validDescription2);

        assertNotEquals(achievement1, achievement2,
            "Achievements with different descriptions should not be equal");
    }
}
