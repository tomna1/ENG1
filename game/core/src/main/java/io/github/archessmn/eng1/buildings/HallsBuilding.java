package io.github.archessmn.eng1.buildings;

import io.github.archessmn.eng1.World;

/**
 * Wrapper of {@link Building} that creates a building with the HALLS type.
 */
public class HallsBuilding extends Building {
    public HallsBuilding(World world, float x, float y, boolean built) {
        super(world, Type.HALLS, x, y, 60, 60, 10f, built);
    }
}
