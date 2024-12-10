package io.github.archessmn.eng1.leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

/**
 * This is the UI for the {@link LeaderboardPosition} class. It contains all
 * the information needed to draw a record onto the screen.
 */
public class LeaderboardPositionRecord{
    private LeaderboardPosition record;
    private Label score;
    private Label name;
    private ImageButton[] achievements;

    public LeaderboardPositionRecord(LeaderboardPosition record, Skin skin, Rectangle bounds) {
        if (record == null) throw new IllegalArgumentException("record cannot be null");
        if (skin == null) throw new IllegalArgumentException("skin cannot be null");
        if (bounds == null) throw new IllegalArgumentException("Bounds cannot be null");
        this.record = record;
        
        name = new Label(record.getUsername(), skin);
        name.setAlignment(Align.left);
        name.setBounds(bounds.x, bounds.y, bounds.width/2, bounds.height);
        score = new Label(Float.toString(record.getScore()), skin);
        score.setAlignment(Align.right);
        score.setBounds(bounds.x + bounds.width/2, bounds.y, bounds.width/2, bounds.height);
    }

    /* 
    private void setupAchievements() {
        CompletedAchievement achievement;
        ImageButton button;
        achievements = new ImageButton[record.getAchievementCount()];
        for (int i = 0; i < record.getAchievementCount(); i++) {
            achievement = record.getAchievement(i);
            button = achievement.getButton();
            button.addListener()
        }
    } */

    /**
     * Returns a deep copy of the associated LeaderboardPosition.
     * @return
     */
    public LeaderboardPosition getLeaderboardPosition() {
        return new LeaderboardPosition(record);
    }

    public Label getNameLabel() {
        return name;
    } 

    public Label getScoreLabel() {
        return score;
    }

    public ImageButton[] getAchievements() {
        return achievements;
    }
}
