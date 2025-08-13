package com.yourgame.model.Food.Cooking;

import com.yourgame.model.Item.Item;

public class Ingredient {
    private final Item item;
    private final int quantity;

    public Ingredient(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Item getItem() {
        return item;
    }
}
