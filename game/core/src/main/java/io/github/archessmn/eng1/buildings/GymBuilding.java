package io.github.archessmn.eng1.buildings;

import io.github.archessmn.eng1.World;

/**
 * Wrapper of {@link Building} that creates a building with the GYM type.
 */
public class GymBuilding extends Building {
    public GymBuilding(World world) {
        super(world, Type.GYM, 660, 40, 60, 60, 10f, true);
    }

    @Override
    public Building makeCopy(){
        return new GymBuilding(getWorld());
    }
}
