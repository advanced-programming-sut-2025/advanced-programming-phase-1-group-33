package com.yourgame.model.Stores;


import com.yourgame.model.Food.FoodType;

public class StardopSaloonFoodItem extends ShopItem {
    private final FoodType foodType;

    public StardopSaloonFoodItem(String name, FoodType foodType, int price, int dailyLimit) {
        super(name, price, dailyLimit);
        this.foodType = foodType;
    }

    public FoodType getFood() {
        return foodType;
    }
}
