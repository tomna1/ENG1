package io.github.archessmn.eng1.buildings;

import java.util.HashMap;

import io.github.archessmn.eng1.SatisfactionContributor;
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
        connections = new HashMap<>();
        connections.put(Type.LECTURE_HALL, 0.0f);
        connections.put(Type.PIAZZA, 0.0f);
    }

    @Override
    protected void setSatisfactionContributor() {
        
        satisfactionContributor = new SatisfactionContributor(this, 
        2.5f, 
        0.0f, 
        0.3f, 
        0.0f
        );
    }
}
