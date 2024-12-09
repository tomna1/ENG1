package io.github.archessmn.eng1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * This class is the UI of the {@link Leaderboard}.
 */
public class LeaderboardMenu {
    private Stage stage;
    private Table table;
    private ImageButton menuButton;
    private final Skin skin;
    private Leaderboard leaderboard;

    public LeaderboardMenu(Leaderboard leaderboard, FitViewport viewport) {
        if (leaderboard == null) throw new IllegalArgumentException("Leaderboard should not be null.");
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);
        setupMenuButton();
        this.leaderboard = leaderboard;
    }

    private void setupMenuButton() {
        Texture buttonUp = new Texture(Gdx.files.internal(""));
        Texture buttonDown = new Texture(Gdx.files.internal(""));
        
        Drawable menuButtonUp = new TextureRegionDrawable(buttonUp);
        Drawable menuButtonDown = new TextureRegionDrawable(buttonDown);
        menuButton = new ImageButton(menuButtonUp, menuButtonDown, menuButtonDown);
        menuButton.setSize(20, 20);
        menuButton.setPosition(0, 0);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO: go to main menu.
            }
        });
    }

    private void setupTable() {
        final float height =  stage.getHeight();

        for (int i = 0; i < leaderboard.getCount(); i++) {
            
            table.add()
        }
    }

    public void draw() {
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
