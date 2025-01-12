package io.github.archessmn.eng1.headless;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.World;
import io.github.archessmn.eng1.buildings.Building;
import io.github.archessmn.eng1.buildings.GymBuilding;

public class GameScreenTest extends AbstractHeadlessGdxTest {
    private World world;

    @BeforeEach
    void init() {
        world = new World(100, 100); // use a test world.
    }

    @Test
    // This tests checks that a building created and added to the world can be
    // successfully retrieved.
    public void testAddBuilding() {
        // Create a Gym building
        GymBuilding gymBuilding = new GymBuilding(world);

        // Add the building to the world
        int buildingId = world.addBuilding(gymBuilding); // Assuming it returns an ID

        // Assert that the building is added correctly by ID
        Building retrievedBuilding = world.getBuilding(buildingId);
        assertNotNull(retrievedBuilding, "Building should be added and retrievable by ID");
        assertEquals(gymBuilding, retrievedBuilding, "Retrieved building should match the added building");

    }

}
