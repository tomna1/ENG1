package io.github.archessmn.eng1.buildings;

import io.github.archessmn.eng1.World;

/**
 * Wrapper of {@link Building} that creates a building with the OFFICES type.
 */
public class OfficeBuilding extends Building {
    public OfficeBuilding(World world) {
        super(world, Type.OFFICES, 840, 40, 60, 60, 10f, true);
    }

    @Override
    public Building makeCopy(){
        return new OfficeBuilding(getWorld());
    }

    @Override
    public void setConnections() {
        connections.put(Type.LECTURE_HALL, 0.0f);
        connections.put(Type.PIAZZA, 0.0f);
    }
}
