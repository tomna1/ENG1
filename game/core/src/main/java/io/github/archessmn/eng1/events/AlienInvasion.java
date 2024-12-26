package io.github.archessmn.eng1.events;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import io.github.archessmn.eng1.World;
import io.github.archessmn.eng1.buildings.Building;

/**
 * A UFO stops on random buildings and starts a 5 second timer, if the player
 * hasn't clicked on the UFO 5 times in that timer it takes 5 satisfaction.
 * If the player does click the UFO, then it flies to another building.
 * 
 */
public class AlienInvasion implements Event {
    private World world;
    private Stage stage;
    private Texture pageTexture;
    private Image newsPage;
    private int clickCount, startTime, tractorStart;
    private float UFOx, UFOy;
    private float[][] nextBuildingPos = new float[2][2];
    private boolean starting, tractorActive, hit;
    private Drawable UFOidle, UFOactive, UFOhit;
    private Image UFO;
    private Random rand;
    private Building currentBuilding;

    /**
     * sets up the news page and the UFO ready for the event to start.
     * 
     * @param world World used to get building and coordinates.
     * @param stage Stage used to draw the page and the UFO on.
     */
    public AlienInvasion(World world, Stage stage) {
        this.world = world;
        this.stage = stage;

        pageTexture = new Texture(Gdx.files.internal("newsUFO.png"));
        newsPage = new Image(pageTexture);
        newsPage.setSize(400, 272);
        tractorActive = false;
        rand = new Random();

        UFOx = -45f;
        UFOy = -45f;

        starting = false;

        buildUFO();

    }

    /**
     * if there is more than 2 buildings, display the news page for the user to
     * read and after 6 seconds start the event.
     * 
     * @param elapsedTime seconds spent unpaused
     * @return -1 if there is less than 2 buildings placed to report that
     *         the event could not start.
     * @return 0 if the news page has been displayed for less than 6 seconds
     *         to tell the eventManager not to run the main body of the event yet.
     * @return 1 once the news page is finished displaying, to run the next part
     *         of the event.
     */
    public int eventStart(int elapsedTime) {
        if (!starting) {
            Array<Building> buildings = world.getBuildings();
            if (buildings.size < 2) {
                return -1;
            }
            starting = true;
            startTime = elapsedTime;
            stage.addActor(newsPage);
            newsPage.setPosition(0, 0);
        }

        if (elapsedTime == startTime + 6) {
            startTime = elapsedTime;
            newsPage.remove();

            stage.addActor(UFO);
            nextBuildingPos = getNextBuildingPos();

            return 1;
        }
        return 0;
    };

    /**
     * moves the UFO around the screen and checks if it is active.
     * If the UFO is active set its drawable depending on its state, and check
     * if student satisfaction needs to be removed, or if it needs to move to
     * the next building.
     * 
     * @param elapsedTime seconds spent unpaused.
     * @return 1 if less than 20 seconds have passed to keep the event running.
     * @return 2 once the main body of the event has finished
     */
    public int eventMain(int elapsedTime) {
        if (tractorActive) {
            if (elapsedTime == (tractorStart + 5)) {
                System.out.println("-5 satisfaction");
                // -5 student satisfaction
            }
            if (clickCount >= 5) {
                tractorActive = false;
                clickCount = 0;

                UFO.setDrawable(UFOidle);

                nextBuildingPos = getNextBuildingPos();
            }

            if (!hit) {
                if (elapsedTime % 2 == 0) {
                    UFO.setDrawable(UFOactive);
                } else {
                    UFO.setDrawable(UFOidle);
                }
            }
            hit = false;

        } else {
            UFOx += nextBuildingPos[1][0];
            UFOy += nextBuildingPos[1][1];
            UFO.setPosition(UFOx, UFOy);

            if (Math.abs(UFOx - nextBuildingPos[0][0]) < 0.02
                    && Math.abs(UFOy - nextBuildingPos[0][1]) < 0.02) {
                tractorActive = true;
                tractorStart = elapsedTime;
            }
        }

        if (elapsedTime == (startTime + 20)) {
            tractorActive = false;

            float nextX = 500;
            float nextY = 545;

            float xSpeed = (nextX - UFOx) / 120;
            float ySpeed = (nextY - UFOy) / 120;

            nextBuildingPos[0][0] = nextX;
            nextBuildingPos[0][1] = nextY;
            nextBuildingPos[1][0] = xSpeed;
            nextBuildingPos[1][1] = ySpeed;
            return 2;
        }

        return 1;
    };

    /**
     * move the UFO off the screen before removing the UFO from the stage and
     * ending the event.
     * 
     * @return 0 once the UFO is off screen to end the event.
     * @return 2 if the UFO is still moving off screen.
     */
    public int eventEnd() {
        UFOx += nextBuildingPos[1][0];
        UFOy += nextBuildingPos[1][1];
        UFO.setPosition(UFOx, UFOy);

        if (Math.abs(UFOx - nextBuildingPos[0][0]) < 0.02
                && Math.abs(UFOy - nextBuildingPos[0][1]) < 0.02) {
            UFO.remove();
            return 0;
        } else {
            return 2;
        }
    };

    /**
     * Gets the coordinates and the speeds the UFO needs to travel to and at.
     * @return a 2d array with the coordinates and the speed
     */
    public float[][] getNextBuildingPos() {
        Array<Building> buildings = world.getBuildings();
        int index;
        Building nextBuilding = currentBuilding;
        while (nextBuilding == currentBuilding) {
            index = rand.nextInt(buildings.size);
            nextBuilding = buildings.get(index);
        }
        currentBuilding = nextBuilding;
        float nextBuildingX = nextBuilding.getX() + 10;
        float nextBuildingY = nextBuilding.getY() + 25;

        float xSpeed = (nextBuildingX - UFOx) / 180;
        float ySpeed = (nextBuildingY - UFOy) / 180;

        float[][] nextBuildingPos = { { nextBuildingX, nextBuildingY }, { xSpeed, ySpeed } };

        return nextBuildingPos;

    }

    /**
     * set the textures and events of the UFO.
     */
    private void buildUFO() {
        Texture UFOidleTexture = new Texture(Gdx.files.internal("UFOidle.png"));
        Texture UFOactiveTexture = new Texture(Gdx.files.internal("UFOactive.png"));
        Texture UFOhitTexture = new Texture(Gdx.files.internal("UFOhit.png"));

        UFOidle = new TextureRegionDrawable(UFOidleTexture);
        UFOactive = new TextureRegionDrawable(UFOactiveTexture);
        UFOhit = new TextureRegionDrawable(UFOhitTexture);

        UFO = new Image(UFOidle);

        UFO.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent hitEvent, float x, float y) {
                if (tractorActive) {
                    UFO.setDrawable(UFOhit);
                    hit = true;
                    clickCount += 1;
                }
            }
        });

        UFO.setPosition(UFOx, UFOy);
        UFO.setSize(40, 28);

    }
}
