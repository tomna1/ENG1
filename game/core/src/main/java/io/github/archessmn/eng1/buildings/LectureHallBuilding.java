package io.github.archessmn.eng1.buildings;

import io.github.archessmn.eng1.World;

/**
 * Wrapper of {@link Building} that creates a building with the LECTURE_HALL type.
 */
public class LectureHallBuilding extends Building {
    public LectureHallBuilding(World world) {
        super(world, Type.LECTURE_HALL, 780, 40, 60, 60, 10f, true);
    }

    @Override
    public Building makeCopy(){
        return new LectureHallBuilding(getWorld());
    }

    @Override
    public void setConnections() {
        connections.put(Type.PIAZZA, 0.0f);
    }
}
