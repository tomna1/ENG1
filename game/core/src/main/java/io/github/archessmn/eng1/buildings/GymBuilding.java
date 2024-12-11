package io.github.archessmn.eng1.buildings;

import java.util.HashMap;

import io.github.archessmn.eng1.SatisfactionContributor;
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

    @Override
    public void setConnections() {
        connections = new HashMap<>();
        connections.put(Type.HALLS, 0.0f);
    }

    @Override
    protected void setSatisfactionContributor() {

        satisfactionContributor = new SatisfactionContributor(this, 
        2.5f, 
        0.0f, 
        0.2f, 
        0.0f
        );
    }
}
