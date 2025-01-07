package io.github.archessmn.eng1.buildings;

import java.util.HashMap;
//import java.util.stream.Gatherer.Integrator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.archessmn.eng1.util.GridCoordTuple;
import io.github.archessmn.eng1.util.GridUtils;
import io.github.archessmn.eng1.SatisfactionContributor;
import io.github.archessmn.eng1.World;

/**
 * Base class for each building type ({@link io.github.archessmn.eng1.buildings.Building.Type}),
 * stores information about the building and provides utility classes for interacting with it.
 */
public abstract class Building {
    /**
     * Defines the type and, by inference, the {@link Use}
     * and sprite of a building.
     */
    public enum Type {
        GYM, HALLS, LECTURE_HALL, OFFICES, PIAZZA
    }

    /**
     * The use of a building, inferred from its {@link Type}
     */
    public enum Use {
        SLEEP, LEARN, EAT, RECREATION
    }

    private Type buildingType;
    private final int id;
    private final World world;
    
    private float x, y;
    private int gridX, gridY;
    private float width, height;
    private float timeUntilBuilt;

    private boolean placed, built;

    private Rectangle bounds;
    private final Sprite sprite;
    private Sprite unbuiltSprite;

    protected HashMap<Type, Float> connections;
    private HashMap<Building, Integer> connectedBuildings;

    protected SatisfactionContributor satisfactionContributor;
   
    /**
     * Initialises a new building.
     * @param world The {@link World} that the building is a part of.
     * @param buildingType The {@link Type} of the building.
     * @param x The X coordinate to place the building at.
     * @param y The Y coordinate to place the building at.
     * @param width Width of the building.
     * @param height Height of the building.
     * @param timeUntilBuilt Time until the building is marked as built.
     * @param built Whether the building should be marked as built upon creation.
     */
    public Building(World world, Type buildingType, float x, float y, float width, float height, float timeUntilBuilt, boolean built) {
        this.world = world;
        this.buildingType = buildingType;
        this.id = world.buildings.size - 1;
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        placed = false;
        this.bounds = new Rectangle(this.x, this.y, this.width, this.height);

        this.timeUntilBuilt = timeUntilBuilt;
        this.built = built;
        if (timeUntilBuilt > 0) {
            this.unbuiltSprite = new Sprite(world.assetManager.get("construction.png", Texture.class));
            this.unbuiltSprite.setSize(width, height);
        }

        String spriteFileName = Building.getFileOfType(buildingType);
        this.sprite = new Sprite(world.assetManager.get(spriteFileName, Texture.class));
        this.sprite.setSize(width, height);
        this.bounds = new Rectangle(this.x, this.y, this.width, this.height);

        connections = new HashMap<>();
        connectedBuildings = new HashMap<>();

        setConnections();
        setSatisfactionContributor();
    }

    /**
     * Usually called once per frame to run any updates the building may need,
     * such as reducing time until built.
     * @param deltaTime The amount of time to advance by.
     */
    public void tick(float deltaTime) {
        this.setX(MathUtils.clamp(this.x, 0, world.getWidth() - this.width));
        this.setY(MathUtils.clamp(this.y, 0, world.getHeight() - this.height));

        if (placed && timeUntilBuilt > 0) this.timeUntilBuilt -= deltaTime;
        if (timeUntilBuilt <= 0) built = true;
    }

    /**
     * Usually called once per frame to run any updates the building may need,
     * such as reducing time until built. Uses Gdx.graphics.getDeltaTime() to
     * get the amount of time to advance by.
     */
    public void tick() {
        this.tick(Gdx.graphics.getDeltaTime());
    }

    /**
     * Called when placing the building into the world, will check if the building
     * overlaps and cancel if it does, returning false. Will snap the building to
     * the grid and update its grid coordinates to match its position.
     * @return true if placing the building was successful.
     */
    public boolean place() {
        if (world.doesBuildingOverlap(this)) {
            return false;
        }
        this.snapToGrid();
        GridCoordTuple gridCoord = GridUtils.getGridCoords(this.x, this.y);
        this.gridX = gridCoord.x;
        this.gridY = gridCoord.y;

        this.placed = true;

        return true;
    }

    /**
     * Snaps the building to the grid.
     */
    public void snapToGrid() {
        Vector2 gridCoords = getRawGridCoords();
        this.setCenter(gridCoords.x, gridCoords.y);
    }

    /**
     * Used to update the position of the building in the world.
     * @param x The X position to use
     * @param y The Y position to use
     */
    public void setCenter(float x, float y) {
        this.x = x - this.width / 2;
        this.y = y - this.height / 2;
    }

    public void updateConnectedBuildings(){

        connectedBuildings = new HashMap<>();
        for(Building otherBuilding : new Array<Building>(world.getBuildings())){

            if(isConnectedBuilding(otherBuilding)){

                connectedBuildings.put(otherBuilding, getManhattenDistanceFrom(otherBuilding));
            }
        }
    }

    public boolean isConnectedBuilding(Building building){
        return connections.containsKey(building.getBuildingType());
    }

    /**
     * Makes an un-built deep copy of the current building type
     * @return A copy of the building.
     */
    public abstract Building makeCopy();

    protected abstract void setSatisfactionContributor();

    public SatisfactionContributor getSatisfactionContributor(){
        return satisfactionContributor;
    }

    /**
     * Draws the building into the game using the provided {@link SpriteBatch}.
     * @param batch The {@link SpriteBatch} to use to draw the building.
     */
    public void draw(SpriteBatch batch) {
        if (timeUntilBuilt > 0 && !built) {
            unbuiltSprite.setPosition(x, y);
            unbuiltSprite.draw(batch);
            return;
        }
        sprite.setPosition(this.x, this.y);
        sprite.draw(batch);
    }

    // Sets the connections for this building, holding the building type, and its weighting for this building
    protected abstract void setConnections();
    
    public HashMap<Type, Float> getConnections(){
        return connections;
    }

    public HashMap<Building, Integer> getConnectedBuildingsOfType(Type type){

        HashMap<Building, Integer> connectionBuildingsOfType = new HashMap<>(); 
        for(Building building : connectedBuildings.keySet()){
            if(building.getBuildingType() == type){
                connectionBuildingsOfType.put(building, connectedBuildings.get(building));
            }
        }
        return connectionBuildingsOfType;
    }

    public Integer getClosestConnectedBuildingDistanceOfType(Type type){

        HashMap<Building, Integer> connectedBuildingsOfType = getConnectedBuildingsOfType(type);

        Integer closestConnectedDistance = Integer.MAX_VALUE;

        for(Building connectedBuilding : connectedBuildingsOfType.keySet()){

            Integer distanceToConnection = connectedBuildingsOfType.get(connectedBuilding);
            if(distanceToConnection < closestConnectedDistance){
                closestConnectedDistance = distanceToConnection;
            }
        }

        return closestConnectedDistance;
    }


    public int getID() { return this.id; }
    
    /**
     * Sets the X position of the building
     * @param x The X position to use
     */
    public void setX(float x) {
        this.x = x;
    }
    public float getX() { return this.x; }
   
    /**
     * Sets the Y position of the building
     * @param y The Y position to use
     */
    public void setY(float y) {
        this.y = y;
    }
    public float getY() { 
        return this.y;
    }

    public Vector2 getPositionVector(){
        return new Vector2(x, y);
    }

    public Integer getManhattenDistanceFrom(Building otherBuilding){
        return Math.abs(gridX - otherBuilding.getGridX()) + Math.abs(gridY - otherBuilding.getGridY());
    }

    public HashMap<Building, Integer> getConnectedBuildings(){
        return connectedBuildings;
    }

    public Float getConnectionWeight(Type type){
        return getConnections().get(type);
    }
    
    public int getGridX() {
        return this.gridX;
    }
    
    public int getGridY() {
        return this.gridY;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    /**
     * Calculates and returns the bounding box of the building.
     * @return The bounding box {@link Rectangle} of the building.
     */
    public Rectangle getBounds() {
        return this.bounds.set(this.x, this.y, this.width, this.height);
    }

    /**
     * Get the use of the building, inferred from its {@link Type}.
     * @return The {@link Use} of the building.
     */
    public Use getBuildingUse() {
        return Building.getBuildingUse(this.buildingType);
    }

    /**
     * Get the raw coordinates of the grid square the building would
     * snap to, relative to the entire viewport.
     * For example the bottom left grid position would be (0.0, 480).
     * @return A {@link Vector2} of the position on the grid
     */
    public Vector2 getRawGridCoords() {
        return GridUtils.getRawGridCoords(this.x + this.width / 2, this.y + this.height / 2);
    }

    /**
     * Get the raw coordinates of the grid square the building would
     * snap to, relative to the grid.
     * For example, the top left grid position would be (0, 8).
     * @return A {@link GridCoordTuple} of the grid position
     */
    public GridCoordTuple getGridCoords() {
        return GridUtils.getGridCoords(this.x + this.width / 2, this.y + this.height / 2);
    }


    public Type getBuildingType(){
        return buildingType;
    }

    public static Use getBuildingUse(Type buildingType) {
        return switch(buildingType) {
            case GYM -> Use.RECREATION;
            case HALLS -> Use.SLEEP;
            case LECTURE_HALL, OFFICES -> Use.LEARN;
            case PIAZZA -> Use.EAT;
        };
    }

    private static String getFileOfType(Type buildingType) {
        return switch (buildingType) {
            case GYM -> "gym.png";
            case HALLS -> "halls.png";
            case LECTURE_HALL -> "lecturehall.png";
            case OFFICES -> "offices.png";
            case PIAZZA -> "piazza.png";
        };
    }

    public World getWorld(){
        return world;
    }
}
