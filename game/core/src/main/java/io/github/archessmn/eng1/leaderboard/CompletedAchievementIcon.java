package io.github.archessmn.eng1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CompletedAchievementIcon {
    private CompletedAchievement achievement;
    private ImageButton button;
    private Label label;


    public CompletedAchievementIcon(CompletedAchievement achievement, SpriteBatch batch, Skin skin) {
        if (achievement == null) throw new IllegalArgumentException("achievement cannot be null");
        if (batch == null) throw new IllegalArgumentException("batch cannot be null");
        if (skin == null) throw new IllegalArgumentException("skin cannot be null");
        
        label = new Label(achievement.getDescription(), skin);

        Texture buttonUpTexture = new Texture(achievement.getIconPath());
        Drawable buttonUp = new TextureRegionDrawable(buttonUpTexture);
        ImageButton button = new ImageButton(buttonUp);
        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                label.draw(batch, 1.0f);
            }
        });
    }
}
