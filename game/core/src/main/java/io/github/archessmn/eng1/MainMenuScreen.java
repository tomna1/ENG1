package io.github.archessmn.eng1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen extends ConcreteScreen {
    private Main main;
    private MainMenu mainMenu;

    public MainMenuScreen(Main main) {
        super(main.getViewport());
        this.main = main;
        mainMenu = new MainMenu(main, viewport);
    }
    
    @Override
    public void show() {
        mainMenu.setAsInputProcesser();;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        mainMenu.draw();
    }

    @Override
    public void dispose() {
        mainMenu.dispose();
    }
}
