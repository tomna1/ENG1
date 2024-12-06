package io.github.archessmn.eng1;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.archessmn.eng1.buildings.Building;
import io.github.archessmn.eng1.buildings.GymBuilding;
import io.github.archessmn.eng1.buildings.HallsBuilding;
import io.github.archessmn.eng1.buildings.LectureHallBuilding;
import io.github.archessmn.eng1.buildings.OfficeBuilding;
import io.github.archessmn.eng1.buildings.PiazzaBuilding;

public class GameScreen implements Screen {
    private World world;
    private FitViewport viewport;

    private TextureAtlas atlas;
    private Skin skin;

    private ShapeRenderer shapeRenderer;
    private ShapeRenderer blockRenderer;
    private SpriteBatch batch;

    private Vector2 touchPos = new Vector2();
    private Vector2 unprojectedTouchPos = new Vector2();

    private Array<Building> draggablebuildings = new Array<>();

    private Timer timer = new Timer(300, 60);

    private BitmapFont font;
    private Integer buildingClicked = -1;

    private Boolean paused = true;
    private Boolean gameEnded = false;

    private Stage stage;
    private Table rightTable;

    private Label timerLabel;
    private final HashMap<Building.Use, Label> buildingUseCountLabels = new HashMap<>();
    private final HashMap<Building.Use, Label> buildingUseNameLabels = new HashMap<>();

    public GameScreen(Main main) {
        // 300 here represents the pixel width of the UI on the right hand side
        world = new World(Main.VIEWPORT_WIDTH - 300, Main.VIEWPORT_HEIGHT);
        viewport = main.getViewport();

        atlas = new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        skin.addRegions(atlas);

        shapeRenderer = new ShapeRenderer();
        blockRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        // These are the icons for the drawable buildings on the right hand side of the menu.
        draggablebuildings.add(new GymBuilding(world));
        draggablebuildings.add(new HallsBuilding(world));
        draggablebuildings.add(new LectureHallBuilding(world));
        draggablebuildings.add(new OfficeBuilding(world));
        draggablebuildings.add(new PiazzaBuilding(world));

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        createLabels();
        font = createFont();
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
        
        // I have no idea what this rootTable nonsense is.
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        rightTable = new Table();
        rightTable.pad(10);
        rootTable.right().add(rightTable).expandY().fillY().width(300);
        rightTable.add(timerLabel).row();
        for (Building.Use buildingUse : Building.Use.values()) {
            rightTable.add(buildingUseNameLabels.get(buildingUse)).left();
            rightTable.add(buildingUseCountLabels.get(buildingUse)).right().row();
        }
        rightTable.add(new Label("\nHelp:", labelStyle)).row();
        rightTable.add(new Label("Drag a building from below to place it", labelStyle)).left().top().row();
        rightTable.add(new Label("onto the grid. Don't overlap them!", labelStyle)).left().top().row();
        rightTable.add(new Label("Gym  Halls  Lecture Hall  Office  Piazza", labelStyle)).expandX().expandY().bottom();
    }

    private BitmapFont createFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/Arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (0.05f * Gdx.graphics.getHeight());
        font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {

    }

    @Override
    public void resume() {
        
    }

    @Override
    public void pause() {

    }

    @Override
    public void render(float deltaTime) {
        input();
        logic(deltaTime);
        draw();
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) paused = !paused;

        if (paused || gameEnded) {
            buildingClicked = -1;
            return;
        }

        touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        unprojectedTouchPos.set(viewport.unproject(touchPos));
        // If the player clicks on the building icons of the menu, makes a copy and idk
        // all of this should probably be refactored to use buttons anyway.
        if (Gdx.input.justTouched()) {
            for (int i = draggablebuildings.size - 1; i >= 0; i--) {
                Building building = draggablebuildings.get(i);
                if (building.getBounds().contains(unprojectedTouchPos)) {
                    buildingClicked = world.addBuilding(building.makeCopy());
                    break;
                }
            }
        } 
        // If the player lets go of click button after they have pressed the
        // a building button, places the builing at that location.
        else if (!Gdx.input.isTouched() && buildingClicked != -1) {
            Building building = world.getBuilding(buildingClicked);
            boolean placeSuccess = building.place();
            if (!placeSuccess) {
                world.buildings.removeIndex(buildingClicked);
            }
            else {
                // Updates the building count labels.
                world.buildingUseCounts.put(building.getBuildingUse(),
                                            world.buildingUseCounts.get(building.getBuildingUse()) + 1
                                            );
            }
            buildingClicked = -1;
        }

        if (buildingClicked != -1) {
            world.getBuilding(buildingClicked).setCenter(touchPos.x, touchPos.y);
        }
    }

    private void logic(float deltaTime) {
        if (paused || gameEnded) return;
        world.tickbuildings();
        float delta = Gdx.graphics.getDeltaTime();
        timer.update(deltaTime);
        if (timer.hasEnded()) {
            gameEnded = true;
        }
        stage.act(delta);
    }

    private void draw() {
        ScreenUtils.clear(Color.OLIVE);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        world.drawGrid();

        if (buildingClicked != -1) {
            // Draw red outline on the grid tile where the building is being placed.
            Building building = world.getBuilding(buildingClicked);
            if (world.doesBuildingOverlap(building)) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            } else {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            }
            shapeRenderer.setColor(Color.RED);
            Vector2 buldingCoords = building.getRawGridCoords();
            shapeRenderer.rect(buldingCoords.x - (building.getWidth() / 2), buldingCoords.y - (building.getHeight() / 2),
                               building.getWidth(), building.getHeight());
            shapeRenderer.end();

            batch.begin();
            if (world.doesBuildingOverlap(buildingClicked)) {
                font.draw(batch, "buildings overlap", 20, 520);
            }
            batch.end();
        }

        this.drawBuildingMenu();

        world.drawbuildings(batch);

        batch.begin();
        if (paused) {
            font.draw(batch, "Paused, press [ESC] to resume", 20, 460);
            font.draw(batch, "Building icons from macrovector on Freepik", 0, 25);
        }
        if (gameEnded) {
            font.draw(batch, "End of the game!", 20, 460);
            font.draw(batch, "Building icons from macrovector on Freepik", 0, 25);
        }
        batch.end();

        timerLabel.setText(String.format("Year: %d, Day: %d", timer.getYearCount(), timer.getDayCount()));
        // Sets the building count labels to the updated building count values.
        for (Building.Use use : Building.Use.values()) {
            buildingUseCountLabels.get(use).setText(world.buildingUseCounts.get(use));
        }

        stage.draw();
    }

    /**
     * Draws the dark grey building menu.
     */
    private void drawBuildingMenu() {
        // Give the menu its drak grey background.
        blockRenderer.begin(ShapeRenderer.ShapeType.Filled);
        blockRenderer.setColor(Color.DARK_GRAY);
        blockRenderer.rect(rightTable.getX(), rightTable.getY(), rightTable.getWidth(), rightTable.getHeight());
        blockRenderer.end();

        batch.begin();
        for (Building building : draggablebuildings) {
            building.draw(batch);
        }
        batch.end();
    }

    /**
     * This method gets overriden by {@link Main#resize(int, int)}
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
        batch.dispose();
        world.dispose();
    }
}
