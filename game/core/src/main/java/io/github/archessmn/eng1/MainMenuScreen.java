package io.github.archessmn.eng1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * This is the screen that shows at the start of the game. It allows the player
 * to select between playing the game or viewing the leaderboard.
 */
public class MainMenuScreen implements Screen {
    private Stage stage;
    private FitViewport viewport;
    private ImageButton startGameButton;
    private ImageButton leaderboardButton;
    private Main main;

    /**
     * Creates a new main menu.
     * @param main Reference to Main. Cannot be null.
     */
    public MainMenuScreen(Main main) {
        if (main == null) throw new IllegalArgumentException("main cannot be null");
        this.main = main;
        this.viewport = main.getViewport();
        stage = new Stage(viewport);
        stage.setDebugAll(true);
        setupButtons();
    }

    /**
     * Sets up the start game button and leaderboard button and adds them
     * to the stage.
     */
    private void setupButtons() {
        final float buttonHeight = stage.getHeight()/4;
        final float buttonWidth = stage.getWidth()/4;
        final float maxButtonY = stage.getHeight() - (buttonHeight*2);
        final float spaceBetweenButtons = buttonHeight / 4;
        
        Texture startButtonUp = new Texture(Gdx.files.internal("start_game_button_up.png"));
        Texture startButtonDown = new Texture(Gdx.files.internal("start_game_button_down.png"));
        Drawable startUp = new TextureRegionDrawable(startButtonUp);
        Drawable startDown = new TextureRegionDrawable(startButtonDown);
        startGameButton = new ImageButton(startUp, startDown, startDown);
        startGameButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                main.startGame();
            }
        });
        startGameButton.setPosition((stage.getWidth()/2)-(buttonWidth/2), maxButtonY);
        startGameButton.setSize(buttonWidth, buttonHeight);
        startGameButton.align(Align.center);
        stage.addActor(startGameButton);

        Texture leaderboardButtonUp = new Texture(Gdx.files.internal("leaderboard_button_up.png"));
        Texture leaderboardButtonDown = new Texture(Gdx.files.internal("leaderboard_button_down.png"));
        Drawable leaderboardUp = new TextureRegionDrawable(leaderboardButtonUp);
        Drawable leaderboardDown = new TextureRegionDrawable(leaderboardButtonDown);
        leaderboardButton = new ImageButton(leaderboardUp, leaderboardDown, leaderboardDown);
        leaderboardButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                main.viewLeaderboard();
            }
        });
        leaderboardButton.setPosition((stage.getWidth()/2)-(buttonWidth/2), maxButtonY-buttonHeight-spaceBetweenButtons);
        leaderboardButton.setSize(buttonWidth, buttonHeight);
        leaderboardButton.align(Align.center);
        stage.addActor(leaderboardButton);
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
