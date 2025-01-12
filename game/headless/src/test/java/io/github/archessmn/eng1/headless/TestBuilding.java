package io.github.archessmn.eng1.headless;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.World;
import io.github.archessmn.eng1.buildings.Building;
import io.github.archessmn.eng1.buildings.GymBuilding;

public class TestBuilding extends AbstractHeadlessGdxTest {
    private World world;

    @BeforeEach
    void init() {
        world = new World(100, 100); // use a test world.
    }

    @Test
    // This tests checks that when a building is created it is assigned the corect
    // type.
    public void TestCorrectBuildingType() {

        GymBuilding gymBuilding = new GymBuilding(world); // Verify the building's type is set to GYM
        assertEquals(Building.Type.GYM, gymBuilding.getBuildingType());
        // when you have an instance of a GYM building
        // it should be reflected in the getBuildingType() option
        // if it isn't then this could affect the counters as it is not stored as the
        // right building type

    }

}
