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
        final String validTitle = "title1";
        final String validDescription = "description1";

        CompletedAchievement achievement = new CompletedAchievement(validPath, validTitle, validDescription);

        assertEquals(validPath, achievement.getIconPath(), 
            "Achievement must return the same path as that defined in its constructor.");
        assertEquals(validDescription, achievement.getDescription(), 
            "Achievement must return the same desciption as that defined in the constructor.");
    }

    @Test
    public void nullDescriptionInit() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validTitle = "title1";
        final String nullDescription = null;

        CompletedAchievement achievement = new CompletedAchievement(validPath, validTitle, nullDescription);
        assertEquals("", achievement.getDescription(), 
            "Description should be an empty string if null in constructor.");
    }

    @Test
    public void throwsOnNullTitleInit() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String nullTitle = null;
        final String validDescription = "description1";

        assertThrows(IllegalArgumentException.class, () -> new CompletedAchievement(validPath, nullTitle, validDescription),
        "Should throw IllegalArgumentException on null title");
    }

    @Test
    public void throwsOnEmptyTitleInit() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String emptyTitle = "";
        final String validDescription = "description1";

        assertThrows(IllegalArgumentException.class, () -> new CompletedAchievement(validPath, emptyTitle, validDescription),
        "Should throw IllegalArgumentException on null title");
    }

    @Test
    public void throwsOnNullFilePathInit() {
        final FileHandle nullPath = null;
        final String validDescription = "description1";
        final String validTitle = "title1";

        assertThrows(IllegalArgumentException.class, () -> new CompletedAchievement(nullPath, validTitle, validDescription),
        "Should throw IllegalArgumentException on null path");
    }

    @Test
    public void throwsOnInvalidFilePath() {
        final FileHandle invalidPath = Gdx.files.internal("tests/doesnt_exist");
        if (invalidPath.exists()) throw new IllegalArgumentException("FilePath should not exist");
        final String validTitle = "title1";
        final String validDescription = "desc";
        assertThrows(IllegalArgumentException.class, () -> new CompletedAchievement(invalidPath, validTitle, validDescription),
        "Should throw IllegalArgumentException on invalid path");
    }

    @Test
    public void toLeaderboardStringValid() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validTitle = "title1";
        final String validDescription = "description1";
        final String expected = validPath.path() + "#" + validTitle + "#" + validDescription;

        CompletedAchievement achievement = new CompletedAchievement(validPath, validTitle, validDescription);
        assertEquals(expected, achievement.toLeaderboardString(), 
        "Expected string should ne \"path#title1#description1\"");
    }

    public void toLeaderboardStringEmptyDescription() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validTitle = "title1";
        final String emptyDescription = "";
        final String expected = validPath.path() + "#" + validTitle + "#";

        CompletedAchievement achievement = new CompletedAchievement(validPath, validTitle, emptyDescription);
        assertEquals(expected, achievement.toLeaderboardString(), 
        "Expected string should be \"path#title1#\"");
    }

    @Test
    public void fromValidString() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validTitle = "title1";
        final String validDescription = "description1";
        
        CompletedAchievement achievement = new CompletedAchievement(validPath, validTitle, validDescription);
        final String str = achievement.toLeaderboardString();
        assertEquals(achievement, CompletedAchievement.fromLeaderboardString(str), 
            "The string should be successfully converted to a CompletedAchievementObject");
    }

    @Test
    public void fromStringWithNoDescription() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validTitle = "title1";
        final String emptyDescription = "";
        
        CompletedAchievement achievement = new CompletedAchievement(validPath, validTitle, emptyDescription);
        final String str = achievement.toLeaderboardString();
        assertEquals(achievement, CompletedAchievement.fromLeaderboardString(str), 
            "The string should be successfully converted to a CompletedAchievementObject");
    }
}
