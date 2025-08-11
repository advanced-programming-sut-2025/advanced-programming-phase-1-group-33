package com.yourgame.model.Map.Elements;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.WeatherAndTime.Season;

import java.awt.*;
import java.util.List;

public class DroppedItem extends MapElement {
    private final Item item;
    private final Vector2 position;
    private final Vector2 velocity;

    public DroppedItem(Item item, float x, float y) {
        super(ElementType.DROPPED_ITEM, new Rectangle((int) x, (int) y, 16, 16), 1);
        this.item = item;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2();
    }

    public Item getItem() {
        return item;
    }

    public Vector2 getPosition() {
        return position;
    }

    /**
     * Updates the item's position based on its velocity. Called every frame.
     * @param delta The time elapsed since the last frame.
     */
    public void update(float delta) {
        position.add(velocity.x * delta, velocity.y * delta);
        // Sync the pixelBounds with the new position for rendering and collision.
        pixelBounds.x = (int) position.x;
        pixelBounds.y = (int) position.y;
    }

    /**
     * Sets the item's velocity to move towards a target position (the player).
     * @param target The position to move towards.
     * @param speed The speed of movement in pixels per second.
     */
    public void moveTo(Vector2 target, float speed) {
        velocity.set(target).sub(position).nor().scl(speed);
    }

    public void stopMovement() {
        velocity.setZero();
    }

    @Override
    public TextureRegion getTexture(GameAssetManager assetManager, Season currentSeason) {
        return item.getTextureRegion(assetManager);
    }

    @Override
    public MapElement clone(int tileX, int tileY) {
        return new DroppedItem(this.item, tileX * 16, tileY * 16);
    }

    @Override
    public List<Item> drop() {
        return List.of();
    }
}
