package com.yourgame.model.Food.Cooking;

import com.yourgame.model.Food.Food;

import java.util.ArrayList;

public class CookingRecipe {
    private final ArrayList<Ingredient> ingredients;
    private final Food result;
    private final CookingRecipeSource source;

    public CookingRecipe(ArrayList<Ingredient> ingredients, Food result, CookingRecipeSource source) {
        this.ingredients = ingredients;
        this.result = result;
        this.source = source;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public Food getResult() {
        return result;
    }

    public CookingRecipeSource getSource() {
        return source;
    }
}
