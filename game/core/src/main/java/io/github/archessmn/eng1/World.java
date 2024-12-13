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

    public Array<Building> buildings = new Array<>();
    public HashMap<Building.Use, Integer> buildingUseCounts = new HashMap<>();
    public HashMap<Building.Type, Integer> buildingTypeCounts = new HashMap<>();

    private Integer width, height;
    private Integer studentCount, teacherCount;

    private SatisfactionManager satisfactionManager;

    /**
     * Creates a new world which can be used to store buildings.
     * 
     * @param worldWidth Width of the world. Must be greater than 0.
     * @param worldHeight Height of the world. Must be greater than 0.
     * 
     */
    public World(Integer worldWidth, Integer worldHeight) {
    
        testWorldDimensions(worldWidth, worldHeight);
        this.width = worldWidth;
        this.height = worldHeight;
        studentCount = 0;
        teacherCount = 0;
        satisfactionManager = new SatisfactionManager(this);

        initializeBuildingCounts();
        loadAssests();
    }

    private void testWorldDimensions(Integer worldWidth, Integer worldHeight){
        if (worldWidth <= 0) throw new IllegalArgumentException("World Width should be greater than 0.");
        if (worldHeight <= 0) throw new IllegalArgumentException("World Height should be greater than 0.");
    }

    private void initializeBuildingCounts(){
        for (Building.Use use : Building.Use.values()) {
            buildingUseCounts.put(use, 0);
        }
        for(Building.Type type : Building.Type.values()){
            buildingTypeCounts.put(type, 0);
        }
    }

    private void loadAssests(){
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
     * Adds a building to the world building store and returns its location in the store
     * @param building Building to add to the world
     * @return The index of the building in the world store
     */
    public int addBuilding(Building building) {
        buildings.add(building);
        return buildings.size - 1;
    }

    public void updateWorld(Building newBuilding){
        updateBuildingConnections();
        satisfactionManager.updateSatisfaction();
    }

    private void printCounts(){
        for(Building.Type type : buildingTypeCounts.keySet()){
            System.out.println(String.format("Type: %-15s Count: %d", type.name(),  buildingTypeCounts.get(type)));
        }
    }

    public Integer getTypeCount(Building.Type type){
        return buildingTypeCounts.get(type);
    }

    public Float ratioToType(Building.Type typeOne, Building.Type typeTwo){
        Integer typeCount = getTypeCount(typeTwo);
        if(typeCount == 0){
            return null;
        }
        else{
            return (float) getTypeCount(typeOne) / typeCount;
        }
    }



    private void updateBuildingConnections(){
        for(Building building : buildings){
            building.updateConnectedBuildings();
        }
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
     * Draw all the buildings into the world.
     */
    public void drawbuildings(SpriteBatch batch) {
        batch.begin();
        for (Building building : buildings) building.draw(batch);
        batch.end();
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
     * Dispose of anything that needs disposing of. Duh.
     */
    public void dispose() {
        gridRenderer.dispose();
        assetManager.dispose();
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

    public HashMap<Building.Use, Integer> getBuildingUseCounts(){
        return buildingUseCounts;
    }

    public HashMap<Building.Type, Integer> getBuildingTypeCount(){
        return buildingTypeCounts;
    }

    public Integer getStudentCount(){
        return studentCount;
    }

    public Integer getTeacherCount(){
        return teacherCount;
    }
}
