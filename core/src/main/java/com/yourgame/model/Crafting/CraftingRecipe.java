package com.yourgame.model.Crafting;

import com.yourgame.model.Food.Cooking.Ingredient;

import java.util.ArrayList;

public class CraftingRecipe {
    private final ArrayList<Ingredient> ingredients;
    private final CraftedItem result;
    private final CraftingRecipeSource source;

    public CraftingRecipe(ArrayList<Ingredient> ingredients, CraftedItem result, CraftingRecipeSource source) {
        this.ingredients = ingredients;
        this.result = result;
        this.source = source;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public CraftedItem getResult() {
        return result;
    }

    public CraftingRecipeSource getSource() {
        return source;
    }
}
