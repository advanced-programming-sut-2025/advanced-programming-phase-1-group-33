package com.yourgame.model.UserInfo;

import com.yourgame.model.ManuFactor.Ingredient;

public class Coin implements Ingredient {
    @Override
    public int hashCode() {
        return 3;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Coin;
    }
}
