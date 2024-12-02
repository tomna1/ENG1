package io.github.archessmn.eng1;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import io.github.archessmn.eng1.buildings.Building;
import io.github.archessmn.eng1.util.GridCoordTuple;
import io.github.archessmn.eng1.util.GridUtils;

import java.util.HashMap;

/**
 * Class used to store information about the world and the buildings in it.
 */
public class World {
    public AssetManager assetManager;
    private ShapeRenderer gridRenderer;
    private Integer width, height;
    public Array<Building> buildings = new Array<>();
    public HashMap<Building.Use, Integer> buildingUseCounts = new HashMap<>();

    /**
     * Initialises an empty world and loads assets.
     * @param VIEWPORT_WIDTH Width to create the viewport
     * @param VIEWPORT_HEIGHT Height to create the viewport
     * @param worldWidth Width to use for the usable world space
     * @param worldHeight Height to use for the usable world space
     */
    public World(Integer VIEWPORT_WIDTH, Integer VIEWPORT_HEIGHT, Integer worldWidth, Integer worldHeight) {
        this.width = worldWidth;
        this.height = worldHeight;

        gridRenderer = new ShapeRenderer();

        for (Building.Use use : Building.Use.values()) {
            buildingUseCounts.put(use, 0);
        }

        assetManager = new AssetManager();
        assetManager.load("gym.png", Texture.class);
        assetManager.load("halls.png", Texture.class);
        assetManager.load("lecturehall.png", Texture.class);
        assetManager.load("offices.png", Texture.class);
        assetManager.load("piazza.png", Texture.class);
        assetManager.load("construction.png", Texture.class);
        assetManager.load("missing_texture.png", Texture.class);
        assetManager.finishLoading();
    }
    
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }

    /**
     * Draws the grid using {@link GridUtils}
     */
    public void drawGrid() {
        GridUtils.drawGrid(gridRenderer);
    }

    /**
     * Adds a building to the world building store and returns its location in the store
     * @param building Building to add to the world
     * @return The index of the building in the world store
     */
    public int addBuilding(Building building) {
        buildings.add(building);
        return buildings.size - 1;
    }

    /**
     * Run the {@link Building#tick()} method on each building in the world.
     */
    public void tickbuildings() {
        for (Building building : buildings) {
            building.tick();
        }
    }

    /**
     * Draw all the buildings into the world.
     */
    public void drawbuildings(SpriteBatch batch) {
        batch.begin();
        for (Building building : buildings) building.draw(batch);
        batch.end();
    }

    /**
     * Get a building from the world building store
     * @param id The ID of a building (its index).
     * @return The building with the given ID / index
     */
    public Building getBuilding(Integer id) {
        return buildings.get(id);
    }

    /**
     * Utility method to check if a building overlaps with any others in the world
     * after being snapped to the grid based on its current location
     * @param id ID / index of the building in the world building store
     * @return true if the building overlaps with another, else false
     */
    public boolean doesBuildingOverlap(Integer id) {
        Building overlapBuilding = getBuilding(id);

        return doesBuildingOverlap(overlapBuilding);
    }

    /**
     * Utility method to check if a building overlaps with any others in the world
     * after being snapped to the grid based on its current location
     * @param overlapBuilding The building to check for overlaps with others
     * @return true if the building overlaps with another, else false
     */
    public boolean doesBuildingOverlap(Building overlapBuilding) {
        GridCoordTuple gridCoords = overlapBuilding.getGridCoords();
        for (Building building : buildings) {
            if (building.getID() != overlapBuilding.getID()) {
                if (building.getGridX() == gridCoords.x && building.getGridY() == gridCoords.y) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Dispose of anything that needs disposing of. Duh.
     */
    public void dispose() {
        gridRenderer.dispose();
        assetManager.dispose();
    }
}
