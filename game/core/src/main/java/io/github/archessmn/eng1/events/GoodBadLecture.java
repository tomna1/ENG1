package io.github.archessmn.eng1.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.archessmn.eng1.World;
import io.github.archessmn.eng1.buildings.Building;
import io.github.archessmn.eng1.buildings.Building.Use;

/**
 * Randomly adds or takes 5 student satisfaction
 * and displays a pop up on the screen to inform the player.
 */
public class GoodBadLecture implements Event {
    private final World world;
    private final Sprite pageSprite;
    private final Texture pageTexture;
    private int startTime, pageX, pageY;
    private final boolean positive;

    /**
     * creates an instance of the event and sets up sprites and buttons ready
     * to start.
     * 
     * @param world      World used to check number of lecture halls.
     * @param randomBool random true or false to determine if student satisfaction
     *                   should increase or decrease.
     */
    public GoodBadLecture(World world, boolean randomBool) {
        this.world = world;
        this.positive = randomBool;
        if (this.positive) {
            this.pageTexture = new Texture(Gdx.files.internal("greatlecture.png"));
        } else {
            this.pageTexture = new Texture(Gdx.files.internal("awfullecture.png"));
        }
        this.pageSprite = new Sprite(this.pageTexture);
        this.pageSprite.setSize(400, 225);
        this.pageX = -1000;
        this.pageY = -1000;
    }

    /**
     * Checks if lecture halls are present before placing page on the screen
     * and recording the start time.
     * 
     * @param elapsedTime The number of seconds spent unpaused.
     * @return 1 if event started successfully.
     * @return -1 if event failed due to there being no lecture halls.
     */
    public int eventStart(int elapsedTime) {
        boolean lectureHall = false;
        for (Building building : world.getBuildings()) {
            if (building.getBuildingUse() == Use.LEARN) {
                lectureHall = true;
                break;
            }
        }
        if (lectureHall != true) {
            return -1;
        }
        startTime = elapsedTime;
        pageX = 0;
        pageY = 0;

        // decrease or increase student satisfaction by 5

        return 1;
    }

    /**
     * checks if 6 seconds have passed since start of event.
     * 
     * @param elapsedTime seconds spent unpaused.
     * @return 1 if less than 6 seconds.
     * @return 2 if reached 6 seconds.
     */
    public int eventMain(int elapsedTime) {
        if (startTime + 6 == elapsedTime) {
            return 2;
        }
        return 1;
    }

    /**
     * removes page from the screen.
     * 
     * @return 0 to tell eventManager that the event is finished.
     */
    public int eventEnd() {
        pageX = -1000;
        pageY = -1000;
        return 0;
    }

    /**
     * draws the page used to notify the player.
     * 
     * @param batch adds sprite to gameScreen's batch to draw.
     */
    public void draw(SpriteBatch batch) {
        pageSprite.setPosition(pageX, pageY);

        batch.begin();
        pageSprite.draw(batch);
        batch.end();
    }

}
