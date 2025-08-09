package com.yourgame.model.Map.Elements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.WeatherAndTime.Season;

import java.awt.*;
import java.util.List;

public class Rock extends MapElement {
    private final boolean isSmall;

    public Rock(boolean small, int worldX, int worldY) {
        super(ElementType.ROCK, new Rectangle(worldX, worldY, small ? 16 : 32, small ? 16 : 32), small ? 2 : 5);
        isSmall = small;
    }

    @Override
    public TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason) {
        String path = "Game/Mineral/rock_" + (isSmall ? "small" : "big") + ".png";
        return new TextureRegion(assetManager.getTexture(path));
    }

    @Override
    public MapElement clone(int tileX, int tileY) {
        return new Rock(isSmall, tileX * Tile.TILE_SIZE, tileY * Tile.TILE_SIZE);
    }

    @Override
    public List<Item> drop() {
        return List.of();
    }
}
