package com.yourgame.Graphics.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.WeatherAndTime.Season;

import java.awt.*;

public class Rock extends MapElement {
    private final boolean isSmall;

    public Rock(boolean small, int x, int y) {
        super(ElementType.ROCK, new Rectangle(x, y, small ? 16 : 32, small ? 16 : 32), 5);
        isSmall = small;
    }

    @Override
    public TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason) {
        String path = "Game/Mineral/rock_" + (isSmall ? "small" : "big") + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    @Override
    public MapElement clone(int tileX, int tileY) {
        return new Rock(isSmall, tileX * TileData.TILE_SIZE, tileY * TileData.TILE_SIZE);
    }
}
