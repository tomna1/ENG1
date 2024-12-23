package io.github.archessmn.eng1;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.archessmn.eng1.buildings.Building;

public class GameMenu extends Menu {
    private GameScreen gameScreen;
    
    private TextureAtlas atlas;
    private Skin skin;
    private Label timerLabel;
    private ShapeRenderer blockRenderer;

    private final HashMap<Building.Use, Label> buildingUseCountLabels = new HashMap<>();
    private final HashMap<Building.Use, Label> buildingUseNameLabels = new HashMap<>();
    
    public GameMenu(GameScreen gameScreen, Viewport viewport) {
        super(viewport);
        this.gameScreen = gameScreen;

        atlas = new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.addRegions(atlas);

        createLabels();
    }

    private void createLabels() {
        Label.LabelStyle labelStyle = skin.get(Label.LabelStyle.class);

        timerLabel = new Label("Timer", labelStyle);
        // This is setting up the label for the counters of each building. "Sleep buildings:"
        // is an example of how the labels are meant to look.
        for (Building.Use buildingUse : Building.Use.values()) {
            String useName = buildingUse.toString().charAt(0) + buildingUse.toString().substring(1).toLowerCase();
            buildingUseNameLabels.put(buildingUse, new Label(useName + " buildings:", labelStyle));
        }
        // This is the counter for how many buildings of each type have been built.
        for (Building.Use buildingUse : Building.Use.values()) {
            buildingUseCountLabels.put(buildingUse, new Label("0", labelStyle));
        }
        
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        table.pad(10);
        rootTable.right().add(table).expandY().fillY().width(300);
        table.add(timerLabel).row();
        for (Building.Use buildingUse : Building.Use.values()) {
            table.add(buildingUseNameLabels.get(buildingUse)).left();
            table.add(buildingUseCountLabels.get(buildingUse)).right().row();
        }
        addBuildingButtons();

        blockRenderer = new ShapeRenderer();
    }

    private void addBuildingButtons() {
        for (Building.Type type : Building.Type.values()) {
            String[] files = Building.getButtonFileOfType(type);
            ImageButton button = createBuildingButton(
                type, 
                Gdx.files.internal(files[0]), 
                Gdx.files.internal(files[1])
            );
            table.add(button).center().row();
        }
    }

    private ImageButton createBuildingButton(Building.Type buildingType, FileHandle buttonUpFile, FileHandle buttonDownFile) {
        Texture buttonUpTexture = new Texture(buttonUpFile);
        Texture buttonDownTexture = new Texture(buttonDownFile);
        Drawable buttonUp = new TextureRegionDrawable(buttonUpTexture);
        Drawable buttonDown = new TextureRegionDrawable(buttonDownTexture);
        ImageButton button = new ImageButton(buttonUp, buttonDown, buttonDown);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.processBuildingMenuInput(buildingType);
            }
        });
        return button;
    }

    public void updateCountLabels(Building.Use use, int newCount) {
        buildingUseCountLabels.get(use).setText(Integer.toString(newCount));
    }

    public void updateTimerLabel(int yearCount, int dayCount) {
        timerLabel.setText(String.format("Year: %d, Day: %d", yearCount, dayCount));
    }

    public void draw() {
        stage.act();
        drawBuildingMenuBackground();
        stage.draw();
    }

    /**
     * Draws the dark grey building menu.
     */
    private void drawBuildingMenuBackground() {
        // Give the menu its dark grey background.
        blockRenderer.begin(ShapeRenderer.ShapeType.Filled);
        blockRenderer.setColor(Color.DARK_GRAY);
        blockRenderer.rect(table.getX(), table.getY(), table.getWidth(), table.getHeight());
        blockRenderer.end();
    }
}
