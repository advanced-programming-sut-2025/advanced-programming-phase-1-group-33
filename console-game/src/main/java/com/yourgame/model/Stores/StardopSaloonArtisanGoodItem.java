package com.yourgame.model.Stores;


import com.yourgame.model.ManuFactor.ArtisanGoodType;

public class StardopSaloonArtisanGoodItem extends ShopItem{
    private final ArtisanGoodType type;

    public StardopSaloonArtisanGoodItem(String name, ArtisanGoodType type ,int price, int dailyLimit) {
        super(name, price, dailyLimit);
        this.type = type;
    }

    public ArtisanGoodType getType() {
        return type;
    }
}
