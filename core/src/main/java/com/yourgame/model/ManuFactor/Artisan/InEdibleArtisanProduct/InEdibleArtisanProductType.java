package com.yourgame.model.ManuFactor.Artisan.InEdibleArtisanProduct;

import com.yourgame.model.Item.ArtisanIngredient;
import com.yourgame.model.ManuFactor.Artisan.ArtisanProductType;

public enum InEdibleArtisanProductType implements ArtisanIngredient, ArtisanProductType {
    Coal("Coal", 0, 50, 1),
    Cloth("Cloth", 0, 470, 4);

    private final String name;
    private final int energy;
    private final int sellPrice;
    private final int processingHour;

    InEdibleArtisanProductType(String name, int energy, int sellPrice, int processingHour) {
        this.name = name;
        this.energy = energy;
        this.sellPrice = sellPrice;
        this.processingHour = processingHour;
    }

    @Override
    public int getSellPrice() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public int getProcessingHour() {
        return processingHour;
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
