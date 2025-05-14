package com.yourgame.controller.GameController;

import com.yourgame.model.Animals.AnimalGood;
import com.yourgame.model.Animals.Fish;
import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Item.Crop;
import com.yourgame.model.Item.Food;
import com.yourgame.model.Item.Refrigerator;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Recipes.CookingRecipe;
import com.yourgame.model.UserInfo.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class CookingController {
    public Response cookingRefrigerator(Request request) {
        String action = request.body.get("action");
        String itemName = request.body.get("itemName");
        Food food = Food.getFoodByName(itemName);
        Player player = App.getGameState().getCurrentPlayer();
        Refrigerator refrigerator = player.getBackpack().getRefrigerator();

        if (food == null)
            return new Response(false, "Food <" + itemName + "> not found!");

        if (action.equals("put")) {
            if (!player.getBackpack().getIngredientQuantity().containsKey(food))
                return new Response(false, "You don't have this food in the backpack!");
            player.getBackpack().removeIngredients(food, 1);
            refrigerator.addItem(food, 1);
            return new Response(true, "You put <" + itemName + "> successfully in refrigerator!");
        }
        else {
            if (!(player.getBackpack().getRefrigerator().getQuantity(food) == 0))
                return new Response(false, "You don't have this food in the Refrigerator!");
            player.getBackpack().getRefrigerator().removeItem(food, 1);
            player.getBackpack().addIngredients(food, 1);
            return new Response(true, "You pickUp <" + itemName + "> successfully!");
        }
    }

    public Response handleShowCookingRecipes(Request request) {
        Player player = App.getGameState().getCurrentPlayer();
        ArrayList<CookingRecipe> recipes = player.getBackpack().getCookingRecipes();
        StringBuilder output = new StringBuilder();
        output.append("Cooking Recipes: \n");
        for (int i = 0; i < recipes.size(); i++) {
            output.append(i+1).append(". ").append(recipes.get(i).toString()).append("\n");
        }
        return new Response(true, output.toString());
    }

    public Response handleCookingFood(Request request) {
        String ItemName = request.body.get("itemName");
        CookingRecipe recipe = CookingRecipe.getRecipeByName(ItemName);
        Player player = App.getGameState().getCurrentPlayer();

        if (recipe == null)
            return new Response(false, "Recipe <" + ItemName + "> not found!");
        if (!player.getBackpack().containRecipe(recipe))
            return new Response(false, "You don't have <" + ItemName + "> CookingRecipe in your backpack!");
        if (player.getBackpack().hasCapacity())
            return new Response(false, "You don't have enough space in backpack!");

        HashMap<Ingredient, Integer> requiredIngredients = recipe.getIngredients();

        for (Ingredient requiredIngredient : requiredIngredients.keySet()) {

            Ingredient ingredientInBackpack = getIngredient(requiredIngredient, player);

            if (ingredientInBackpack == null)
                return new Response(false, "You don't have any <" + requiredIngredient + "> in your backpack!");

            if (player.getBackpack().getIngredientQuantity().get(ingredientInBackpack) < requiredIngredients.get(ingredientInBackpack)) {
                return new Response(false, "You don't have enough <" + requiredIngredient + "> in your backpack!");
            }

            player.getBackpack().removeIngredients(ingredientInBackpack, requiredIngredients.get(requiredIngredient));
        }

        Food food = Food.getFoodByName(recipe.name());
        player.getBackpack().addIngredients(food, 1);

        player.consumeEnergy(3);

        return new Response(true, "You cook <" + food + "> successfully!");
    }

    private Ingredient getIngredient(Ingredient requiredIngredient, Player player) {
        for (Ingredient myIngredient : player.getBackpack().getIngredientQuantity().keySet()) {
            if ((myIngredient instanceof Crop crop && crop.getType().equals(requiredIngredient)) ||
                    (myIngredient instanceof AnimalGood animalGood && animalGood.getType().equals(requiredIngredient)) ||
                    (myIngredient instanceof Fish fish && fish.getType().equals(requiredIngredient)) ||
                    (myIngredient.equals(requiredIngredient))) {
                return myIngredient;
            }
        }
        return null;
    }

    public Response handleEating(Request request) {
        String foodName = request.body.get("foodName");
        Food food = Food.getFoodByName(foodName);
        Player player = App.getGameState().getCurrentPlayer();

        if (food == null)
            return new Response(false, "Food <" + foodName + "> not found!");
        if (player.getBackpack().getIngredientQuantity().containsKey(food)) {
            player.getBackpack().removeIngredients(food, 1);
            player.addEnergy(food.getEnergy());
            return new Response(true,
                    "You eat <" + food + "> successfully!And increased your energy " + food.getEnergy() + "!");
        }
        else
            return new Response(false, "You don't have Food <" + food + "> space in backpack!");
    }
}
