package com.yourgame.model.Animals;

import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Stores.Sellable;

public class AnimalGood implements Ingredient, Sellable {
    private final AnimalGoodType type;
    private final Quality quality;
    private final int sellPrice;

    public AnimalGood(AnimalGoodType type, Quality quality) {
        this.type = type;
        this.quality = quality;
        this.sellPrice = (int) (type.getPrice() * quality.getRatio());
    }

    public AnimalGoodType getType() {
        return type;
    }

    public Quality getQuality() {
        return quality;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
