package com.yourgame.model.Farming;

import com.yourgame.model.Item.ArtisanIngredient;

public enum Mushrooms implements ArtisanIngredient {
    CHANTERELLE(160, 75),
    COMMON_MUSHROOM(40, 38),
    MAGMA_CAP(150, 50),
    MOREL(150, 50),
    PURPLE_MUSHROOM(250, 125),
    RED_MUSHROOM(75, -50);

    private final int basePrice;
    private final int baseEnergy;

    Mushrooms(int basePrice, int baseEnergy) {
        this.basePrice = basePrice;
        this.baseEnergy = baseEnergy;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public int getBaseEnergy() {
        return baseEnergy;
    }
}
