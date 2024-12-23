package io.github.archessmn.eng1;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Inherit from this and override what you need to.
 */
public class ConcreteScreen implements Screen {
    protected Viewport viewport;

    public ConcreteScreen(Viewport viewport) {
        if (viewport == null) throw new IllegalArgumentException("viewport cannot be null");
		this.viewport = viewport;
    }
    
    @Override
	public void render (float delta) {
	}

	@Override
	public void resize (int width, int height) {
        viewport.update(width, height, true);
	}

	@Override
	public void show () {
	}

	@Override
	public void hide () {
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
