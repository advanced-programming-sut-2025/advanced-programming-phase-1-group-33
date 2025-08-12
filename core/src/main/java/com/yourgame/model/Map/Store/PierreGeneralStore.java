package com.yourgame.model.Map.Store;

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
import com.yourgame.model.Stores.PierreGeneralStoreBackPackUpgrade;
import com.yourgame.model.Stores.PierreGeneralStoreSaplingItem;
import com.yourgame.model.Stores.PierreGeneralStoreSeedsItem;
import com.yourgame.model.Stores.ShopItem;
import com.yourgame.model.UserInfo.Coin;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.enums.SymbolType;

public class PierreGeneralStore extends Store {
    private ArrayList<ShopItem> inventory;

    public PierreGeneralStore(String name, String pathToTmx, int startHour, int endHour) {
        super(name, pathToTmx, startHour, endHour);
        loadInventory();
    }

    @Override
    public void loadInventory() {

        inventory = new ArrayList<>();

        //BackpackUpgrade
        inventory.add(new PierreGeneralStoreBackPackUpgrade("Large Pack", BackpackType.Big, 2000, 1));
        inventory.add(new PierreGeneralStoreBackPackUpgrade("Deluxe Pack", BackpackType.Deluxe, 10000, 1));

        //Saplings
        inventory.add(new PierreGeneralStoreSaplingItem("Apple Sapling", TreeSource.Apple_Sapling, 4000,
                Integer.MAX_VALUE));
        inventory.add(new PierreGeneralStoreSaplingItem("Apricot Sapling", TreeSource.Apple_Sapling, 2000,
                Integer.MAX_VALUE));
        inventory.add(new PierreGeneralStoreSaplingItem("Cherry Sapling", TreeSource.Apple_Sapling, 3400,
                Integer.MAX_VALUE));
        inventory.add(new PierreGeneralStoreSaplingItem("Orange Sapling", TreeSource.Apple_Sapling, 4000,
                Integer.MAX_VALUE));
        inventory.add(new PierreGeneralStoreSaplingItem("Peach Sapling", TreeSource.Apple_Sapling, 6000,
                Integer.MAX_VALUE));
        inventory.add(new PierreGeneralStoreSaplingItem("Pomegranate Sapling", TreeSource.Apple_Sapling, 6000,
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
}
