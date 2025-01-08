package io.github.archessmn.eng1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class EndGameMenu {
    private GameScreen gameScreen;
    private Stage stage;
    private Table table;
    private TextField textField;
    private Label mainLabel;
    private ImageButton confirmButton;
    private float score = 0;

    public EndGameMenu(Main main, GameScreen gameScreen) {
        if (main == null) throw new IllegalArgumentException("main cannot be null");
        if (gameScreen == null) throw new IllegalArgumentException("gameScreen cannot be null");
        this.gameScreen = gameScreen;

        stage = new Stage(main.getViewport());
        table = new Table();
        table.setFillParent(true);
        table.top();

        setupScoreLabel();
        setupTextField();
        setupConfirmButton(main);

        stage.addActor(table);
        stage.setDebugAll(true);
    }

    public void setScore(float score) {
        this.score = score;
        mainLabel.setText("Your Score was " + Float.toString(score) + 
            ". Please Enter your username below to save it to the leaderboard.\n" + 
            "Usernames must be between 1 and 16 characters inclusively.");
    }

    private void setupScoreLabel() {
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        mainLabel = new Label("", skin);
        mainLabel.setWrap(true);
        table.add(mainLabel).width(300.0f).pad(10.0f).row();
    }

    private void setupTextField() {
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        textField = new TextField("", skin);
        table.add(textField).width(300.0f).pad(10.0f).row();
    }

    private void setupConfirmButton(Main main) {
        Texture buttonUp = new Texture(Gdx.files.internal("ui/buttons/confirm_button_up.png"));
        Texture buttonDown = new Texture(Gdx.files.internal("ui/buttons/confirm_button_down.png"));
        
        Drawable confirmButtonUp = new TextureRegionDrawable(buttonUp);
        Drawable confirmButtonDown = new TextureRegionDrawable(buttonDown);
        confirmButton = new ImageButton(confirmButtonUp, confirmButtonDown, confirmButtonDown);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String text = textField.getText();
                if (text.length() < 1 || text.length() > 16) return;
                gameScreen.saveToLeaderboard(text, score);
                main.viewMainMenu();
            }
        });
        table.add(confirmButton).pad(10.0f).row();
    }

    public void setAsInputProcessor() {
        Gdx.input.setInputProcessor(stage);
    }

    public void draw(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
