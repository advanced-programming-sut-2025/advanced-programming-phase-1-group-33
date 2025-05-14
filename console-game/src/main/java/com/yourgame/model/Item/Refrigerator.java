package com.yourgame.model.Item;

import java.util.HashMap;

public class Refrigerator {
    //TODO add to player class
    private final HashMap<Food, Integer> foodQuantity = new HashMap<>();

    public void addItem(Food food, int quantity) {
        int oldQuantity = foodQuantity.getOrDefault(food, 0);
        foodQuantity.put(food, oldQuantity + quantity);
    }

    public void removeItem(Food food, int quantity) {
        int oldQuantity = foodQuantity.getOrDefault(food, 0);
        if (oldQuantity == 0)
            return;
        if (oldQuantity == quantity)
            foodQuantity.remove(food);

        foodQuantity.put(food, oldQuantity - quantity);
    }

    public int getQuantity(Food food) {
        return foodQuantity.getOrDefault(food, 0);
    }
}
