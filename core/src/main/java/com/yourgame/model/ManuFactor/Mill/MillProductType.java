package com.yourgame.model.ManuFactor.Mill;

import com.yourgame.model.Item.ArtisanIngredient;

public enum MillProductType implements ArtisanIngredient {
    WheatFlour("WheatFlour", 50, 25),
    Sugar("Sugar", 50, 13),
    Rice("Rice", 100, 25);

    private final String name;
    private final int sellPrice;
    private final int energy;

    MillProductType(String name, int sellPrice, int energy) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.energy = energy;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getBaseEnergy() {
        return energy;
    }

    @Override
    public int getBasePrice() {
        return sellPrice;
    }
}
