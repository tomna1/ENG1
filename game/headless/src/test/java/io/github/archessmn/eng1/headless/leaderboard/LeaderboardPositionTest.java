package io.github.archessmn.eng1.headless.leaderboard;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import io.github.archessmn.eng1.headless.AbstractHeadlessGdxTest;
import io.github.archessmn.eng1.leaderboard.CompletedAchievement;
import io.github.archessmn.eng1.leaderboard.LeaderboardPosition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

public class LeaderboardPositionTest extends AbstractHeadlessGdxTest {
    
    @Test
    public void defaultInit() {
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validDescription = "description1";
        CompletedAchievement achievement = new CompletedAchievement(validPath, validDescription);
        
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
        final FileHandle validPath = Gdx.files.internal("tests/achievement_icons/icon_1.png");
        if (validPath.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validDescription = "description1";
        CompletedAchievement achievement = new CompletedAchievement(validPath, validDescription);

        final FileHandle validPath2 = Gdx.files.internal("tests/achievement_icons/icon_2.png");
        if (validPath2.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validDescription2 = "description1";
        CompletedAchievement achievement2 = new CompletedAchievement(validPath2, validDescription2);

        final FileHandle validPath3 = Gdx.files.internal("tests/achievement_icons/icon_3.png");
        if (validPath3.exists() == false) throw new IllegalArgumentException("file must exist");
        final String validDescription3 = "description1";
        CompletedAchievement achievement3 = new CompletedAchievement(validPath3, validDescription3);
        
        final String validUsername = "example";
        final float validScore = 100.0f;
        final ArrayList<CompletedAchievement> validAchievements = new ArrayList<>();
        validAchievements.add(achievement);
        validAchievements.add(achievement2);
        validAchievements.add(achievement3);

        LeaderboardPosition position = new LeaderboardPosition(validUsername, validScore, validAchievements);
        
        assertEquals(validAchievements, position.getAchievements(), 
            "position should be the same as defined in the constructor.");
        assertEquals(validAchievements.size(), position.getAchievementCount(),
            "achievement count should be the same as the size of the constructor arraylist");
        assertEquals(validAchievements.get(0), position.getAchievement(0), 
            "achievement returned should be the same as the index in original arraylist");
        assertEquals(validAchievements.get(2), position.getAchievement(2), 
            "achievement returned should be the same as the index in original arraylist");
    }

    @Test
    public void copyConstructorInit() {
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
    public void throwsOnNullCopyConstructorInit() {
        final LeaderboardPosition nullPosition = null;
        
        assertThrows(IllegalArgumentException.class, () -> new LeaderboardPosition(nullPosition), 
            "Should throw IllegalArguementException when arguement in constructor is null");
    }
}
