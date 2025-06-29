package com.yourgame.model.Item;

import java.util.HashMap;

public class Refrigerator {
    private final HashMap<Food, Integer> foodQuantity = new HashMap<>();
    private final int capacity = 15;

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

    public boolean containFood(Food food) {
        return foodQuantity.getOrDefault(food, 0) > 0;
    }

    public boolean hasCapacity() {
        return capacity > foodQuantity.size();
    }
}
