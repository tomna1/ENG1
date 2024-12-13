package io.github.archessmn.eng1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    public static final int VIEWPORT_WIDTH = 960;
    public static final int VIEWPORT_HEIGHT = 540;
    private MainMenuScreen mainMenu;
    private GameScreen gameScreen;
    private FitViewport viewport;

    @Override
    public void create() {
        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        mainMenu = new MainMenuScreen(this);
        this.setScreen(mainMenu);
    }

    public void startGame() {
        gameScreen = new GameScreen(this);
        this.setScreen(gameScreen);
        mainMenu.dispose();
    }

    public void viewLeaderboard() {
        System.out.println("view leaderboard");
    }

    /**
     * Returns a reference to the viewport that should be used by all screens.
     * @return The viewport.
     */
    public FitViewport getViewport() {
        return viewport;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        screen.dispose();
    }
}
