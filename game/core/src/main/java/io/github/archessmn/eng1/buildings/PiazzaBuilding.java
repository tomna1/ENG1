package io.github.archessmn.eng1.buildings;

import java.util.HashMap;

import io.github.archessmn.eng1.SatisfactionContributor;
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

    @Override
    public void setConnections() {
        connections = new HashMap<>();
        connections.put(Type.HALLS, 0.0f);
        connections.put(Type.LECTURE_HALL, 0.0f);
        connections.put(Type.OFFICES, 0.0f);
    }

    @Override
    protected void setSatisfactionContributor() {
        
        satisfactionContributor = new SatisfactionContributor(this, 
        3.5f, 
        2.5f, 
        null, 
        null
        );
    }
}
