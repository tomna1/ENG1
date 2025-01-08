package io.github.archessmn.eng1;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.archessmn.eng1.leaderboard.Leaderboard;
import io.github.archessmn.eng1.leaderboard.LeaderboardMenu;

/**
 * This is the screen that shows the leaderboard. It contains the leaderboard
 * and leaderboard menu.
 */
public class LeaderboardScreen implements Screen {
    private FitViewport viewport;
    private Leaderboard leaderboard;
    private LeaderboardMenu leaderboardMenu;

    /**
     * Creates a new leaderboard screen.
     * @param main Reference to Main. Cannot be null.
     */
    public LeaderboardScreen(Main main) {
        if (main == null) throw new IllegalArgumentException("main cannot be null");
        this.viewport = main.getViewport();
        String leaderboardFileDir = "ENG1/leaderboard";
        leaderboard = new Leaderboard(leaderboardFileDir);
        leaderboardMenu = new LeaderboardMenu(main, leaderboard);
        leaderboardMenu.setAsInputProcessor();
    }
    
    @Override
    public void show() {
        leaderboardMenu.setAsInputProcessor();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        leaderboardMenu.draw(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /**
     * Returns a reference to the leaderboard used by the menu.
     * @return
     */
    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    @Override
    public void dispose() {
        leaderboardMenu.dispose();
    }
}
