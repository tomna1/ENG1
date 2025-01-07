package io.github.archessmn.eng1.buildings;

import java.util.HashMap;

import io.github.archessmn.eng1.SatisfactionContributor;
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
        connections = new HashMap<>();
        connections.put(Type.PIAZZA, 5f);
    }

    @Override
    protected void setSatisfactionContributor() {
        
        satisfactionContributor = new SatisfactionContributor(this, 
        3.5f, 
        3.5f, 
        0.5f, 
        0.7f
        );
    }
}
