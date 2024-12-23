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
    private Drawable UFOdrawable, UFOidle, UFOactive, UFOactive2, UFOhit;
    private int pageX, pageY, clickCount, tractorCount, startTime;
    private float nextBuildingX, nextBuildingY;
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

        buildButton();
    }


    public int eventStart(int elapsedTime) {
            if (world.getBuildings().size == 0) {
                return -1;
            }
            stage.addActor(UFO);
            startTime = elapsedTime;
            pageX = 0;
            pageY = 0;
        return 1;
    };

    public int eventMain(int elapsedTime) {

        return 1;
    };

    public int eventEnd() {
        return -1;
    };

    public void draw(SpriteBatch batch) {
    };

    /**
     * set the textures, events and positions of the ImageButtons.
     */
    private void buildButton() {
        Texture UFOidleTexture = new Texture(Gdx.files.internal("UFOidle.png"));
        Texture UFOactive1Texture = new Texture(Gdx.files.internal("UFOactive1.png"));
        Texture UFOactive2Texture = new Texture(Gdx.files.internal("UFOactive2.png"));
        Texture UFOhitTexture = new Texture(Gdx.files.internal("UFOhit.png"));

        UFOidle = new TextureRegionDrawable(UFOidleTexture);
        UFOactive = new TextureRegionDrawable(UFOactive1Texture);
        UFOactive2 = new TextureRegionDrawable(UFOactive2Texture);
        UFOhit = new TextureRegionDrawable(UFOhitTexture);

        UFOdrawable = UFOidle;

        UFO = new ImageButton(UFOdrawable, UFOhit, UFOactive);

        UFO.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent onlineEvent, float x, float y) {
                if (tractorActive) {
                    clickCount += 1;
                    if (clickCount == 5) {
                        tractorActive = false;
                        Array<Building> buildings = world.getBuildings();
                        Building nextBuilding = buildings.get(rand.nextInt(buildings.size));
                        nextBuildingX = nextBuilding.getX();
                        nextBuildingY = nextBuilding.getY();

                    }
                }
            }
        });

        UFO.setPosition(50, 190);
        UFO.setSize(40, 40);
        
    }
}
