package io.github.archessmn.eng1.headless;

import org.junit.jupiter.api.Test;
import io.github.archessmn.eng1.World;

import static org.junit.jupiter.api.Assertions.*;

public class WorldTest extends AbstractHeadlessGdxTest {
    private World world;

    @Test
    public void worldDimensionsTest() {
        System.out.println("something");
        world = new World(1, 1, 13, 23);
        System.out.println("something2");
        assertEquals(23, world.getHeight(),
            "The world height should be the same as the number in its constructor.");
        assertEquals(13, world.getWidth(),
            "The world width should be the same as the number in its constructor.");
    }
}
