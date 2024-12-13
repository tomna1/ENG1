package io.github.archessmn.eng1.buildings;

import java.util.HashMap;

import io.github.archessmn.eng1.SatisfactionContributor;
import io.github.archessmn.eng1.World;

/**
 * Wrapper of {@link Building} that creates a building with the HALLS type.
 */
public class HallsBuilding extends Building {

    public HallsBuilding(World world) {
        super(world, Type.HALLS, 720, 40, 60, 60, 10f, true);
    }

    @Override
    public Building makeCopy(){
        return new HallsBuilding(getWorld());
    }

    @Override
    public void setConnections() {
        connections = new HashMap<>();
        connections.put(Type.GYM, 0.2f);
        connections.put(Type.LECTURE_HALL, 0.5f);
        connections.put(Type.OFFICES, 0.4f);
        connections.put(Type.PIAZZA, 0.4f);
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
