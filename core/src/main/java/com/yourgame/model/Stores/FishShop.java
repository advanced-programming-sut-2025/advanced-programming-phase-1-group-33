package com.yourgame.model.Stores;

import java.awt.*;
import java.util.ArrayList;

import com.yourgame.model.App;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Item.Tools.FishingPole;
import com.yourgame.model.Item.Tools.PoleStage;
import com.yourgame.model.Recipes.CraftingRecipes;
import com.yourgame.model.UserInfo.Coin;
import com.yourgame.model.enums.SymbolType;
import com.yourgame.model.Food.FoodType;

public class FishShop extends Store {

    private ArrayList<ShopItem> inventory;

    public FishShop(int x, int y, int width, int height) {
        super(new Rectangle(x, y, width, height), "willy", 9, 17);
    }

    @Override
    public void loadInventory() {

        inventory = new ArrayList<>();

        inventory.add(new FishShopCraftingRecipe("Fish Smoker", CraftingRecipes.FishSmoker , 10000 , 1));
        inventory.add(new FishShopPoleItem("Training Rod", PoleStage.Training , 0 , 25 , 1));
        inventory.add(new FishShopPoleItem("Bamboo Pole", PoleStage.Bamboo , 0 , 500 , 1));
        inventory.add(new FishShopPoleItem("Fiberglass Rod", PoleStage.Fiberglass , 2 , 1800 , 1));
        inventory.add(new FishShopPoleItem("Iridium Rod", PoleStage.Iridium , 4 , 7500 , 1));
        inventory.add(new ShopItem("Trout Soup",250,1));

    }

    @Override
    public String showAllProducts() {
        StringBuilder message = new StringBuilder("FishShop products:");
        for (ShopItem item : inventory) {
            message.append("\n" + "Name: ").append(item.name).append("  Price: ").append(item.price);
        }
        return message.toString();
    }

    @Override
    public String showAvailableProducts() {
        StringBuilder message = new StringBuilder("FishShop Available Products:");
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

        if (item.remainingQuantity < value) {
            return new Response(false, "Not enough stock");
        }


        if (item instanceof FishShopPoleItem) {

            if (((FishShopPoleItem) item).getFishingSkillRequired() < App.getGameState().getCurrentPlayer().getFishingSkill().level()) {
                return new Response(false, "your fishing level must be at least " + ((FishShopPoleItem) item).getFishingSkillRequired());
            }

            App.getGameState().getCurrentPlayer().getBackpack().addTool(new FishingPole((((FishShopPoleItem) item).getType())));
            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new Coin(), (-1) * totalPrice);

        } else if (item instanceof FishShopCraftingRecipe) {

            App.getGameState().getCurrentPlayer().getBackpack().addRecipe(((FishShopCraftingRecipe) item).getRecipe());
            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new Coin(), (-1) * totalPrice);

        } else {

            if(App.getGameState().getCurrentPlayer().getBackpack().isInventoryFull()){
                return new Response(false, "Not enough capacity in your inventory");
            }
            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(FoodType.TroutSoup,value);
            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new Coin(), (-1) * totalPrice);
            item.decreaseRemainingQuantity(value);

        }

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
        return SymbolType.FishShop;
    }


}
