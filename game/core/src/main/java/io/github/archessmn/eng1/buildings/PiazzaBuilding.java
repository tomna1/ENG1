package io.github.archessmn.eng1.buildings;

import io.github.archessmn.eng1.World;

/**
 * Wrapper of {@link Building} that creates a building with the PIAZZA type.
 */
public class PiazzaBuilding extends Building {
    public PiazzaBuilding(World world) {
        super(world, Type.PIAZZA, 900, 40, 60, 60, 10f, true);
    }

    @Override
    public Building makeCopy(){
        return new PiazzaBuilding(getWorld());
    }
}
