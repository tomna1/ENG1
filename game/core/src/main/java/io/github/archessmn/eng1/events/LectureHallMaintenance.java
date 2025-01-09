package io.github.archessmn.eng1.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
 * Move lectures online: -6 student satisfaction.
 * 
 * Use other lecture halls: -11 baseline but +2.5 for every hall that has
 * been placed, to a maximum of -1.
 * 
 * If the player hasn't made a decision in 15 seconds the event ends
 * and they get -11 satisfaction
 */
public class LectureHallMaintenance implements Event {
    private World world;
    private Stage stage;
    private Image newsPage;
    private Texture pageTexture;
    private ImageButton onlineButton, otherHallsButton;
    private int startTime, eventDuration;
    private float startSatisfaction, satisfactionChange;
    private boolean choseOnline, choiceMade;

    /**
     * creates an instance of the event and sets up Images and ImageButtons ready
     * to start.
     * 
     * @param world World used to check number of lecture halls.
     * @param stage Stage used to place ImageButtons on the screen.
     */
    public LectureHallMaintenance(World world, Stage stage) {
        this.world = world;
        this.stage = stage;
        eventDuration = 15;
        startSatisfaction = -11;
        satisfactionChange = 0;
        choiceMade = false;

        pageTexture = new Texture(Gdx.files.internal("newsMaintenance.png"));
        newsPage = new Image(this.pageTexture);
        newsPage.setSize(400, 205);

        buildButtons();
    }

    /**
     * Checks if lecture halls are present before placing newsPage and ImageButtons
     * on the screen and recording the start time.
     * 
     * @param elapsedTime The number of seconds spent unpaused.
     * @return 1 if event started successfully.
     * @return -1 if event failed due to there being no lecture halls.
     */
    public int eventStart(int elapsedTime) {
        // check for any lecture halls or offices
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

        // if no learning buildings present, return -1 and end event
        stage.addActor(newsPage);
        stage.addActor(onlineButton);
        stage.addActor(otherHallsButton);
        startTime = elapsedTime;
        newsPage.setPosition(0, 0);

        return 1;
    }

    /**
     * Run the action specified by the players decision, or the time out option
     * if they take too long to decide.
     * 
     * @param elapsedTime seconds spent unpaused.
     * @return 2 to tell eventManager to end the event.
     * @return 1 to keep waiting for a player decision.
     */
    public int eventMain(int elapsedTime) {
        if (choiceMade) {
            if (choseOnline) {
                // increase base change by 5
                satisfactionChange = 5;
            } else {
                // count learning buildings and increase the base satisfaction by 2.5 for each
                for (Building building : world.getBuildings()) {
                    if (building.getBuildingUse() == Use.LEARN) {
                        satisfactionChange += 2.5f;
                    }
                }
                // cap satisfaction at 10
                if (satisfactionChange > 10) {
                    satisfactionChange = 10;
                }
            }
            return 2;
        }
        if (elapsedTime == startTime + eventDuration) {
            return 2;
        }
        return 1;
    }

    /**
     * updates satisfaction and removes the page and buttons from stage.
     * 
     * @return 0 to tell eventManager the event has finished.
     */
    public int eventEnd() {
        world.updateEventSatisfaction(startSatisfaction + satisfactionChange, -1);

        newsPage.remove();
        onlineButton.remove();
        otherHallsButton.remove();

        return 0;
    }

    /**
     * set the textures, events and positions of the ImageButtons.
     */
    private void buildButtons() {
        // build textures
        Texture onlineButtonTextureUp = new Texture(Gdx.files.internal("online_up.png"));
        Texture onlineButtonTextureDown = new Texture(Gdx.files.internal("online_down.png"));
        Texture otherHallsTextureUp = new Texture(Gdx.files.internal("other_halls_up.png"));
        Texture otherHallsTextureDown = new Texture(Gdx.files.internal("other_halls_down.png"));

        // build drawables
        Drawable onlineButtonUp = new TextureRegionDrawable(onlineButtonTextureUp);
        Drawable onlineButtonDown = new TextureRegionDrawable(onlineButtonTextureDown);
        Drawable otherHallsButtonUp = new TextureRegionDrawable(otherHallsTextureUp);
        Drawable otherHallsButtonDown = new TextureRegionDrawable(otherHallsTextureDown);

        // build imageButtons
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

    public int getStartTime() {
        return startTime;
    }

    public int getEventDuration() {
        return eventDuration;
    }

    public float getSatisfactionChange() {
        return satisfactionChange;
    }

    public boolean isChoiceMade() {
        return choiceMade;
    }

    public boolean hasChoseOnline() {
        return choseOnline;
    }

    public void setSatisfactionChange(int satisfactionChange) {
        this.satisfactionChange = satisfactionChange;
    }
}