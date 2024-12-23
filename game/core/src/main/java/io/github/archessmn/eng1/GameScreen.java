package io.github.archessmn.eng1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.archessmn.eng1.buildings.Building;
import io.github.archessmn.eng1.buildings.BuildingFactory;

public class GameScreen extends ConcreteScreen {
    private World world;
    private BuildingFactory buildingFactory;
    private GameMenu gameMenu;
    private PauseMenu pauseMenu;
    private Timer timer = new Timer(300, 60);

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    private Vector2 touchPos = new Vector2();
    private Vector2 worldPos = new Vector2();

    private Building.Type buildingTypeClicked = null;

    private Boolean paused = false;
    private Boolean gameEnded = false;

    public GameScreen(Main main) {
        super(main.getViewport());
        // 300 here represents the pixel width of the UI on the right hand side
        world = new World(Main.VIEWPORT_WIDTH - 300, Main.VIEWPORT_HEIGHT);
        buildingFactory = new BuildingFactory(world);
        viewport = main.getViewport();

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        gameMenu = new GameMenu(this, viewport);
        pauseMenu = new PauseMenu(viewport);
    }

    @Override
    public void show() {
        gameMenu.setAsInputProcesser();
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
            buildingTypeClicked = null;
            return;
        }

        touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        worldPos.set(viewport.unproject(touchPos));

        if (Gdx.input.justTouched() && buildingTypeClicked != null) {
            int x = (int)worldPos.x;
            int y = (int)worldPos.y;
            

            if (world.doesBuildingOverlap(x, y) == false) {
                Building building = buildingFactory.getBuildingFromType(buildingTypeClicked, (int)(touchPos.x - (touchPos.x%60)), (int)(touchPos.y - (touchPos.y%60)));
                boolean placeSuccess = building.place();
                if (placeSuccess) {
                    world.addBuilding(building);
                    Building.Use use = building.getBuildingUse();
                    gameMenu.updateCountLabels(use, world.getBuildingCount(use));
                }
                buildingTypeClicked = null;    
            }
        }
    }

    /**
     * Will process the input of a building button being clicked. Should be called
     * be any building button.
     * @param typeClicked The type of the building button that was clicked.
     */
    public void processBuildingMenuInput(Building.Type typeClicked) {
        if (this.buildingTypeClicked != null) return;
        if (typeClicked == null) return;
        buildingTypeClicked = typeClicked;
    }

    private void logic(float deltaTime) {
        if (paused || gameEnded) return;
        world.tickbuildings();
        timer.update(deltaTime);
        if (timer.hasEnded()) {
            gameEnded = true;
        }
        gameMenu.updateTimerLabel(timer.getYearCount(), timer.getDayCount());
    }

    private void draw() {
        ScreenUtils.clear(Color.OLIVE);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        world.drawGrid();

        touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        worldPos.set(viewport.unproject(touchPos));

        if (buildingTypeClicked != null) {
            // Draw red outline on the grid tile where the building is being placed.
            if (world.doesBuildingOverlap((int)touchPos.x, (int)touchPos.y)) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            } else {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            }
            shapeRenderer.setColor(Color.RED);
            // NOTE: 60 IS THE BUILDING HEIGHT/WIDTH.
            shapeRenderer.rect((touchPos.x - (touchPos.x%60)), (touchPos.y - (touchPos.y%60)),
                               60, 60);
            shapeRenderer.end();
        }

        world.drawbuildings(batch);
        gameMenu.draw();

        if (paused) {
            pauseMenu.draw();
        }
    }

    /**
     * Draws the dark grey building menu.
     */
    //private void drawBuildingMenu() {
        // Give the menu its drak grey background.
    //    blockRenderer.begin(ShapeRenderer.ShapeType.Filled);
    //    blockRenderer.setColor(Color.DARK_GRAY);
    //    blockRenderer.rect(rightTable.getX(), rightTable.getY(), rightTable.getWidth(), rightTable.getHeight());
    //    blockRenderer.end();
    //}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        world.dispose();
        gameMenu.dispose();
        pauseMenu.dispose();
    }
}
