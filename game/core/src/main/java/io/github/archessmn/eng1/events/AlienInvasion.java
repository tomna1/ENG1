package io.github.archessmn.eng1.events;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private Image pageSprite;
    private int clickCount, startTime, tractorStart;
    private float UFOx, UFOy;
    private float[][] nextBuildingPos = new float[2][2];
    private boolean starting, tractorActive, pageVisible, hit;
    private Drawable UFOidle, UFOactive, UFOhit;
    private Image UFO;
    private Random rand;
    private Building currentBuilding;

    public AlienInvasion(World world, Stage stage) {
        System.out.println("alien");
        this.world = world;
        this.stage = stage;
        this.pageTexture = new Texture(Gdx.files.internal("newsUFO.png"));
        this.pageSprite = new Image(this.pageTexture);
        this.pageSprite.setSize(400, 272);
        this.tractorActive = false;
        this.pageVisible = true;
        this.rand = new Random();

        this.UFOx = -45f;
        this.UFOy = -45f;
        
        this.starting = false;


        buildUFO();



    }

    public int eventStart(int elapsedTime) {
        if(!starting){
            Array<Building> buildings = world.getBuildings();
            if (buildings.size < 2) {
                return -1;
            }
            starting = true;
            startTime = elapsedTime;
            stage.addActor(pageSprite);
            pageSprite.setPosition(0, 0);
        }

        if (elapsedTime == startTime + 6) {
            startTime = elapsedTime;
            pageSprite.setPosition(-1000, -1000);

            stage.addActor(UFO);
            nextBuildingPos = getNextBuildingPos();

            return 1;
        }
        return 0;
    };

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
                hit = false;

                nextBuildingPos = getNextBuildingPos();
            }

            if (hit) {
                UFO.setDrawable(UFOhit);
                hit = false;
            } else {
                if (elapsedTime % 2 == 0) {
                    UFO.setDrawable(UFOactive);
                } else {
                    UFO.setDrawable(UFOidle);
                }
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

    public int eventEnd() {
        UFOx += nextBuildingPos[1][0];
        UFOy += nextBuildingPos[1][1];
        UFO.setPosition(UFOx, UFOy);

        if (Math.abs(UFOx - nextBuildingPos[0][0]) < 0.02
                && Math.abs(UFOy - nextBuildingPos[0][1]) < 0.02) {
            UFO.remove();
            pageSprite.remove();
            return 0;
        } else {
            return 2;
        }
    };

    public void draw(SpriteBatch batch) {
        if (pageVisible) {
        }
    };

    public float[][] getNextBuildingPos() {
        Array<Building> buildings = world.getBuildings();
        int index;
        Building nextBuilding = currentBuilding;
        while (nextBuilding == currentBuilding) {
            index = rand.nextInt(buildings.size);
            nextBuilding = buildings.get(index);
        }
        currentBuilding = nextBuilding;
        float nextBuildingX = nextBuilding.getX();
        float nextBuildingY = nextBuilding.getY();

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
