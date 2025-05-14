package com.yourgame.controller.GameController;

import com.yourgame.model.App;
import com.yourgame.model.IO.Request;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Item.Food;
import com.yourgame.model.Item.TreeSource;
import com.yourgame.model.ManuFactor.ArtisanMachine;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Recipes.CookingRecipe;
import com.yourgame.model.Recipes.CraftingRecipes;
import com.yourgame.model.UserInfo.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class CraftingController {
    public Response craftingShowRecipes(Request resquest) {
        Player player = App.getGameState().getCurrentPlayer();
        ArrayList<CraftingRecipes> recipes = player.getBackpack().getCraftingRecipes();
        StringBuilder output = new StringBuilder();
        output.append("Crafting Recipes: \n");
        for (int i = 0; i < recipes.size(); i++) {
            output.append(i + 1).append(". ").append(recipes.get(i).toString()).append("\n");
        }
        return new Response(true, output.toString());
    }

    public Response handleItemCrafting(Request request) {
        String itemName = request.body.get("itemName");
        CraftingRecipes recipe = CraftingRecipes.getRecipeByName(itemName);
        Player player = App.getGameState().getCurrentPlayer();

        if (recipe == null)
            return new Response(false, "Recipe <" + itemName + "> not found!");
        if (!player.getBackpack().containRecipe(recipe))
            return new Response(false, "You don't have <" + itemName + "> CraftingRecipe in your backpack!");
        if (player.getBackpack().hasCapacity())
            return new Response(false, "You don't have enough space in backpack!");

        HashMap<Ingredient, Integer> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients.keySet()) {
            if (!(player.getBackpack().getIngredientQuantity().containsKey(ingredient) &&
                    player.getBackpack().getIngredientQuantity().get(ingredient) >= ingredients.get(ingredient))) {
                return new Response(false, "You don't have enough <" + ingredient + "> in your backpack!");
            }
            player.getBackpack().removeIngredients(ingredient, ingredients.get(ingredient));
        }

        ArtisanMachine artisanMachine = ArtisanMachine.getArtisanMachineByRecipe(recipe);
        if (artisanMachine != null)
            player.getBackpack().addArtisanMachine(artisanMachine);
        if (recipe.equals(CraftingRecipes.MysticTreeSeed))
            player.getBackpack().addIngredients(TreeSource.MysticTreeSeeds, 1);
        //TODO else

        player.consumeEnergy(2);

        return new Response(true, "You craft <" + itemName + "> successfully!");
    }

    public Response handleAddItemCheat(Request request) {
        String count = request.body.get("count");
        int quantity = Integer.parseInt(count);
        String ItemName = request.body.get("itemName");
        Player player = App.getGameState().getCurrentPlayer();

        if (quantity <= 0)
            return new Response(false, "The quantity must be greater than zero!");
        if (!player.getBackpack().hasCapacity(quantity))
            return new Response(false, "You don't have enough space in backpack!");
        CraftingRecipes craftingRecipe = CraftingRecipes.getRecipeByName(ItemName);
        ArtisanMachine machine;
        if (craftingRecipe != null) {
            if ((machine = ArtisanMachine.getArtisanMachineByRecipe(craftingRecipe)) != null) {
                player.getBackpack().addArtisanMachine(machine);
            } else if (craftingRecipe.equals(CraftingRecipes.MysticTreeSeed)) {
                player.getBackpack().addIngredients(TreeSource.MysticTreeSeeds, quantity);
            }
            //TODO else
            return new Response(true, "You add <" + ItemName + "> successfully!");
        }
        CookingRecipe cookingRecipe = CookingRecipe.getRecipeByName(ItemName);
        if (cookingRecipe != null) {
            Food food = Food.getFoodByName(ItemName);
            player.getBackpack().addIngredients(food, quantity);
            return new Response(true, "You add <" + ItemName + "> successfully!");
        }
        //TODO else item
        return new Response(false, "There is no such Item!");
    }
}
