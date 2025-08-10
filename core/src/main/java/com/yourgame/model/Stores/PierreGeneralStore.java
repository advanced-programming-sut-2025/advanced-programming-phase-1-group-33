package com.yourgame.model.Stores;

import java.awt.*;
import java.util.ArrayList;

import com.yourgame.model.App;
import com.yourgame.model.IO.Response;
import com.yourgame.model.Item.Inventory.BackpackType;
import com.yourgame.model.Farming.CropType;
import com.yourgame.model.Farming.Seeds;
import com.yourgame.model.Farming.TreeSource;
import com.yourgame.model.ManuFactor.ArtisanGood;
import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.ManuFactor.Bouquet;
import com.yourgame.model.ManuFactor.Dehydrator;
import com.yourgame.model.UserInfo.Coin;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.enums.SymbolType;

public class PierreGeneralStore extends Store {
    private ArrayList<ShopItem> inventory;

    public PierreGeneralStore(int x, int y, int width, int height) {
        super(new Rectangle(x, y, width, height), "Pierre", 9, 23);
    }

    @Override
    public void loadInventory() {

        inventory = new ArrayList<>();

        //BackpackUpgrade
        inventory.add(new PierreGeneralStoreBackPackUpgrade("Large Pack", BackpackType.Big, 2000, 1));
        inventory.add(new PierreGeneralStoreBackPackUpgrade("Deluxe Pack", BackpackType.Deluxe, 10000, 1));

        //Saplings
        inventory.add(new PierreGeneralStoreSaplingItem("Apple Sapling", TreeSource.AppleSapling, 4000,
                Integer.MAX_VALUE));
        inventory.add(new PierreGeneralStoreSaplingItem("Apricot Sapling", TreeSource.AppleSapling, 2000,
                Integer.MAX_VALUE));
        inventory.add(new PierreGeneralStoreSaplingItem("Cherry Sapling", TreeSource.AppleSapling, 3400,
                Integer.MAX_VALUE));
        inventory.add(new PierreGeneralStoreSaplingItem("Orange Sapling", TreeSource.AppleSapling, 4000,
                Integer.MAX_VALUE));
        inventory.add(new PierreGeneralStoreSaplingItem("Peach Sapling", TreeSource.AppleSapling, 6000,
                Integer.MAX_VALUE));
        inventory.add(new PierreGeneralStoreSaplingItem("Pomegranate Sapling", TreeSource.AppleSapling, 6000,
                Integer.MAX_VALUE));

        //Spring Seeds
        inventory.add(new PierreGeneralStoreSeedsItem("Parsnip Seeds", Seeds.Parsnip_Seeds, Season.Spring, 30, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Bean Starter", Seeds.Bean_Starter, Season.Spring, 90, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Cauliflower Seeds", Seeds.Cauliflower_Seeds, Season.Spring, 120
                , 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Potato Seeds", Seeds.Potato_Seeds, Season.Spring, 75, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Tulip Bulb", Seeds.Tulip_Bulb, Season.Spring, 30, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Kale Seeds", Seeds.Kale_Seeds, Season.Spring, 105, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Jazz Seeds", Seeds.Jazz_Seeds, Season.Spring, 45, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Garlic Seeds", Seeds.Garlic_Seeds, Season.Spring, 60, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Rice Shoot", Seeds.Rice_Shoot, Season.Spring, 60, 5));

        //Summer Seeds
        inventory.add(new PierreGeneralStoreSeedsItem("Melon Seeds", Seeds.Melon_Seeds, Season.Summer, 120, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Tomato Seeds", Seeds.Tomato_Seeds, Season.Summer, 75, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Blueberry Seeds", Seeds.Blueberry_Seeds, Season.Summer, 120, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Pepper Seeds", Seeds.Pepper_Seeds, Season.Summer, 60, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Wheat Seeds", Seeds.Wheat_Seeds, Season.Summer, 15, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Radish Seeds", Seeds.Radish_Seeds, Season.Summer, 60, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Poppy Seeds", Seeds.Poppy_Seeds, Season.Summer, 150, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Spangle Seeds", Seeds.Spangle_Seeds, Season.Summer, 75, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Hops Starter", Seeds.Hops_Starter, Season.Summer, 90, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Corn Seeds", Seeds.Corn_Seeds, Season.Summer, 225, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Sunflower Seeds", Seeds.Sunflower_Seeds, Season.Summer, 300, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Red Cabbage Seeds", Seeds.Red_Cabbage_Seeds, Season.Summer, 150,
                5));

        //Fall Seeds
        inventory.add(new PierreGeneralStoreSeedsItem("Eggplant Seeds", Seeds.Eggplant_Seeds, Season.Fall, 30, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Corn Seeds", Seeds.Corn_Seeds, Season.Fall, 225, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Pumpkin Seeds", Seeds.Pumpkin_Seeds, Season.Fall, 150, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Bok Choy Seeds", Seeds.Bok_Choy_Seeds, Season.Fall, 75, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Yam Seeds", Seeds.Yam_Seeds, Season.Fall, 90, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Cranberry Seeds", Seeds.Cranberry_Seeds, Season.Fall, 360, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Sunflower Seeds", Seeds.Sunflower_Seeds, Season.Fall, 300, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Fairy Seeds", Seeds.Fairy_Seeds, Season.Fall, 300, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Amaranth Seeds", Seeds.Amaranth_Seeds, Season.Fall, 105, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Grape Starter", Seeds.Grape_Starter, Season.Fall, 90, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Wheat Seeds", Seeds.Wheat_Seeds, Season.Fall, 15, 5));
        inventory.add(new PierreGeneralStoreSeedsItem("Artichoke Seeds", Seeds.Artichoke_Seeds, Season.Fall, 45, 5));

        inventory.add(new ShopItem("Rice", 200, Integer.MAX_VALUE));
        inventory.add(new ShopItem("Bouquet", 1000, 2));
        inventory.add(new ShopItem("Dehydrator", 10000, 1));
        inventory.add(new ShopItem("Oil", 200, Integer.MAX_VALUE));
        inventory.add(new ShopItem("Vinegar", 200, Integer.MAX_VALUE));


    }

    @Override
    public String showAllProducts() {
        StringBuilder message = new StringBuilder("PierreGeneralStore products:");
        for (ShopItem item : inventory) {
            message.append("\n" + "Name: ").append(item.name).append("  Price: ").append(item.price);
        }
        return message.toString();
    }

    @Override
    public String showAvailableProducts() {
        StringBuilder message = new StringBuilder("PierreGeneralStore Available Products:");
        for (ShopItem item : inventory) {
            if (item.remainingQuantity > 0) {
                if (item instanceof PierreGeneralStoreSeedsItem && ((PierreGeneralStoreSeedsItem) item).getSeason().equals(App.getGameState().getGameTime().getSeason())) {

                    message.append("\nName: ").append(item.name).append("   Price: ").append((item.price * 2) / 3).append("   Remaining: ").append(item.remainingQuantity);
                } else {
                    message.append("\nName: ").append(item.name).append("   Price: ").append(item.price).append("   " +
                            "Remaining: ").append(item.remainingQuantity);
                }
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

        int totalPrice = calculatePrice(item) * value;

        if (App.getGameState().getCurrentPlayer().getBackpack().getIngredientQuantity().getOrDefault(new Coin(), 0) < totalPrice) {
            return new Response(false, "Not enough money");
        }

        if (item.getRemainingQuantity() < value) {
            return new Response(false, "Not enough stock");
        }

        if (item instanceof PierreGeneralStoreSeedsItem) {

            if (App.getGameState().getCurrentPlayer().getBackpack().isInventoryFull()) {
                return new Response(false, "Not enough capacity in your inventory");
            }

            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(((PierreGeneralStoreSeedsItem) item).getSeeds(), value);

        } else if (item instanceof PierreGeneralStoreBackPackUpgrade) {

            if (item.name.equals("Large Pack")) {

                if (! App.getGameState().getCurrentPlayer().getBackpack().getType().equals(BackpackType.Primary)) {
                    return new Response(false, "you can't upgrade the backpack to Large Pack from this level");
                }
                App.getGameState().getCurrentPlayer().getBackpack().changeType(BackpackType.Big);

            } else {

                if (App.getGameState().getCurrentPlayer().getBackpack().getType().equals(BackpackType.Primary)) {
                    return new Response(false, "you must first buy the Large Pack");
                }
                if (App.getGameState().getCurrentPlayer().getBackpack().getType().equals(BackpackType.Deluxe)) {
                    return new Response(false, "you can't upgrade the backpack to Deluxe Pack from this level");
                }
                App.getGameState().getCurrentPlayer().getBackpack().changeType(BackpackType.Deluxe);
            }

        } else if (item instanceof PierreGeneralStoreSaplingItem) {

            if (App.getGameState().getCurrentPlayer().getBackpack().isInventoryFull()) {
                return new Response(false, "Not enough capacity in your inventory");
            }

            App.getGameState().getCurrentPlayer().getBackpack().addIngredients(((PierreGeneralStoreSaplingItem) item).getSource(),value);

        } else {

            if (App.getGameState().getCurrentPlayer().getBackpack().isInventoryFull() && !item.name.equals("Dehydrator")) {
                return new Response(false, "Not enough capacity in your inventory");
            }

            switch (item.name) {

                case "Rice":{
                    App.getGameState().getCurrentPlayer().getBackpack().addIngredients(CropType.UnmilledRice,value);
                    break;
                }
               case "Bouquet":{
                   App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new Bouquet(),value);
                   break;
               }
                case "Dehydrator":{
                    App.getGameState().getCurrentPlayer().getBackpack().addArtisanMachine(new Dehydrator());
                    break;
                }
                case "Oil":{
                    App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new ArtisanGood(ArtisanGoodType.Oil),value);
                    break;
                }
                case "Vinegar":{
                    App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new ArtisanGood(ArtisanGoodType.Vinegar),value);
                    break;
                }
            }

        }

        App.getGameState().getCurrentPlayer().getBackpack().addIngredients(new Coin(), -1 * totalPrice);
        item.decreaseRemainingQuantity(value);
        return new Response(true, "You successfully purchased " + value + "number(s) of " + productName);
    }

    @Override
    public void ResetQuantityEveryNight() {
        for (ShopItem item : inventory) {
            item.resetQuantityEveryNight();
        }
    }

    private int calculatePrice(ShopItem item) {
        if (item instanceof PierreGeneralStoreSeedsItem) {
            if (App.getGameState().getGameTime().getSeason().equals(((PierreGeneralStoreSeedsItem) item).getSeason())) {
                return (item.price * 2) / 3;
            }
        }
        return item.getPrice();
    }

    @Override
    public SymbolType getSymbol() {
        return SymbolType.PierreGeneralStore;
    }

}
