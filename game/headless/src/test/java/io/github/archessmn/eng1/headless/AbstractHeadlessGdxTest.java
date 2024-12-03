package io.github.archessmn.eng1.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

/**
 * Extend from this class if you want to test you are doing to create a
 * headless version of the game (the game simulates without any drawing taking)
 * place.
 */
public abstract class AbstractHeadlessGdxTest {
    @BeforeEach
    public void setup() {
        // THE NEXT LINE CAUSES THE TESTS TO RUN INFINITELY BUT CANNOT BE REMOVED
        // OTHERWISE CAUSES EXCEPTIONS.
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        HeadlessLauncher.main(new String[0]);
    }
}