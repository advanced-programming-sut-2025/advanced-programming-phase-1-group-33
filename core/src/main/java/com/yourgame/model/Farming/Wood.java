package com.yourgame.model.Farming;

import com.yourgame.model.ManuFactor.Ingredient;

public class Wood implements Ingredient {
    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Wood;
    }

}
