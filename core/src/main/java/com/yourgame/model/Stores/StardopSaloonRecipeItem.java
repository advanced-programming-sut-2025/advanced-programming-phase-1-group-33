package com.yourgame.model.Stores;


import com.yourgame.model.Recipes.CookingRecipe;

public class StardopSaloonRecipeItem extends ShopItem{
    private final CookingRecipe recipe;

    public StardopSaloonRecipeItem(String name, CookingRecipe recipe ,int price, int dailyLimit) {
        super(name, price, dailyLimit);
        this.recipe = recipe;
    }

    public CookingRecipe getRecipe() {
        return recipe;
    }
}
