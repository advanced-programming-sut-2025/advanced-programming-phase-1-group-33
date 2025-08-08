package com.yourgame.model.Item;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;

public abstract class Item {
    public enum ItemType {
        FOOD, TOOL, CROP, MATERIAL, RESOURCE, TREASURE, QUEST_ITEM
    }

    protected final String name;
    protected final ItemType itemType;
    protected final int value; // Could represent in-game currency or utility
    protected final boolean isStackable;

    public Item(String name,ItemType itemType, int value, boolean isStackable) {
        this.name = name;
        this.itemType = itemType;
        this.value = value;
        this.isStackable = isStackable;
    }

    public abstract TextureRegion getTextureRegion(GameAssetManager assetManager);

    public String getName() {
        return name;
    }

    public ItemType getItemType() {
            return itemType;
        }

    public int getValue() {
        return value;
    }

    public String getSellPrice() {
        return "";
    }

    public boolean isStackable() {
        return isStackable;
    }
}
