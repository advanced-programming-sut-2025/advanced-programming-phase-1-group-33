package com.yourgame.Graphics.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.WeatherAndTime.Season;

import java.awt.*;

public class WoodElement extends MapElement {
    private final boolean isStump;

    /**
     * @param isStump true: stump | false: trunk
     * */
    public WoodElement(boolean isStump, int worldX, int worldY) {
        super(ElementType.WOOD, new Rectangle(worldX, worldY, 32, 32), 1);
        this.isStump = isStump;
    }

    @Override
    public TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason) {
        String path = "Game/Tree/" + (isStump ? "Stump" : "Trunk") + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    @Override
    public MapElement clone(int tileX, int tileY) {
        return new WoodElement(isStump, tileX * TileData.TILE_SIZE, tileY * TileData.TILE_SIZE);
    }
}
