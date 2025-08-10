package com.yourgame.model.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.WeatherAndTime.Season;

import java.awt.*;
import java.util.List;

public abstract class MapElement {
    public enum ElementType {
        TREE, ROCK, WOOD, FORAGING, CROP, CRAFTING, ANIMAL_PRODUCT
    }

    protected final ElementType type;
    protected final Rectangle pixelBounds;
    protected final Rectangle tileBounds;
    protected float health;

    /**
     * @param pixelBounds unit: pixel
     * */
    public MapElement(ElementType type, Rectangle pixelBounds, float health) {
        this.type = type;
        this.pixelBounds = pixelBounds;
        this.tileBounds = new Rectangle(
            Math.floorDiv(pixelBounds.x, Tile.TILE_SIZE),
            Math.floorDiv(pixelBounds.y, Tile.TILE_SIZE),
            pixelBounds.width / Tile.TILE_SIZE,
            pixelBounds.height / Tile.TILE_SIZE
        );
        this.health = health;
    }

    public abstract TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason);
    public abstract MapElement clone(int tileX, int tileY);
    /**
     * @return The items that the element should drop on destruction.
     * */
    public abstract List<Item> drop();

    public ElementType getType() {
        return type;
    }

    public Rectangle getPixelBounds() {
        return pixelBounds;
    }

    public Rectangle getTileBounds() {
        return tileBounds;
    }

    public float getHealth() {
        return health;
    }

    /**
     * @return true if the element is destroyed
     * */
    public boolean takeDamage(float damage) {
        health -= damage;
        return health <= 0;
    }
}
