package com.yourgame.model.Stores;

import com.yourgame.model.Animals.AnimalType;

public class MarnieRanchLiveStockItem extends ShopItem {

    private final AnimalType animalType;


    public MarnieRanchLiveStockItem(String name, AnimalType animalType, int price, int dailyLimit) {

        super(name, price, dailyLimit);
        this.animalType = animalType;

    }

    public AnimalType getType() {
        return animalType;
    }

}
