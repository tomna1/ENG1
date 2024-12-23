package io.github.archessmn.eng1;

import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.graphics.g2d.BitmapFont;
// import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseMenu extends Menu {
    //private BitmapFont font;
    private Label pauseLabel;
    
    public PauseMenu(Viewport viewport) {
        super(viewport);
        //font = createFont();
        Skin skin  = new Skin(Gdx.files.internal("ui/uiskin.json"));
        pauseLabel = new Label("Game is paused, pressed ESC to unpause.", skin);
        table.add(pauseLabel).center().padTop(0.0f);
        table.setFillParent(true);
    }

    /*
    private BitmapFont createFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/Arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (0.05f * Gdx.graphics.getHeight());
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    } */
}
