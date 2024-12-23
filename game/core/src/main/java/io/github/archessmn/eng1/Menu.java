package io.github.archessmn.eng1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Inherit from this class to create a basic menu.
 */
public class Menu {
    protected Stage stage;
    protected Table table;
    protected Viewport viewport;

    public Menu(Viewport viewport) {
        if (viewport == null) throw new IllegalArgumentException("viewport cannot be null");
        this.viewport = viewport;
        table = new Table();
        stage = new Stage(viewport);
        stage.addActor(table);
    } 

    public void setAsInputProcesser() {
        Gdx.input.setInputProcessor(stage);
    }

    public void draw() {
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
