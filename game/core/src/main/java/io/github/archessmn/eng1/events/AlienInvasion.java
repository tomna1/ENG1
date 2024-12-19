package io.github.archessmn.eng1.events;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AlienInvasion implements Event{

    public AlienInvasion(){
        System.out.println("alien");
    }
    public int eventStart(int elapsedTime){return -1;};
    public int eventMain(int elapsedTime){return -1;};
    public int eventEnd(){return -1;};
    public void draw(SpriteBatch batch){};
}
