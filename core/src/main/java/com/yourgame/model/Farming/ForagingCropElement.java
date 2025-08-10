package com.yourgame.model.Farming;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.Map.Tile;
import com.yourgame.model.WeatherAndTime.Season;

import java.awt.*;
import java.util.List;

public class ForagingCropElement extends MapElement {
    private final ForagingCrop foragingType;

    public ForagingCropElement(ForagingCrop foragingType, int worldX, int worldY) {
        super(ElementType.FORAGING, new Rectangle(worldX, worldY, 16, 16), 1);
        this.foragingType = foragingType;
    }

    @Override
    public TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason) {
        return new TextureRegion(assetManager.getTexture("Game/Foraging/" + foragingType.getName() + ".png"));
    }

    @Override
    public MapElement clone(int tileX, int tileY) {
        return new ForagingCropElement(foragingType, tileX * Tile.TILE_SIZE, tileY * Tile.TILE_SIZE);
    }

    @Override
    public java.util.List<Item> drop() {
        return List.of();
    }
}
