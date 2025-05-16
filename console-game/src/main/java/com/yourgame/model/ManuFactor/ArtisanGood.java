package com.yourgame.model.ManuFactor;

import com.yourgame.model.Stores.Sellable;

import java.util.Objects;

public class ArtisanGood implements Ingredient, Sellable {
    private final ArtisanGoodType type;
    private final int energy;
    private final int sellPrice;

    public ArtisanGood(ArtisanGoodType type) {
        this.type = type;
        this.energy = type.getEnergy();
        this.sellPrice = type.getSellPrice();
    }

    public ArtisanGood(ArtisanGoodType type, int energy, int sellPrice) {
        this.type = type;
        this.energy = energy;
        this.sellPrice = sellPrice;
    }

    public ArtisanGoodType getType() {
        return type;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArtisanGood that))
            return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
