package io.github.archessmn.eng1.events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Event {
    public int eventStart(int elapsedTime);
    public int eventMain(int elapsedTime);
    public int eventEnd();
    public void draw(SpriteBatch batch);
}
