package com.yourgame.model.Stores;


import com.yourgame.model.ManuFactor.Ingredient;

public class JojaMartPermanentStock extends ShopItem implements Ingredient {

    public JojaMartPermanentStock(String name, int price, int dailyLimit) {
        super(name, price, dailyLimit);
    }

}
