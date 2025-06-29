package com.yourgame.model.Stores;

import java.awt.*;
import java.util.ArrayList;

import com.yourgame.model.Animals.AnimalType;
import com.yourgame.model.App;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Inventory.Tools.MilkPail;
import com.yourgame.model.Inventory.Tools.Shear;
import com.yourgame.model.UserInfo.Coin;
import com.yourgame.model.enums.SymbolType;

public class MarnieRanch extends Store {
    private ArrayList<ShopItem> inventory;

    public MarnieRanch(int x, int y, int width, int height) {
        super(new Rectangle(x, y, width, height), "Marnie", 9, 16);
    }


    @Override
    public SymbolType getSymbol() {
        return SymbolType.MarnieRanch;
    }

    @Override
    public void loadInventory() {

        inventory = new ArrayList<>();
        inventory.add(new MarnieRanchLiveStockItem("Chicken", AnimalType.Chicken, 800, 2));
        inventory.add(new MarnieRanchLiveStockItem("Cow", AnimalType.Cow, 1500, 2));
        inventory.add(new MarnieRanchLiveStockItem("Goat", AnimalType.Goat, 4000, 2));
        inventory.add(new MarnieRanchLiveStockItem("Duck", AnimalType.Duck, 1200, 2));
        inventory.add(new MarnieRanchLiveStockItem("Sheep", AnimalType.Sheep, 8000, 2));
        inventory.add(new MarnieRanchLiveStockItem("Rabbit", AnimalType.Rabbit, 8000, 2));
        inventory.add(new MarnieRanchLiveStockItem("Dinosaur", AnimalType.Dinosaur, 14000, 2));
        inventory.add(new MarnieRanchLiveStockItem("Pig", AnimalType.Pig, 16000, 2));
        inventory.add(new ShopItem("Hay", 50, Integer.MAX_VALUE));
        inventory.add(new ShopItem("Milk Pail", 1000, 1));
        inventory.add(new ShopItem("Shears", 1000, 1));

    }

    @Override
    public String showAllProducts() {
        StringBuilder message = new StringBuilder("MarnieRanch products:");
        for (ShopItem item : inventory) {
            message.append("\n" + "Name: ").append(item.name).append("  Price: ").append(item.price);
        }
        return message.toString();
    }

    @Override
    public String showAvailableProducts() {
        StringBuilder message = new StringBuilder("MarnieRanch Available Products:");
        for (ShopItem item : inventory) {
            if (item.remainingQuantity > 0) {
                message.append("\nName: ").append(item.name).append("   Price: ").append(item.price).append("   " +
                        "Remaining: ").append(item.remainingQuantity);
            }
        }
        return message.toString();
    }

    public Response PurchaseAnimal(AnimalType type) {

        if (!this.isOpen()) {
            return new Response(false, "this store is currently closed");
        }

        ShopItem item = null;

        for (ShopItem i : inventory) {
            if (i instanceof MarnieRanchLiveStockItem) {
                if (((MarnieRanchLiveStockItem) i).getType().equals(type)) {
                    item = i;
                }
            }
        }

        if (item == null) {
            return new Response(false, "No such animal");
        }

        if (App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(new Coin(), 0 ) < item.price) {
            return new Response(false, "You don't have enough money to purchase");
        }

        if (item.remainingQuantity == 0) {
            return new Response(false, "Not enough stock");
        }

        item.decreaseRemainingQuantity(1);
        App.getGameState().getCurrentPlayer().getBackpack().removeIngredients(new Coin(), item.price);

        return new Response(true, "you purchased a" + item.name + "successfully");
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

        if (item instanceof MarnieRanchLiveStockItem) {
            return new Response(false, "You must use buy animal in game menu");
        }

        int totalPrice = item.getPrice() * value;

        if (App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(new Coin(), 0) < totalPrice) {
            return new Response(false, "Not enough money");
        }

        if (item.getRemainingQuantity() < value) {
            return new Response(false, "Not enough stock");
        }

        if (item.name.equals("Hay")) {

            App.getGameState().getCurrentPlayer().getBackpack().increaseHay(value);

        } else if (item.name.equals("Milk Pail")) {

            App.getGameState().getCurrentPlayer().getBackpack().addTool(new MilkPail());

        } else if (item.name.equals("Shears")) {

            App.getGameState().getCurrentPlayer().getBackpack().addTool(new Shear());

        }

        App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new Coin(), (-1) * totalPrice);
        App.getGameState().getCurrentPlayer().getBackpack().addIngredients(((BlackSmithStocksItem) item).getType(), value);
        item.decreaseRemainingQuantity(value);

        return new Response(true, "");

    }

    @Override
    public void ResetQuantityEveryNight() {
        for (ShopItem item : inventory) {
            item.resetQuantityEveryNight();
        }
    }
}
