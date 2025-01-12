package io.github.archessmn.eng1.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import io.github.archessmn.eng1.core.World;
import io.github.archessmn.eng1.util.GridCoordTuple;
import io.github.archessmn.eng1.util.GridUtils;

public class Lake extends Sprite{

    private Integer gridX;
    private Integer gridY;

    public Lake(Integer gridX, Integer gridY, World world) {
        this.gridX = gridX;
        this.gridY = gridY;
        Vector2 realCoords = GridUtils.getRealCoords(gridX, gridY);
        setSize(60, 60);
        setPosition(realCoords.x - getWidth() / 2, realCoords.y - getHeight() / 2);
        setRegion(world.assetManager.get("lake.png", Texture.class));
    }

    public Integer getGridX(){
        return gridX;
    }

    public Integer getGridY(){
        return gridY;
    }
    
}
