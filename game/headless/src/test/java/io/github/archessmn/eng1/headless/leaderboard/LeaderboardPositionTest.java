package io.github.archessmn.eng1.headless.leaderboard;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import io.github.archessmn.eng1.headless.AbstractHeadlessGdxTest;
import io.github.archessmn.eng1.leaderboard.CompletedAchievement;
import io.github.archessmn.eng1.leaderboard.LeaderboardPosition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;

import java.util.ArrayList;

public class LeaderboardPositionTest extends AbstractHeadlessGdxTest {
    public CompletedAchievement createRealAchievement() {
        final String validPath = "tests/achievement_icons/icon_1.png";
        final FileHandle validFile = Gdx.files.internal(validPath);
        if (validFile.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validTitle = "title1";
        final String validDescription = "description1";

        final CompletedAchievement achievement = new CompletedAchievement(validPath, validTitle, validDescription);
        return achievement;
    }

    @Test
    public void defaultInit() {
        CompletedAchievement achievement = mock(CompletedAchievement.class);
        
        final String validUsername = "example";
        final float validScore = 100.0f;
        final ArrayList<CompletedAchievement> validAchievements = new ArrayList<>();
        validAchievements.add(achievement);

        LeaderboardPosition position = new LeaderboardPosition(validUsername, validScore, validAchievements);
        assertEquals(validUsername, position.getUsername(), 
            "position should be the same as defined in the constructor.");
        assertEquals(validScore, position.getScore(), 
            "position should be the same as defined in the constructor.");
        assertEquals(validAchievements, position.getAchievements(), 
            "position should be the same as defined in the constructor.");
    }

    @Test
    public void nullAchievementsInit() {
        final String validUsername = "example2";
        final float validScore = 120.0f;
        final ArrayList<CompletedAchievement> nullAchievements = null;

        LeaderboardPosition position = new LeaderboardPosition(validUsername, validScore, nullAchievements);
        assertEquals(new ArrayList<CompletedAchievement>() , position.getAchievements(), 
            "If achievements are defined as null in constructor, should return an empty arraylist.");
    }

    @Test
    public void throwsOnNullUsernameInit() {
        final String nullUsername = null;
        final float validScore = 120.0f;
        final ArrayList<CompletedAchievement> validAchievements = null;

        assertThrows(IllegalArgumentException.class, () -> new LeaderboardPosition(nullUsername, validScore, validAchievements), 
            "Should throws IllegalArguementException if username is null.");
    }

    @Test
    public void multipleAchievementsInit() {
        CompletedAchievement achievement = mock(CompletedAchievement.class);
        CompletedAchievement achievement2 = mock(CompletedAchievement.class);
        CompletedAchievement achievement3 = mock(CompletedAchievement.class);
        final ArrayList<CompletedAchievement> validAchievements = new ArrayList<>();
        validAchievements.add(achievement);
        validAchievements.add(achievement2);
        validAchievements.add(achievement3);
        
        final String validUsername = "example";
        final float validScore = 100.0f;
    
        LeaderboardPosition position = new LeaderboardPosition(validUsername, validScore, validAchievements);
        
        assertEquals(validAchievements, position.getAchievements(), 
            "position should be the same as defined in the constructor.");
        assertEquals(validAchievements.size(), position.getAchievementCount(),
            "achievement count should be the same as the size of the constructor arraylist");
        assertEquals(validAchievements.get(0), position.getAchievement(0), 
            "achievement returned should be the same as the index in original arraylist");
        assertEquals(validAchievements.get(1), position.getAchievement(1), 
            "achievement returned should be the same as the index in original arraylist");
        assertEquals(validAchievements.get(2), position.getAchievement(2), 
            "achievement returned should be the same as the index in original arraylist");
    }

    @Test
    public void validCopyConstructorInit() {
        final String validUsername = "example2";
        final float validScore = 120.0f;
        final ArrayList<CompletedAchievement> nullAchievements = null;

        LeaderboardPosition position = new LeaderboardPosition(validUsername, validScore, nullAchievements);
        LeaderboardPosition position2 = new LeaderboardPosition(position);

        assertEquals(position.getUsername(), position2.getUsername(), 
            "position should be the same as defined in the constructor.");
        assertEquals(position.getScore(), position2.getScore(), 
            "position should be the same as defined in the constructor.");
        assertEquals(position.getAchievements(), position2.getAchievements(), 
            "position should be the same as defined in the constructor.");
    }

    @Test
    public void throwsOnNullCopyConstructor() {
        final LeaderboardPosition nullPosition = null;
        
        assertThrows(IllegalArgumentException.class, () -> new LeaderboardPosition(nullPosition), 
            "Should throw IllegalArguementException when arguement in constructor is null");
    }

    @Test
    public void addAchievementAfterInit() {
        final String validUsername = "example";
        final float validScore = 100.0f;
        final CompletedAchievement achievement = mock(CompletedAchievement.class);
        final ArrayList<CompletedAchievement> validAchievements = null;
        final ArrayList<CompletedAchievement> expectedAchievements = new ArrayList<>();
        expectedAchievements.add(achievement);
        final boolean expectedResult = true;
        
        final LeaderboardPosition position = new LeaderboardPosition(validUsername, validScore, validAchievements);
        boolean result = position.addCompletedAchievement(achievement);
        
        assertEquals(expectedResult, result, 
            "The addCompletedAchievement method should have return true when a valid achievement is the argument");
        assertEquals(1, position.getAchievementCount(),
            "achievement count should now be 1 since an achievement has been added.");
    }

    @Test
    public void returnFalseOnAddNullAchievement() {
        final String validUsername = "example";
        final float validScore = 100.0f;
        final CompletedAchievement nullAchievement = null;
        final ArrayList<CompletedAchievement> validAchievements = null;
        final boolean expectedResult = false;

        final LeaderboardPosition position = new LeaderboardPosition(validUsername, validScore, validAchievements);
        final int expectedAchievementCount = position.getAchievementCount();
        final boolean result = position.addCompletedAchievement(nullAchievement);

        assertEquals(expectedResult, result,
            "addCompletedAchievement should have returned false with a null argument");
        assertEquals(expectedAchievementCount, position.getAchievementCount(), 
            "achievement count should not have changed after null arguement.");
    }

    @Test
    public void returnFalseOnDuplicateAddAchievement() {
        final String validUsername = "example";
        final float validScore = 100.0f;
        final CompletedAchievement validAchievement = createRealAchievement();
        final ArrayList<CompletedAchievement> validAchievements = null;
        final boolean expectedResult = false;

        final LeaderboardPosition position = new LeaderboardPosition(validUsername, validScore, validAchievements);
        position.addCompletedAchievement(validAchievement);
        final int expectedCount = position.getAchievementCount();
        final boolean result = position.addCompletedAchievement(validAchievement);

        assertEquals(expectedResult, result, 
            "Should return false when adding a duplicate achievement.");
        assertEquals(expectedCount, position.getAchievementCount(), 
            "Count should not have changed when attempting to add duplicate achievement.");
    }

    @Test
    public void compareToValidPosition() {
        final String validUsername = "example";
        final float validScore1 = 100.0f;
        final float validScore2 = 200.0f;
        final ArrayList<CompletedAchievement> validAchievements = null;

        final LeaderboardPosition position1 = new LeaderboardPosition(validUsername, validScore1, validAchievements);
        final LeaderboardPosition position2 = new LeaderboardPosition(validUsername, validScore2, validAchievements);

        assertEquals(true, position1.compareTo(position2) < 0, 
            "Should return anything < 0 since score of position1 is less than position2.");
        assertEquals(true, position2.compareTo(position1) > 0, 
            "Should return anything > 0 since score of position2 is greater than position1.");
        assertEquals(0, position1.compareTo(position1), 
            "Positions with the same score should return 0.");
    }
}
