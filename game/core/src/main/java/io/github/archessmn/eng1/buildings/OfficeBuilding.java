package io.github.archessmn.eng1.buildings;

import java.util.HashMap;

import io.github.archessmn.eng1.core.World;
import io.github.archessmn.eng1.satisfaction.SatisfactionContributor;

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
        connections.put(Type.LECTURE_HALL, 8f);
        connections.put(Type.PIAZZA, 7f);
    }

    @Override
    protected void setSatisfactionContributor() {
        
        satisfactionContributor = new SatisfactionContributor(this, 
        0.0f, 
        0.0f, 
        0.0f, 
        0.0f
        );
    }
}
