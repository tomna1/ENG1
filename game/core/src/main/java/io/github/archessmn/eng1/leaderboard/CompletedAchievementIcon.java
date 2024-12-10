package io.github.archessmn.eng1.leaderboard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This class defines the UI for the {@link CompletedAchievement}
 * class.
 */
public class CompletedAchievementIcon {
    private ImageButton button;
    private Label label;

    public CompletedAchievementIcon(CompletedAchievement achievement, SpriteBatch batch, Skin skin, Rectangle bounds) {
        if (achievement == null) throw new IllegalArgumentException("achievement cannot be null");
        if (batch == null) throw new IllegalArgumentException("batch cannot be null");
        if (skin == null) throw new IllegalArgumentException("skin cannot be null");
        
        label = new Label(achievement.getDescription(), skin);
        label.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);

        Texture buttonUpTexture = new Texture(achievement.getIconPath());
        Drawable buttonUp = new TextureRegionDrawable(buttonUpTexture);
        button = new ImageButton(buttonUp);
        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                label.draw(batch, 1.0f);
            }
        });

        button.setBounds(0, 0, 0, 0);
    }

    public void addToStage() {

    }

    public ImageButton getButton() {
        return button;
    }

    public Label getLabel() {
        return label;
    }
}
