package io.github.archessmn.eng1.leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.github.archessmn.eng1.core.Main;

/**
 * This class is the UI of the {@link Leaderboard}.
 */
public class LeaderboardMenu {
    private Stage stage;
    private Table leaderboardTable;
    private ImageButton menuButton;
    private Leaderboard leaderboard;
    private LeaderboardPositionRecord[] records;
    // The label should be changed to fit the description of whatever it is hovering over.
    private Label hoveringLabel;

    /**
     * Creates the menu for the leaderboard. The menu contains a menu button to go
     * back to the main menu as well as the leaderboard which shows the username,
     * score, and any achievement earned throught the playthrough for the top 5
     * scores in the leaderboard.
     * @param main Cannot be null.
     * @param leaderboard The leaderboard which holds the data. Cannot be null
     */
    public LeaderboardMenu(Main main, Leaderboard leaderboard) {
        if (main == null) throw new IllegalArgumentException("main cannot be null");
        if (leaderboard == null) throw new IllegalArgumentException("Leaderboard should not be null.");
        this.leaderboard = leaderboard;
        records = new LeaderboardPositionRecord[leaderboard.getCount()];
        
        stage = new Stage(main.getViewport());
        leaderboardTable = new Table();
        leaderboardTable.setFillParent(true);
        leaderboardTable.top();

        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Label label = new Label("Leaderboard", skin);
        leaderboardTable.add(label).width(100.0f).row();
        
        Skin skin2 = new Skin(Gdx.files.internal("ui/uiskin.json"));
        hoveringLabel = new Label("Hover over achievement icons to get the title and description", skin2);
        setupLeaderboard();
        hoveringLabel.setWrap(true);
        leaderboardTable.add(hoveringLabel).width(300.0f).pad(10.0f).bottom().row();
        setupMenuButton(main);
        
        stage.addActor(leaderboardTable);
        stage.setDebugAll(true);
    }

    private void setupMenuButton(Main main) {
        Texture buttonUp = new Texture(Gdx.files.internal("ui/buttons/menu_button_up.png"));
        Texture buttonDown = new Texture(Gdx.files.internal("ui/buttons/menu_button_down.png"));
        
        Drawable menuButtonUp = new TextureRegionDrawable(buttonUp);
        Drawable menuButtonDown = new TextureRegionDrawable(buttonDown);
        menuButton = new ImageButton(menuButtonUp, menuButtonDown, menuButtonDown);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.viewMainMenu();
            }
        });
        leaderboardTable.add(menuButton).pad(10.0f);
    }

    private void setupLeaderboard() {
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        
        final int leaderboardCount = leaderboard.getCount();
        for (int i = 0; i < leaderboardCount; i++) {
            records[i] = new LeaderboardPositionRecord(leaderboardTable, leaderboard.getLeaderboardPos(i), hoveringLabel);
            leaderboardTable.row();
        }

        skin.dispose();
    }

    /**
     * Sets the current menu as the input processor used by libgdx.
     */
    public void setAsInputProcessor() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Draws the menu.
     * @param delta Time since last frame.
     */
    public void draw(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
