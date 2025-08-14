package com.yourgame.model.Map.Elements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Resource.Wood;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.WeatherAndTime.Season;

import java.awt.*;
import java.util.List;

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
        return new WoodElement(isStump, tileX * Tile.TILE_SIZE, tileY * Tile.TILE_SIZE);
    }

    @Override
    public java.util.List<Item> drop() {
        return List.of(new Wood());
    }
}
