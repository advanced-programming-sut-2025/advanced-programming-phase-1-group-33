package com.yourgame.model.Farming;

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

    public static Fertilizer getByName(String name) {
        for (Fertilizer type : Fertilizer.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

}
