package io.github.archessmn.eng1.headless;

import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.Timer;
import io.github.archessmn.eng1.World;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class WorldTest extends AbstractHeadlessGdxTest {
    /**
     * Tests the world constructors and basic setters/getters.
     */
    @Test
    public void worldInit() {
        int worldWidth = 13;
        int worldHeight = 23;
        Timer timer = mock(Timer.class);
        World world = new World(worldWidth, worldHeight, timer);
        assertEquals(worldWidth, world.getWidth(),
            "The world width should be the same as the number in its constructor.");
        
        assertEquals(worldHeight, world.getHeight(),
            "The world height should be the same as the number in its constructor.");
        
        assertEquals(0, world.buildings.size,
            "There should be no buildings in the world upon creation.");
    }

    @Test
    public void worldInitIllegalWidth() {
        final int worldWidth = 0;
        final int worldHeight = 5;
        Timer timer = mock(Timer.class);
        assertThrows(IllegalArgumentException.class, () -> new World(worldWidth, worldHeight, timer),
            "Should throw IllegalArguementException when worldWidth <= 0 in constructor.");

        final int worldWidth2 = -8;
        assertThrows(IllegalArgumentException.class, () -> new World(worldWidth2, worldHeight, timer),
            "Should throw IllegalArguementException when worldWidth <= 0 in constructor.");
    }

    @Test
    public void worldInitIllegalHeight() {
        Timer timer = mock(Timer.class);
        
        final int worldWidth = 5;
        final int worldHeight = 0;
        assertThrows(IllegalArgumentException.class, () -> new World(worldWidth, worldHeight, timer),
            "Should throw IllegalArguementException when worldHeight <= 0 in constructor.");

        final int worldHeight2 = -8;
        assertThrows(IllegalArgumentException.class, () -> new World(worldWidth, worldHeight2, timer),
            "Should throw IllegalArguementException when worldHeight <= 0 in constructor.");
    }
}
