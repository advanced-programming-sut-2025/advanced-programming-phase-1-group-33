package com.yourgame.model.Item;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Item {
    public enum ItemType {
        FOOD, TOOL, CROP, MATERIAL, RESOURCE, TREASURE, QUEST_ITEM
    }

    private final String name;
    private final ItemType itemType;
    private final int value; // Could represent in-game currency or utility
    private final boolean isStackable;

    public Item(String name,ItemType itemType, int value, boolean isStackable) {
        this.name = name;
        this.itemType = itemType;
        this.value = value;
        this.isStackable = isStackable;
    }

    public abstract TextureRegion getTextureRegion();

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
