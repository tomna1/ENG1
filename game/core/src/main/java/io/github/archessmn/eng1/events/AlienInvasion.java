package io.github.archessmn.eng1.events;

import java.util.Random;

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
import com.badlogic.gdx.utils.Array;

import io.github.archessmn.eng1.World;
import io.github.archessmn.eng1.buildings.Building;

public class AlienInvasion implements Event {
    private World world;
    private Stage stage;
    private Texture pageTexture;
    private Sprite pageSprite;
    private int pageX, pageY, clickCount, startTime, tractorStart;
    private float UFOx, UFOy;
    private float[][] nextBuildingPos = new float[2][2];
    private boolean tractorActive;
    private ImageButton UFO;
    private Random rand;

    public AlienInvasion(World world, Stage stage) {
        System.out.println("alien");
        this.world = world;
        this.stage = stage;
        this.pageTexture = new Texture(Gdx.files.internal("maintenance.png"));
        this.pageSprite = new Sprite(this.pageTexture);
        this.pageSprite.setSize(400, 225);
        this.pageX = -1000;
        this.pageY = -1000;
        this.tractorActive = false;
        this.rand = new Random();

        this.UFOx = -45f;
        this.UFOy = -45f;

        buildButton();
    }

    public int eventStart(int elapsedTime) {
        Array<Building> buildings = world.getBuildings();
        if (buildings.size == 0) {
            return -1;
        }

        nextBuildingPos = getNextBuildingPos();

        stage.addActor(UFO);

        startTime = elapsedTime;
        pageX = 0;
        pageY = 0;
        return 1;
    };

    public int eventMain(int elapsedTime) {
        if (tractorActive) {
            if (elapsedTime == (tractorStart + 5)) {
                System.out.println("-5 satisfaction");
                // -5 student satisfaction
            }
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

        if (elapsedTime == (startTime + 15)) {
            tractorActive = false;
            
            float nextX = 500;
            float nextY = 545;

            float xSpeed = (nextX - UFOx) / 180;
            float ySpeed = (nextY - UFOy) / 180;

            nextBuildingPos = new float[][] { { nextX, nextY }, { xSpeed, ySpeed } };
            return 2;
        }
        return 1;
    };

    public int eventEnd() {
        UFOx += nextBuildingPos[1][0];
        UFOy += nextBuildingPos[1][1];
        UFO.setPosition(UFOx, UFOy);

        if (Math.abs(UFOx - nextBuildingPos[0][0]) < 0.02
                && Math.abs(UFOy - nextBuildingPos[0][1]) < 0.02) {
            return 0;

        }
        return 2;
    };

    public void draw(SpriteBatch batch) {
    };

    public float[][] getNextBuildingPos() {
        Array<Building> buildings = world.getBuildings();
        Building nextBuilding = buildings.get(rand.nextInt(buildings.size));
        float nextBuildingX = nextBuilding.getX();
        float nextBuildingY = nextBuilding.getY();

        float xSpeed = (nextBuildingX - UFOx) / 180;
        float ySpeed = (nextBuildingY - UFOy) / 180;

        float[][] nextBuildingPos = { { nextBuildingX, nextBuildingY }, { xSpeed, ySpeed } };

        return nextBuildingPos;

    }

    /**
     * set the textures, events and positions of the ImageButtons.
     */
    private void buildButton() {
        Texture UFOidleTexture = new Texture(Gdx.files.internal("UFOidle.png"));
        Texture UFOactiveTexture = new Texture(Gdx.files.internal("UFOactive.png"));
        Texture UFOhitTexture = new Texture(Gdx.files.internal("UFOhit.png"));

        Drawable UFOidle = new TextureRegionDrawable(UFOidleTexture);
        Drawable UFOactive = new TextureRegionDrawable(UFOactiveTexture);
        Drawable UFOhit = new TextureRegionDrawable(UFOhitTexture);

        UFO = new ImageButton(UFOidle, UFOhit, UFOactive);

        UFO.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent onlineEvent, float x, float y) {
                UFO.setChecked(tractorActive);
                if (tractorActive) {
                    clickCount += 1;
                    if (clickCount == 5) {
                        UFO.setChecked(false);
                        tractorActive = false;
                        clickCount = 0;

                        nextBuildingPos = getNextBuildingPos();

                    }
                }
            }
        });

        UFO.setPosition(UFOx, UFOy);
        UFO.setSize(40, 40);

    }
}
