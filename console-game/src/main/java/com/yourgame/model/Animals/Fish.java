package com.yourgame.model.Animals;


import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Stores.Sellable;

public class Fish implements Ingredient, Sellable {
    private final FishType type;
    private final int sellPrice;
    private final Quality quality;

    public Fish(FishType type, Quality quality) {
        this.type = type;
        this.quality = quality;
        this.sellPrice = (int) (type.getPrice() * quality.getRatio());
    }

    public FishType getType() {
        return type;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public Quality getQuality() {
        return quality;
    }

    @Override
    public String toString() {
        return "Fish -> " +
                "type: " + type +
                ", quality: " + quality;
    }
}
