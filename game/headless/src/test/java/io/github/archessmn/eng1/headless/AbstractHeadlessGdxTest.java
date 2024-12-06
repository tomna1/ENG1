package io.github.archessmn.eng1.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

/**
 * Inherit from this class if you want to test you are doing to create a
 * headless version of the game (the game works without any drawing taking
 * place).
 */
public abstract class AbstractHeadlessGdxTest {
    @BeforeEach
    public void setup() {
        // This line mocks the openGL interface which is resposible for drawing
        // things onto the screen. 
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        // This launches the game in headless mode (the render() method in
        // Main is not called.
        HeadlessLauncher.main(new String[0]);
    }
}