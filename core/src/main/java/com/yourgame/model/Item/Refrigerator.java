package com.yourgame.model.Item;

import com.yourgame.model.Food.FoodType;

import java.util.HashMap;

public class Refrigerator {
    private final HashMap<FoodType, Integer> foodQuantity = new HashMap<>();
    private final int capacity = 15;

    public void addItem(FoodType foodType, int quantity) {
        int oldQuantity = foodQuantity.getOrDefault(foodType, 0);
        foodQuantity.put(foodType, oldQuantity + quantity);
    }

    public void removeItem(FoodType foodType, int quantity) {
        int oldQuantity = foodQuantity.getOrDefault(foodType, 0);
        if (oldQuantity == 0)
            return;
        if (oldQuantity == quantity)
            foodQuantity.remove(foodType);

        foodQuantity.put(foodType, oldQuantity - quantity);
    }

    public int getQuantity(FoodType foodType) {
        return foodQuantity.getOrDefault(foodType, 0);
    }

    public boolean containFood(FoodType foodType) {
        return foodQuantity.getOrDefault(foodType, 0) > 0;
    }

    public boolean hasCapacity() {
        return capacity > foodQuantity.size();
    }
}
