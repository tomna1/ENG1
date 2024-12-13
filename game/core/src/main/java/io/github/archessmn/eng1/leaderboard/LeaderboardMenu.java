package io.github.archessmn.eng1.leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.github.archessmn.eng1.Main;

/**
 * This class is the UI of the {@link Leaderboard}.
 */
public class LeaderboardMenu {
    private Stage stage;
    private Table leaderboardTable;
    private ImageButton menuButton;
    private Leaderboard leaderboard;
    private LeaderboardPositionRecord[] records;

    public LeaderboardMenu(Main main, Leaderboard leaderboard) {
        if (leaderboard == null) throw new IllegalArgumentException("Leaderboard should not be null.");
        if (main == null) throw new IllegalArgumentException("main cannot be null");
        this.leaderboard = leaderboard;
        records = new LeaderboardPositionRecord[leaderboard.getCount()];
        
        stage = new Stage(main.getViewport());
        leaderboardTable = new Table();
        leaderboardTable.setFillParent(true);
        leaderboardTable.top();

        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Label label = new Label("Leaderboard", skin);
        leaderboardTable.add(label).width(100.0f).row();
        stage.addActor(leaderboardTable);

        setupMenuButton(main);
        setupLeaderboard();
        stage.setDebugAll(true);
    }

    private void setupMenuButton(Main main) {
        Texture buttonUp = new Texture(Gdx.files.internal("ui/buttons/menu_button_up.png"));
        Texture buttonDown = new Texture(Gdx.files.internal("ui/buttons/menu_button_down.png"));
        
        Drawable menuButtonUp = new TextureRegionDrawable(buttonUp);
        Drawable menuButtonDown = new TextureRegionDrawable(buttonDown);
        menuButton = new ImageButton(menuButtonUp, menuButtonDown, menuButtonDown);
        menuButton.setSize(200, 200);
        menuButton.setPosition(0, 0);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.viewMainMenu();
            }
        });
        stage.addActor(menuButton);
    }

    private void setupLeaderboard() {
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        
        final int leaderboardCount = leaderboard.getCount();
        
        // The X value of all records in the leaderboard.
        final float leaderboardX = (stage.getWidth()/4);
        // The starting Y value of the records in the leaderboard.
        final float leaderboardY = stage.getHeight();
        // The width of each record in the leaderboard
        final float leaderboardWidth = stage.getWidth()/2;
        // The height of each record in the leaderboard.
        final float leaderboardRecordHeight = stage.getHeight()/leaderboardCount;
        Rectangle recordBounds = new Rectangle(leaderboardX, leaderboardY, leaderboardWidth, leaderboardRecordHeight);

        for (int i = 0; i < leaderboardCount; i++) {
            records[i] = new LeaderboardPositionRecord(leaderboardTable, leaderboard.getLeaderboardPos(i), recordBounds);
            leaderboardTable.row();
            recordBounds.y -= leaderboardRecordHeight;
        }

        skin.dispose();
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
