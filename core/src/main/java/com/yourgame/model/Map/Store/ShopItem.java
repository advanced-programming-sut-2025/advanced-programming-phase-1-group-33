package com.yourgame.model.Map.Store;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.yourgame.Graphics.GameAssetManager;
import com.yourgame.model.Item.Item;

public class ShopItem {
    private final Item item;
    private final int dailyLimit;
    private int remainingQuantity;

    public ShopItem(Item item, Integer dailyLimit) {
        this.item = item;
        this.dailyLimit = dailyLimit;
        this.remainingQuantity = dailyLimit;
    }

    public Item getItem() {
        return item;
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

    public int getPriceOutOfSeason() {
        return (int) (item.getValue() * 1.5);
    }
}
