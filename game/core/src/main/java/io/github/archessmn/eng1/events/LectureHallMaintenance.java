package io.github.archessmn.eng1.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.github.archessmn.eng1.World;
import io.github.archessmn.eng1.buildings.Building;
import io.github.archessmn.eng1.buildings.Building.Use;

/**
 * Displays a pop up saying that a lecture hall has shut down and
 * gives the player 2 choices.
 * 
 * Move lectures online: -10 student satisfaction.
 * 
 * Use other lecture halls: -20 baseline but +4 for every hall that has
 * been placed, to a maximum of -2.
 * 
 * If the player hasn't made a decision in 10 seconds the event ends
 * and they get -25 satisfaction
 */
class LectureHallMaintenance implements Event {
    private final World world;
    private final Stage stage;
    private final Sprite pageSprite;
    private final Texture pageTexture;
    private ImageButton onlineButton, otherHallsButton;
    private int startTime, pageX, pageY;
    private boolean choseOnline, choiceMade;

    /**
     * creates an instance of the event and sets up sprites and buttons ready
     * to start.
     * 
     * @param world World used to check number of lecture halls.
     * @param stage Stage used to place ImageButtons on the screen.
     */
    public LectureHallMaintenance(World world, Stage stage) {
        System.out.println("hall");
        this.world = world;
        this.stage = stage;
        this.pageTexture = new Texture(Gdx.files.internal("maintenance.png"));
        this.pageSprite = new Sprite(this.pageTexture);
        this.pageSprite.setSize(400, 225);
        this.pageX = -1000;
        this.pageY = -1000;

        buildButtons();
    }

    /**
     * Checks if lecture halls are present before placing page and ImageButtons
     * on the screen and recording the start time.
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

        stage.addActor(onlineButton);
        stage.addActor(otherHallsButton);
        startTime = elapsedTime;
        pageX = 0;
        pageY = 0;

        return 1;
    }

    /**
     * run the specified action of the players decision, or the time out option
     * if they take too long to decide.
     * 
     * @param elapsedTime seconds spent unpaused.
     * @return 2 to tell eventManager to end the event.
     * @return 1 to keep waiting for a player decision.
     */
    public int eventMain(int elapsedTime) {
        if (choiceMade) {
            if (choseOnline) {
                // student satisfaction -10
                System.out.println("online");
            } else {
                int satisfactionChange = -20;
                for (Building building : world.getBuildings()) {
                    if (building.getBuildingUse() == Use.LEARN) {
                        satisfactionChange += 4;
                    }
                }
                if (satisfactionChange > -2) {
                    satisfactionChange = -2;
                }
                System.out.println(satisfactionChange);
                // student satisfaction + satisfactionChange
            }
            return 2;
        }
        if (elapsedTime == startTime + 10) {
            // student satisfaction -25
            System.out.println("did nothing");
            return 2;
        }
        return 1;
    }

    /**
     * removes the page and buttons from the screen.
     * 
     * @return 0 to tell eventManager the event has finished.
     */
    public int eventEnd() {
        pageX = -1000;
        pageY = -1000;
        onlineButton.setVisible(false);
        otherHallsButton.setVisible(false);
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

    /**
     * set the textures, events and positions of the ImageButtons.
     */
    private void buildButtons() {
        Texture onlineButtonTextureUp = new Texture(Gdx.files.internal("online_up.png"));
        Texture onlineButtonTextureDown = new Texture(Gdx.files.internal("online_down.png"));
        Texture otherHallsTextureUp = new Texture(Gdx.files.internal("other_halls_up.png"));
        Texture otherHallsTextureDown = new Texture(Gdx.files.internal("other_halls_down.png"));

        Drawable onlineButtonUp = new TextureRegionDrawable(onlineButtonTextureUp);
        Drawable onlineButtonDown = new TextureRegionDrawable(onlineButtonTextureDown);
        Drawable otherHallsButtonUp = new TextureRegionDrawable(otherHallsTextureUp);
        Drawable otherHallsButtonDown = new TextureRegionDrawable(otherHallsTextureDown);

        onlineButton = new ImageButton(onlineButtonUp, onlineButtonDown, onlineButtonUp);
        otherHallsButton = new ImageButton(otherHallsButtonUp, otherHallsButtonDown, otherHallsButtonUp);

        onlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent onlineEvent, float x, float y) {
                choseOnline = true;
                choiceMade = true;
            }
        });
        otherHallsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent onlineEvent, float x, float y) {
                choseOnline = false;
                choiceMade = true;
            }
        });

        onlineButton.setPosition(690, 190);
        otherHallsButton.setPosition(690, 95);

        onlineButton.setSize(240, 135);
        otherHallsButton.setSize(240, 135);

    }
}
