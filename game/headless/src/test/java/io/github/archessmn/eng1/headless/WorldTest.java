package io.github.archessmn.eng1.headless;

import org.junit.jupiter.api.Test;
import io.github.archessmn.eng1.World;

import static org.junit.jupiter.api.Assertions.*;

public class WorldTest extends AbstractHeadlessGdxTest {
    private World world;

    @Test
    public void worldDimensionsTest() {
        world = new World(1, 1, 13, 23);
        assertEquals(23, world.height,
            "The world height should be the same as the number in its constructor.");
        assertEquals(13, world.width,
            "The world width should be the same as the number in its constructor.");
    }
}
