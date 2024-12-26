package io.github.archessmn.eng1.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.archessmn.eng1.World;
import io.github.archessmn.eng1.buildings.Building;
import io.github.archessmn.eng1.buildings.Building.Use;

/**
 * Randomly adds or takes 5 student satisfaction
 * and displays a pop up on the screen to inform the player.
 */
public class GoodBadLecture implements Event {
    private World world;
    private Stage stage;
    private Image newsPage;
    private Texture pageTexture;
    private int startTime;
    private boolean positive;

    /**
     * creates an instance of the event and sets up Image.
     * 
     * @param world      World used to check number of lecture halls.
     * @param stage      Stage to place images on.
     * @param randomBool random true or false to determine if student satisfaction
     *                   should increase or decrease.
     */
    public GoodBadLecture(World world, Stage stage, boolean randomBool) {
        this.world = world;
        this.stage = stage;
        this.positive = randomBool;

        if (positive) {
            pageTexture = new Texture(Gdx.files.internal("newsGreatLecture.png"));
        } else {
            pageTexture = new Texture(Gdx.files.internal("newsAwfulLecture.png"));
        }
        newsPage = new Image(pageTexture);
        newsPage.setSize(400, 205);
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
        stage.addActor(newsPage);
        newsPage.setPosition(0, 0);

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
        newsPage.remove();
        return 0;
    }

}
