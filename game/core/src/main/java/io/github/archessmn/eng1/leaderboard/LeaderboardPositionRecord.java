package io.github.archessmn.eng1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
public class LeaderboardPositionRecord extends Widget {
    private LeaderboardPosition record;
    private Label score;
    private Label name;
    private ImageButton[] achievements;

    public LeaderboardPositionRecord(LeaderboardPosition record, Skin skin) {
        if (record == null) throw new IllegalArgumentException("record cannot be null");
        if (skin == null) throw new IllegalArgumentException("skin cannot be null");
        this.record = record;
        final int width = Gdx.graphics.getWidth();
        final int height = Gdx.graphics.getHeight();
        final float thisWidth = width / 2;
        final float thisHeight = height / 12;
        this.setBounds((thisWidth)-(thisWidth), height-((record.getPosition()+1)*thisHeight), thisWidth, thisHeight);
        
        name = new Label(record.getUsername(), skin);
        score = new Label(Float.toString(record.getScore()), skin);
    }

    private void setupAchievements() {
        CompletedAchievement achievement;
        ImageButton button;
        achievements = new ImageButton[record.getAchievementCount()];
        for (int i = 0; i < record.getAchievementCount(); i++) {
            achievement = record.getAchievement(i);
            button = achievement.getButton();
            button.addListener()
        }
    }

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
