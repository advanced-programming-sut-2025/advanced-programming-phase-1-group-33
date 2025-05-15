package com.yourgame.model.Stores;


import com.yourgame.model.ManuFactor.Ingredient;

public class FishShopStock extends ShopItem implements Ingredient {

    public FishShopStock(String name, int price, int dailyLimit) {
        super(name, price, dailyLimit);
    }

}
