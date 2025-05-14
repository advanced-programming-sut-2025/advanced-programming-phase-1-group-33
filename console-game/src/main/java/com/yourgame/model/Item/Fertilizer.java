package com.yourgame.model.Item;

import com.yourgame.model.ManuFactor.Ingredient;

public enum Fertilizer implements Ingredient {
    GrowthFertilizer,
    WaterFertilizer;

    public static Fertilizer getFertilizerByName(String fertilizerName) {
        if (fertilizerName.equalsIgnoreCase("GrowthFertilizer"))
            return GrowthFertilizer;
        else
            return WaterFertilizer;
    }
}
