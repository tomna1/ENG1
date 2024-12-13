package io.github.archessmn.eng1.leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This is the UI for the {@link LeaderboardPosition} class. It contains all
 * the information needed to draw a data onto the screen.
 */
public class LeaderboardPositionRecord {
    private LeaderboardPosition data;
    // The label containing the username and score of the player. e.g tomna1: 1045.6
    private Label scoreNameLabel;

    /**
     * Creates a new record for a leaderboard positions. This includes a label
     * which holds the username and score of the playthrough. It also has
     * achievements (ImageButtons) which when clicked tell a description of how
     * they were achieved.
     * @param table The table to add the components to. Cannot be null.
     * @param data The data to create a record for. Cannot be null.
     * @param bounds The record will always be within these bounds. Cannot be
     * null.
     */
    public LeaderboardPositionRecord(Table table, LeaderboardPosition data, Rectangle bounds, Label hoverLabel) {
        if (table == null) throw new IllegalArgumentException("table cannot be null");
        if (data == null) throw new IllegalArgumentException("data cannot be null");
        if (bounds == null) throw new IllegalArgumentException("Bounds cannot be null");
        this.data = data;
        
        Rectangle labelBounds = new Rectangle(bounds);
        labelBounds.width = labelBounds.width/2;
        setupLabels(table, labelBounds);

        Rectangle achievementBounds = new Rectangle(bounds);
        achievementBounds.width = achievementBounds.width/2;
        achievementBounds.x = achievementBounds.x + achievementBounds.width;
        createAchievementsButtons(table, bounds, hoverLabel);
    }

    /**
     * Setups up the label containing the username and score of the player and 
     * adds them to the stage. Will be formatted like tomna1: 1037.2 where 
     * 'tomna1' is the username and '1037.2' is the score.
     * @param stage The stage to add the labels to when created.
     * @param skin The skin.
     * @param bounds The label will always be within theses bounds.
     */
    private Label setupLabels(Table table, Rectangle bounds) {
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        scoreNameLabel = new Label(String.format("%s : %f", data.getUsername(), data.getScore()), skin);
        // scoreNameLabel.setText(String.format("%s : %f", data.getUsername(), data.getScore()));
        // scoreNameLabel.setAlignment(Align.center);
        // scoreNameLabel.setBounds(bounds.x, bounds.y, bounds.width/2, bounds.height);
        // scoreNameLabel.setFontScale(2);
        table.add(scoreNameLabel);
        return scoreNameLabel;
    }

    /**
     * Creates the buttons for each achievement the player earned and adds
     * them to the stage. Each button has an achievement icon and when clicked
     * will show a label which tells the player how they were earned.
     * @param table The table to add the buttons to when created.
     * @param bounds The buttons will all always be within these bounds.
     */
    private void createAchievementsButtons(Table table, Rectangle bounds, Label hoverLabel) {
        ImageButton button;
        Rectangle achievementBounds = new Rectangle(bounds);
        final float widthPerAchievement = achievementBounds.width / data.getAchievementCount();
        achievementBounds.width = widthPerAchievement;

        for (int i = 0; i < data.getAchievementCount(); i++) {
            button = createAchievementButton(data.getAchievement(i), bounds, hoverLabel);
            table.add(button).left();
            achievementBounds.x += widthPerAchievement;
        }
    }

    /**
     * Creates a button which will have the icon associated with the achievement
     * and will print out the description of the achievement when clicked.
     * @param achievement The achievement to create a button of. Cannot be null.
     * @param bounds The bounds that the achievement should be in.
     * @return The ImageButton that is the achievement.
     */
    private ImageButton createAchievementButton(CompletedAchievement achievement, Rectangle bounds, Label hoverLabel) {
        if (achievement == null) throw new IllegalArgumentException("achievement cannot be null");
        Texture buttonUpTexture = new Texture(achievement.getIconPath());
        Drawable buttonUp = new TextureRegionDrawable(buttonUpTexture);
        ImageButton button = new ImageButton(buttonUp);
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                hoverLabel.setText(achievement.getTitle()+": "+achievement.getDescription());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            }
        });
        button.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);

        return button;
    }
}
