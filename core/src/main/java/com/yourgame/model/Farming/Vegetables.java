package com.yourgame.model.Farming;

import com.yourgame.model.Item.ArtisanIngredient;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProduct;

public enum Vegetables implements ArtisanIngredient {
    BEET(100, 38),
    CAULIFLOWER(175, 38),
    CORN(50, 15),
    EGGPLANT(60, 25),
    FIDDLEHEAD_FERN(90, 25),
    HOPS(25, 0), // Not edible
    KALE(110, 50),
    MIXED_SEEDS(0, 0),
    PARSNIP(35, 25),
    POTATO(80, 25),
    RADISH(90, 25),
    RHUBARB(220, 50),
    UNMILLED_RICE(30, 0),
    YAM(160, 25);

    private final int basePrice;
    private final int baseEnergy;

    Vegetables(int basePrice, int baseEnergy) {
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
