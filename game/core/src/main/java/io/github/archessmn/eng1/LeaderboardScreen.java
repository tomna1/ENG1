package io.github.archessmn.eng1;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.archessmn.eng1.leaderboard.Leaderboard;
import io.github.archessmn.eng1.leaderboard.LeaderboardMenu;

public class LeaderboardScreen implements Screen {
    private FitViewport viewport;
    private Leaderboard leaderboard;
    private LeaderboardMenu leaderboardMenu;

    public LeaderboardScreen(Main main) {
        if (main == null) throw new IllegalArgumentException("main cannot be null");
        this.viewport = main.getViewport();
        leaderboard = new Leaderboard();
        leaderboardMenu = new LeaderboardMenu(leaderboard, viewport);
        leaderboardMenu.setAsInputProcessor();
    }
    
    public void show() {

    }

    public void hide() {
        leaderboardMenu.setAsInputProcessor();
    }

    public void pause() {

    }

    public void resume() {

    }

    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        leaderboardMenu.draw(delta);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public void dispose() {

    }
}
