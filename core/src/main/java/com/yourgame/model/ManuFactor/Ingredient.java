package com.yourgame.model.ManuFactor;

import com.yourgame.model.Item.Item;

public interface Ingredient {
    default Item getItem() {
        return null;
    }
}
