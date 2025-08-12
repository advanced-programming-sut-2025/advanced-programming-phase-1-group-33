package com.yourgame.model.Stores;

import java.awt.*;
import java.util.ArrayList;

import com.yourgame.model.App;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Food.FoodType;
import com.yourgame.model.Farming.Seeds;
import com.yourgame.model.UserInfo.Coin;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.enums.SymbolType;

public class JojaMart extends Store {
    private ArrayList<ShopItem> inventory;

    public JojaMart(int x, int y, int width, int height) {
        super(new Rectangle(x, y, width, height), "Morris", 9, 23);
    }

    @Override
    public void loadInventory() {

        inventory = new ArrayList<>();

        // Spring
        inventory.add(new JojaMartSeasonsStock("Parsnip Seeds", Seeds.Parsnip_Seeds, Season.Spring, 25, 5));
        inventory.add(new JojaMartSeasonsStock("Bean Starter", Seeds.Bean_Starter, Season.Spring, 75, 5));
        inventory.add(new JojaMartSeasonsStock("Cauliflower Seeds", Seeds.Cauliflower_Seeds, Season.Spring, 100, 5));
        inventory.add(new JojaMartSeasonsStock("Potato Seeds", Seeds.Potato_Seeds, Season.Spring, 62, 5));
        inventory.add(new JojaMartSeasonsStock("Strawberry Seeds", Seeds.Strawberry_Seeds, Season.Spring, 100, 5));
        inventory.add(new JojaMartSeasonsStock("Tulip Bulb", Seeds.Tulip_Bulb, Season.Spring, 25, 5));
        inventory.add(new JojaMartSeasonsStock("Kale Seeds", Seeds.Kale_Seeds, Season.Spring, 87, 5));
        inventory.add(new JojaMartSeasonsStock("Coffee Beans", Seeds.Coffee_Bean, Season.Spring, 200, 1));
        inventory.add(new JojaMartSeasonsStock("Carrot Seeds", Seeds.Carrot_Seeds, Season.Spring, 5, 10));
        inventory.add(new JojaMartSeasonsStock("Rhubarb Seeds", Seeds.Rhubarb_Seeds, Season.Spring, 100, 5));
        inventory.add(new JojaMartSeasonsStock("Jazz Seeds", Seeds.Jazz_Seeds, Season.Spring, 37, 5));

        // Summer
        inventory.add(new JojaMartSeasonsStock("Tomato Seeds", Seeds.Tomato_Seeds, Season.Summer, 62, 5));
        inventory.add(new JojaMartSeasonsStock("Pepper Seeds", Seeds.Pepper_Seeds, Season.Summer, 50, 5));
        inventory.add(new JojaMartSeasonsStock("Wheat Seeds", Seeds.Wheat_Seeds, Season.Summer, 12, 10));
        inventory.add(new JojaMartSeasonsStock("Summer Squash Seeds", Seeds.Summer_Squash_Seeds, Season.Summer, 10, 10));
        inventory.add(new JojaMartSeasonsStock("Radish Seeds", Seeds.Radish_Seeds, Season.Summer, 50, 5));
        inventory.add(new JojaMartSeasonsStock("Melon Seeds", Seeds.Melon_Seeds, Season.Summer, 100, 5));
        inventory.add(new JojaMartSeasonsStock("Hops Starter", Seeds.Hops_Starter, Season.Summer, 75, 5));
        inventory.add(new JojaMartSeasonsStock("Poppy Seeds", Seeds.Poppy_Seeds, Season.Summer, 125, 5));
        inventory.add(new JojaMartSeasonsStock("Spangle Seeds", Seeds.Spangle_Seeds, Season.Summer, 62, 5));
        inventory.add(new JojaMartSeasonsStock("Starfruit Seeds", Seeds.Starfruit_Seeds, Season.Summer, 400, 5));
        inventory.add(new JojaMartSeasonsStock("Coffee Beans", Seeds.Coffee_Bean, Season.Summer, 200, 1));
        inventory.add(new JojaMartSeasonsStock("Sunflower Seeds", Seeds.Sunflower_Seeds, Season.Summer, 125, 5));

        // Fall
        inventory.add(new JojaMartSeasonsStock("Corn Seeds", Seeds.Corn_Seeds, Season.Fall, 187, 5));
        inventory.add(new JojaMartSeasonsStock("Eggplant Seeds", Seeds.Eggplant_Seeds, Season.Fall, 25, 5));
        inventory.add(new JojaMartSeasonsStock("Pumpkin Seeds", Seeds.Pumpkin_Seeds, Season.Fall, 125, 5));
        inventory.add(new JojaMartSeasonsStock("Broccoli Seeds", Seeds.Broccoli_Seeds, Season.Fall, 15, 5));
        inventory.add(new JojaMartSeasonsStock("Amaranth Seeds", Seeds.Amaranth_Seeds, Season.Fall, 87, 5));
        inventory.add(new JojaMartSeasonsStock("Grape Starter", Seeds.Grape_Starter, Season.Fall, 75, 5));
        inventory.add(new JojaMartSeasonsStock("Beet Seeds", Seeds.Beet_Seeds, Season.Fall, 20, 5));
        inventory.add(new JojaMartSeasonsStock("Yam Seeds", Seeds.Yam_Seeds, Season.Fall, 75, 5));
        inventory.add(new JojaMartSeasonsStock("Bok Choy Seeds", Seeds.Bok_Choy_Seeds, Season.Fall, 62, 5));
        inventory.add(new JojaMartSeasonsStock("Cranberry Seeds", Seeds.Cranberry_Seeds, Season.Fall, 300, 5));
        inventory.add(new JojaMartSeasonsStock("Sunflower Seeds", Seeds.Sunflower_Seeds, Season.Fall, 125, 5));
        inventory.add(new JojaMartSeasonsStock("Fairy Seeds", Seeds.Fairy_Seeds, Season.Fall, 250, 5));
        inventory.add(new JojaMartSeasonsStock("Rare Seed", Seeds.Rare_Seed, Season.Fall, 1000, 1));
        inventory.add(new JojaMartSeasonsStock("Wheat Seeds", Seeds.Wheat_Seeds, Season.Fall, 12, 5));

        // Winter
        inventory.add(new JojaMartSeasonsStock("Powdermelon Seeds", Seeds.Powdermelon_Seeds, Season.Winter, 20, 10));

        //Permanent stock
        // inventory.add(new JojaMartSeasonsStock("Ancient Seed", Seeds.AncientSeeds, Season.Special, 500, 1));
        inventory.add(new ShopItem("Joja cola", 75, Integer.MAX_VALUE));

    }

    @Override
    public String showAllProducts() {
        StringBuilder message = new StringBuilder("JojaMart products:");
        for (ShopItem item : inventory) {
            message.append("\n" + "Name: ").append(item.name).append("  Price: ").append(item.price);
        }
        return message.toString();
    }

    @Override
    public String showAvailableProducts() {
        StringBuilder message = new StringBuilder("JojaMart Available Products:");
        for (ShopItem item : inventory) {
            if (item.remainingQuantity > 0) {
                message.append("\nName: ").append(item.name).append("   Price: ").append(item.price).append("   " +
                        "Remaining: ").append(item.remainingQuantity);
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

        if (App.getGameState().getCurrentPlayer().getBackpack().isInventoryFull()) {
            return new Response(false, "Not enough capacity in your inventory");
        }

        if (item instanceof JojaMartSeasonsStock) {

            // if ((!App.getGameState().getGameTime().getSeason().equals(((JojaMartSeasonsStock) item).getSeason())) &&
            //         (!((JojaMartSeasonsStock) item).getSeason().equals(Season.Special))) {
            //     return new Response(false, "you can't buy this item in this season");
            // }

            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(((JojaMartSeasonsStock) item).getSeedType(), value);
            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new Coin(), (-1) * totalPrice);

        } else {

            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(FoodType.JojaCola, value);
            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new Coin(), (-1) * totalPrice);

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
        return SymbolType.JojaMart;
    }
}
