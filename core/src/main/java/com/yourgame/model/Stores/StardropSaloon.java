package com.yourgame.model.Stores;

import java.awt.*;
import java.util.ArrayList;

import com.yourgame.model.App;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Food.FoodType;
import com.yourgame.model.ManuFactor.ArtisanGood;
import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.Recipes.CookingRecipe;
import com.yourgame.model.UserInfo.Coin;
import com.yourgame.model.enums.SymbolType;

public class StardropSaloon extends Store {
    private ArrayList<ShopItem> inventory;

    public StardropSaloon(int x, int y, int width, int height) {
        super(new Rectangle(x, y, width, height), "Gus", 12, 24);
    }

    @Override
    public void loadInventory() {

        inventory = new ArrayList<>();
        inventory.add(new StardopSaloonArtisanGoodItem("Beer", ArtisanGoodType.Beer,400,Integer.MAX_VALUE));
        inventory.add(new StardopSaloonArtisanGoodItem("Coffee", ArtisanGoodType.Coffee,300,Integer.MAX_VALUE));
        inventory.add(new StardopSaloonFoodItem("Salad", FoodType.Salad,220,Integer.MAX_VALUE));
        inventory.add(new StardopSaloonFoodItem("Bread", FoodType.Bread,120,Integer.MAX_VALUE));
        inventory.add(new StardopSaloonFoodItem("Spaghetti", FoodType.Spaghetti,240,Integer.MAX_VALUE));
        inventory.add(new StardopSaloonFoodItem("Pizza", FoodType.Pizza,600,Integer.MAX_VALUE));
        inventory.add(new StardopSaloonRecipeItem("Hashbrowns", CookingRecipe.HashBrowns,50,1));
        inventory.add(new StardopSaloonRecipeItem("Omelet", CookingRecipe.Omelet,100,1));
        inventory.add(new StardopSaloonRecipeItem("Pancakes", CookingRecipe.Pancakes,100,1));
        inventory.add(new StardopSaloonRecipeItem("Bread", CookingRecipe.Bread,100,1));
        inventory.add(new StardopSaloonRecipeItem("Tortilla", CookingRecipe.Tortilla,100,1));
        inventory.add(new StardopSaloonRecipeItem("Pizza", CookingRecipe.Pizza,150,1));
        inventory.add(new StardopSaloonRecipeItem("Maki Roll", CookingRecipe.MakiRoll,300,1));
        inventory.add(new StardopSaloonRecipeItem("Triple Shot  Espresso", CookingRecipe.TripleShotEspresso,5000,1));
        inventory.add(new StardopSaloonRecipeItem("Cookie", CookingRecipe.Cookie,300,1));

    }

    @Override
    public String showAllProducts() {
        StringBuilder message = new StringBuilder("StardopSaloon products:");
        for (ShopItem item : inventory) {
            message.append("\n" + "Name: ").append(item.name).append("  Price: ").append(item.price);
        }
        return message.toString();
    }

    @Override
    public String showAvailableProducts() {
        StringBuilder message = new StringBuilder("StardopSaloon Available Products:");
        for (ShopItem item : inventory) {
            if (item.remainingQuantity > 0) {
                message.append("\nName: ").append(item.name).append("   Price: ").append(item.price).append("   Remaining: ").append(item.remainingQuantity);
            }
        }
        return message.toString();
    }

    @Override
    public Response purchaseProduct(int value, String productName) {

        if (!this.isOpen()) {
            return new Response(false, "this store is currently closed");
        }

        ShopItem item = null;

        for (ShopItem i : inventory) {
            if (i.name.equals(productName)) {
                item = i;
            }
        }

        if (item == null) {
            return new Response(false, "No such product");
        }

        int totalPrice = item.getPrice() * value;

        if (App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(new Coin(), 0) < totalPrice) {
            return new Response(false, "Not enough money");
        }

        if (item.getRemainingQuantity() < value) {
            return new Response(false, "Not enough stock");
        }

        if (item instanceof StardopSaloonRecipeItem) {

            App.getGameState().getCurrentPlayer().getBackpack().addRecipe(((StardopSaloonRecipeItem) item).getRecipe());

        } else if (item instanceof StardopSaloonArtisanGoodItem) {

            if (App.getGameState().getCurrentPlayer().getBackpack().isInventoryFull()) {
                return new Response(false, "Not enough capacity in your inventory");
            }

            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new ArtisanGood(((StardopSaloonArtisanGoodItem) item).getType()),value);

        } else if (item instanceof StardopSaloonFoodItem) {

            if (App.getGameState().getCurrentPlayer().getBackpack().isInventoryFull()) {
                return new Response(false, "Not enough capacity in your inventory");
            }

            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(((StardopSaloonFoodItem) item).getFood(),value);

        }

        App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new Coin(), (-1) * totalPrice);
        item.decreaseRemainingQuantity(value);
        return new Response(true, "You successfully purchased " + value + "number(s) of " + productName);

    }

    @Override
    public void ResetQuantityEveryNight() {
        for (ShopItem item : inventory) {
            item.resetQuantityEveryNight();
        }
    }

    @Override
    public SymbolType getSymbol() {
        return SymbolType.StardropSaloon;
    }


}
