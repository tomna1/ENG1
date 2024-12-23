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
    private Array<Building> buildings = new Array<>();
    private HashMap<Building.Use, Integer> buildingUseCounts = new HashMap<>();
    private HashMap<Building.Type, Integer> buildingTypeCounts = new HashMap<>();

    /**
     * Creates a new world which can be used to store buildings.
     * 
     * @param worldWidth Width of the world. Must be greater than 0.
     * @param worldHeight Height of the world. Must be greater than 0.
     * 
     */
    public World(Integer worldWidth, Integer worldHeight) {
        if (worldWidth <= 0) throw new IllegalArgumentException("World Width should be greater than 0.");
        if (worldHeight <= 0) throw new IllegalArgumentException("World Height should be greater than 0.");
        this.width = worldWidth;
        this.height = worldHeight;

        for (Building.Use use : Building.Use.values()) {
            buildingUseCounts.put(use, 0);
        }
        for (Building.Type type : Building.Type.values()) {
            buildingTypeCounts.put(type, 0);
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
    
    /**
     * Returns the width of the world as defined in the constructor..
     * @return World width, always greater than 0.
     */
    public int getWidth() {
        return this.width;
    }
    
    /**
     * Returns the height of the world as defined in the constructor.
     * @return World height, always greater than 0.
     */
    public int getHeight() {
        return this.height;
    }

    public int getBuildingCount() {
        return buildings.size;
    }

    public int getBuildingCount(Building.Use use) {
        return buildingUseCounts.get(use);
    }

    public int getBuildingCount(Building.Type type) {
        return buildingTypeCounts.get(type);
    }

    /**
     * Draws the grid using {@link GridUtils}
     */
    public void drawGrid() {
        // This is here instead of constructor because it breaks some of the
        // tests and this method is not called in the tests.
        if (gridRenderer == null) {
            gridRenderer = new ShapeRenderer();
        }
        GridUtils.drawGrid(gridRenderer);
    }

    /**
     * Adds a building to the world building store and returns its location in the store
     * @param building Building to add to the world
     * @return The index of the building in the world store
     */
    public int addBuilding(Building building) {
        buildings.add(building);
        int oldUseCount = buildingUseCounts.get(building.getBuildingUse());
        buildingUseCounts.put(building.getBuildingUse(), oldUseCount+1);

        int oldTypeCount = buildingTypeCounts.get(building.getBuildingType());
        buildingTypeCounts.put(building.getBuildingType(), oldTypeCount+1);

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
     * Gets the array of all buildings in the world.
     * @return The array buildings containing each building object.
     */
    public Array<Building> getBuildings(){
        return buildings;
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
            if (building.getGridX() == gridCoords.x && building.getGridY() == gridCoords.y) {
                return true;
            }
        }
        return false;
    }

    public boolean doesBuildingOverlap(int x, int y) {
        for (Building building : buildings) {
            if ((x >= building.getX() && x <= building.getX()+building.getWidth()) && 
                (y >= building.getY() && y <= building.getY()+building.getHeight())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Dispose of anything that needs disposing of. Duh.
     */
    public void dispose() {
        if (gridRenderer != null) {
            gridRenderer.dispose();    
        }
        assetManager.dispose();
    }
}
