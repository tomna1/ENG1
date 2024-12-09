package io.github.archessmn.eng1;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LeaderboardScreen implements Screen {
    private FitViewport viewport;
    private Leaderboard leaderboard = new Leaderboard(null);

    public LeaderboardScreen(Main main) {
        this.viewport = main.getViewport();
    }
    
    public void show() {

    }

    public void hide() {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void render(float delta) {
        
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
