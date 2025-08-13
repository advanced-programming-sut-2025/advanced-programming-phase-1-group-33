package com.yourgame.model.Map.Store;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class ShopItem extends Item {
    private final int dailyLimit;
    private int remainingQuantity;
    private final String filePath;

    public ShopItem(String name, ItemType itemType, int value, boolean isStackable, Integer dailyLimit, String filePath) {
        super(name, itemType, value, isStackable);

        this.dailyLimit = dailyLimit;
        this.remainingQuantity = dailyLimit;
        this.filePath = filePath;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity() {
        remainingQuantity--;
    }

    public void addRemainingQuantity() {
        remainingQuantity++;
    }

    @Override
    public TextureRegion getTextureRegion(GameAssetManager assetManager) {
        return new TextureRegion(assetManager.getTexture(filePath));
    }
}
