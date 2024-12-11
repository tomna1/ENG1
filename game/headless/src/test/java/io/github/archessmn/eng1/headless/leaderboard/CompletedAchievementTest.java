package io.github.archessmn.eng1.headless.leaderboard;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import io.github.archessmn.eng1.headless.AbstractHeadlessGdxTest;
import io.github.archessmn.eng1.leaderboard.CompletedAchievement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompletedAchievementTest extends AbstractHeadlessGdxTest {
    @Test
    public void defaultInit() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validDescription = "description1";

        CompletedAchievement achievement = new CompletedAchievement(validPath, validDescription);
        assertEquals(validPath, achievement.getIconPath(), 
            "Achievement must return the same path as that defined in its constructor.");
        assertEquals(validDescription, achievement.getDescription(), 
            "Achievement must return the same desciption as that defined in the constructor.");
    }

    @Test
    public void nullDescriptionInit() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String nullDescription = null;

        CompletedAchievement achievement = new CompletedAchievement(validPath, nullDescription);
        assertEquals("", achievement.getDescription(), 
            "Description should be an empty string if null in constructor.");
    }

    @Test
    public void throwsOnNullFilePathInit() {
        final FileHandle nullPath = null;
        final String validDescription = "description1";
        assertThrows(IllegalArgumentException.class, () -> new CompletedAchievement(nullPath, validDescription),
        "Should throw IllegalArgumentException on null path");
    }

    @Test
    public void throwsOnInvalidFilePath() {
        final FileHandle invalidPath = Gdx.files.internal("tests/doesnt_exist");
        if (invalidPath.exists()) throw new IllegalArgumentException("FilePath should not exist");
        final String validDescription = "desc";
        assertThrows(IllegalArgumentException.class, () -> new CompletedAchievement(invalidPath, validDescription),
        "Should throw IllegalArgumentException on invalid path");
    }

    @Test
    public void toStringValid() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validDescription = "description1";
        final String expected = validPath.path() + "#" + validDescription;

        CompletedAchievement achievement = new CompletedAchievement(validPath, validDescription);
        assertEquals(expected, achievement.toString(), 
        "Expected string should ne \"path#description\"");
    }

    public void toStringEmptyDescription() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String emptyDescription = "";
        final String expected = validPath.path() + "#";

        CompletedAchievement achievement = new CompletedAchievement(validPath, emptyDescription);
        assertEquals(expected, achievement.toString(), 
        "Expected string should be \"path#\"");
    }

    @Test
    public void fromValidString() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validDescription = "description1";
        
        CompletedAchievement achievement = new CompletedAchievement(validPath, validDescription);
        final String str = achievement.toString();
        assertEquals(achievement, CompletedAchievement.fromString(str), 
            "The string should be successfully converted to a CompletedAchievementObject");
    }

    @Test
    public void fromStringWithNoDescription() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String emptyDescription = "";
        
        CompletedAchievement achievement = new CompletedAchievement(validPath, emptyDescription);
        final String str = achievement.toString();
        assertEquals(achievement, CompletedAchievement.fromString(str), 
            "The string should be successfully converted to a CompletedAchievementObject");
    }
}
