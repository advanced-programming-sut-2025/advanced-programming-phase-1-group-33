package com.yourgame.model.Farming;

import com.yourgame.model.Item.ArtisanIngredient;

public enum Fruits implements ArtisanIngredient {
    ANCIENT_FRUIT(550, 0),
    APPLE(100, 38),
    APRICOT(50, 38),
    BLACKBERRY(20, 25),
    BLUEBERRY(50, 25),
    CACTUS_FRUIT(75, 15),
    CHERRY(80, 38),
    COCONUT(100, 25),
    CRANBERRY(75, 38),
    CRYSTAL_FRUIT(150, 63),
    GRAPES(80, 38),
    MELON(250, 63),
    ORANGE(100, 38),
    PEACH(140, 38),
    POMEGRANATE(140, 38),
    SALMONBERRY(5, 13),
    SPICE_BERRY(80, 25),
    STARFRUIT(750, 125),
    STRAWBERRY(120, 38);

    private final int basePrice;
    private final int baseEnergy;

    Fruits(int basePrice, int baseEnergy) {
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
