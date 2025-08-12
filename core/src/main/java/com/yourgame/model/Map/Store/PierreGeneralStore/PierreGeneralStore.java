package com.yourgame.model.Map.Store.PierreGeneralStore;

import java.util.ArrayList;

import com.yourgame.model.Item.Inventory.BackpackType;
import com.yourgame.model.Farming.Seeds;
import com.yourgame.model.Farming.TreeSource;
import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.Store.ShopItem;
import com.yourgame.model.Map.Store.Store;
import com.yourgame.model.WeatherAndTime.Season;

public class PierreGeneralStore extends Store {
    private ArrayList<ShopItem> inventory;

    public PierreGeneralStore(String name, String pathToTmx, int startHour, int endHour) {
        super(name, pathToTmx, startHour, endHour);
        loadInventory();
    }

    @Override
    public void loadInventory() {
        inventory = new ArrayList<>();

        // Backpack Upgrades
        inventory.add(new PierreGeneralStoreBackPackUpgrade(
        "Large Pack", Item.ItemType.BACKPACK, 2000, true, 1, "Game/Map/Buildings/PierreGeneralStore/Backpack.png", BackpackType.Big
        ));
        inventory.add(new PierreGeneralStoreBackPackUpgrade(
            "Deluxe Pack", Item.ItemType.BACKPACK, 10000, true, 1, "Game/Map/Buildings/PierreGeneralStore/36_Backpack.png", BackpackType.Deluxe
        ));

        // Saplings
        inventory.add(new PierreGeneralStoreSaplingItem("Apple Sapling", Item.ItemType.CROP, 4000, true, -1, "Game/Map/Buildings/PierreGeneralStore/Apple_Sapling.png", TreeSource.Apple_Sapling));
        inventory.add(new PierreGeneralStoreSaplingItem("Apricot Sapling", Item.ItemType.CROP, 2000, true, -1, "Game/Map/Buildings/PierreGeneralStore/Apricot_Sapling.png", TreeSource.Apricot_Sapling));
        inventory.add(new PierreGeneralStoreSaplingItem("Cherry Sapling", Item.ItemType.CROP, 3400, true, -1, "Game/Map/Buildings/PierreGeneralStore/Cherry_Sapling.png", TreeSource.Cherry_Sapling));
        inventory.add(new PierreGeneralStoreSaplingItem("Orange Sapling", Item.ItemType.CROP, 4000, true, -1, "Game/Map/Buildings/PierreGeneralStore/Orange_Sapling.png", TreeSource.Orange_Sapling));
        inventory.add(new PierreGeneralStoreSaplingItem("Peach Sapling", Item.ItemType.CROP, 6000, true, -1, "Game/Map/Buildings/PierreGeneralStore/Peach_Sapling.png", TreeSource.Peach_Sapling));
        inventory.add(new PierreGeneralStoreSaplingItem("Pomegranate Sapling", Item.ItemType.CROP, 6000, true, -1, "Game/Map/Buildings/PierreGeneralStore/Pomegranate_Sapling.png", TreeSource.Pomegranate_Sapling));

        // Spring Seeds
        inventory.add(new PierreGeneralStoreSeedsItem("Parsnip Seeds", Item.ItemType.CROP, 30, 20, true, 5, "Game/Map/Buildings/PierreGeneralStore/Parsnip_Seeds.png", Seeds.Parsnip_Seeds, Season.Spring));
        inventory.add(new PierreGeneralStoreSeedsItem("Bean Starter", Item.ItemType.CROP, 90, 60, true, 5, "Game/Map/Buildings/PierreGeneralStore/Bean_Starter.png", Seeds.Bean_Starter, Season.Spring));
        inventory.add(new PierreGeneralStoreSeedsItem("Cauliflower Seeds", Item.ItemType.CROP, 120, 80, true, 5, "Game/Map/Buildings/PierreGeneralStore/Cauliflower_Seeds.png", Seeds.Cauliflower_Seeds, Season.Spring));
        inventory.add(new PierreGeneralStoreSeedsItem("Potato Seeds", Item.ItemType.CROP, 75, 50, true, 5, "Game/Map/Buildings/PierreGeneralStore/Potato_Seeds.png", Seeds.Potato_Seeds, Season.Spring));
        inventory.add(new PierreGeneralStoreSeedsItem("Tulip Bulb", Item.ItemType.CROP, 30, 20, true, 5, "Game/Map/Buildings/PierreGeneralStore/Tulip_Bulb.png", Seeds.Tulip_Bulb, Season.Spring));
        inventory.add(new PierreGeneralStoreSeedsItem("Kale Seeds", Item.ItemType.CROP, 105, 70, true, 5, "Game/Map/Buildings/PierreGeneralStore/Kale_Seeds.png", Seeds.Kale_Seeds, Season.Spring));
        inventory.add(new PierreGeneralStoreSeedsItem("Jazz Seeds", Item.ItemType.CROP, 45, 30, true, 5, "Game/Map/Buildings/PierreGeneralStore/Jazz_Seeds.png", Seeds.Jazz_Seeds, Season.Spring));
        inventory.add(new PierreGeneralStoreSeedsItem("Garlic Seeds", Item.ItemType.CROP, 60, 40, true, 5, "Game/Map/Buildings/PierreGeneralStore/Garlic_Seeds.png", Seeds.Garlic_Seeds, Season.Spring));
        inventory.add(new PierreGeneralStoreSeedsItem("Rice Shoot", Item.ItemType.CROP, 60, 40, true, 5, "Game/Map/Buildings/PierreGeneralStore/Rice_Shoot.png", Seeds.Rice_Shoot, Season.Spring));

        // Summer Seeds
        inventory.add(new PierreGeneralStoreSeedsItem("Melon Seeds", Item.ItemType.CROP, 120, 80, true, 5, "Game/Map/Buildings/PierreGeneralStore/Melon_Seeds.png", Seeds.Melon_Seeds, Season.Summer));
        inventory.add(new PierreGeneralStoreSeedsItem("Tomato Seeds", Item.ItemType.CROP, 75, 50, true, 5, "Game/Map/Buildings/PierreGeneralStore/Tomato_Seeds.png", Seeds.Tomato_Seeds, Season.Summer));
        inventory.add(new PierreGeneralStoreSeedsItem("Blueberry Seeds", Item.ItemType.CROP, 120, 80, true, 5, "Game/Map/Buildings/PierreGeneralStore/Blueberry_Seeds.png", Seeds.Blueberry_Seeds, Season.Summer));
        inventory.add(new PierreGeneralStoreSeedsItem("Pepper Seeds", Item.ItemType.CROP, 60, 40, true, 5, "Game/Map/Buildings/PierreGeneralStore/Pepper_Seeds.png", Seeds.Pepper_Seeds, Season.Summer));
        inventory.add(new PierreGeneralStoreSeedsItem("Wheat Seeds", Item.ItemType.CROP, 15, 10, true, 5, "Game/Map/Buildings/PierreGeneralStore/Wheat_Seeds.png", Seeds.Wheat_Seeds, Season.Summer));
        inventory.add(new PierreGeneralStoreSeedsItem("Radish Seeds", Item.ItemType.CROP, 60, 40, true, 5, "Game/Map/Buildings/PierreGeneralStore/Radish_Seeds.png", Seeds.Radish_Seeds, Season.Summer));
        inventory.add(new PierreGeneralStoreSeedsItem("Poppy Seeds", Item.ItemType.CROP, 150, 100, true, 5, "Game/Map/Buildings/PierreGeneralStore/Poppy_Seeds.png", Seeds.Poppy_Seeds, Season.Summer));
        inventory.add(new PierreGeneralStoreSeedsItem("Spangle Seeds", Item.ItemType.CROP, 75, 50, true, 5, "Game/Map/Buildings/PierreGeneralStore/Spangle_Seeds.png", Seeds.Spangle_Seeds, Season.Summer));
        inventory.add(new PierreGeneralStoreSeedsItem("Hops Starter", Item.ItemType.CROP, 90, 60, true, 5, "Game/Map/Buildings/PierreGeneralStore/Hops_Starter.png", Seeds.Hops_Starter, Season.Summer));
        inventory.add(new PierreGeneralStoreSeedsItem("Corn Seeds", Item.ItemType.CROP, 225, 150, true, 5, "Game/Map/Buildings/PierreGeneralStore/Corn_Seeds.png", Seeds.Corn_Seeds, Season.Summer));
        inventory.add(new PierreGeneralStoreSeedsItem("Sunflower Seeds", Item.ItemType.CROP, 300, 200, true, 5, "Game/Map/Buildings/PierreGeneralStore/Sunflower_Seeds.png", Seeds.Sunflower_Seeds, Season.Summer));
        inventory.add(new PierreGeneralStoreSeedsItem("Red Cabbage Seeds", Item.ItemType.CROP, 150, 100, true, 5, "Game/Map/Buildings/PierreGeneralStore/Red_Cabbage_Seeds.png", Seeds.Red_Cabbage_Seeds, Season.Summer));


        // Fall Seeds
        inventory.add(new PierreGeneralStoreSeedsItem("Eggplant Seeds", Item.ItemType.CROP, 30, 20, true, 5, "Game/Map/Buildings/PierreGeneralStore/Eggplant_Seeds.png", Seeds.Eggplant_Seeds, Season.Fall));
        inventory.add(new PierreGeneralStoreSeedsItem("Corn Seeds", Item.ItemType.CROP, 225, 150, true, 5, "Game/Map/Buildings/PierreGeneralStore/Corn_Seeds.png", Seeds.Corn_Seeds, Season.Fall));
        inventory.add(new PierreGeneralStoreSeedsItem("Pumpkin Seeds", Item.ItemType.CROP, 150, 100, true, 5, "Game/Map/Buildings/PierreGeneralStore/Pumpkin_Seeds.png", Seeds.Pumpkin_Seeds, Season.Fall));
        inventory.add(new PierreGeneralStoreSeedsItem("Bok Choy Seeds", Item.ItemType.CROP, 75, 50, true, 5, "Game/Map/Buildings/PierreGeneralStore/Bok_Choy_Seeds.png", Seeds.Bok_Choy_Seeds, Season.Fall));
        inventory.add(new PierreGeneralStoreSeedsItem("Yam Seeds", Item.ItemType.CROP, 90, 60, true, 5, "Game/Map/Buildings/PierreGeneralStore/Yam_Seeds.png", Seeds.Yam_Seeds, Season.Fall));
        inventory.add(new PierreGeneralStoreSeedsItem("Cranberry Seeds", Item.ItemType.CROP, 360, 240, true, 5, "Game/Map/Buildings/PierreGeneralStore/Cranberry_Seeds.png", Seeds.Cranberry_Seeds, Season.Fall));
        inventory.add(new PierreGeneralStoreSeedsItem("Sunflower Seeds", Item.ItemType.CROP, 300, 200, true, 5, "Game/Map/Buildings/PierreGeneralStore/Sunflower_Seeds.png", Seeds.Sunflower_Seeds, Season.Fall));
        inventory.add(new PierreGeneralStoreSeedsItem("Fairy Seeds", Item.ItemType.CROP, 300, 200, true, 5, "Game/Map/Buildings/PierreGeneralStore/Fairy_Seeds.png", Seeds.Fairy_Seeds, Season.Fall));
        inventory.add(new PierreGeneralStoreSeedsItem("Amaranth Seeds", Item.ItemType.CROP, 105, 70, true, 5, "Game/Map/Buildings/PierreGeneralStore/Amaranth_Seeds.png", Seeds.Amaranth_Seeds, Season.Fall));
        inventory.add(new PierreGeneralStoreSeedsItem("Grape Starter", Item.ItemType.CROP, 90, 60, true, 5, "Game/Map/Buildings/PierreGeneralStore/Grape_Starter.png", Seeds.Grape_Starter, Season.Fall));
        inventory.add(new PierreGeneralStoreSeedsItem("Wheat Seeds", Item.ItemType.CROP, 15, 10, true, 5, "Game/Map/Buildings/PierreGeneralStore/Wheat_Seeds.png", Seeds.Wheat_Seeds, Season.Fall));
        inventory.add(new PierreGeneralStoreSeedsItem("Artichoke Seeds", Item.ItemType.CROP, 45, 30, true, 5, "Game/Map/Buildings/PierreGeneralStore/Artichoke_Seeds.png", Seeds.Artichoke_Seeds, Season.Fall));

        // Misc Items
        inventory.add(new ShopItem("Rice", Item.ItemType.CROP, 200, true, -1, "Game/Map/Buildings/PierreGeneralStore/Rice.png"));
        inventory.add(new ShopItem("Bouquet", null, 1000, true, 2, "Game/Map/Buildings/PierreGeneralStore/Bouquet.png"));
        inventory.add(new ShopItem("Dehydrator", Item.ItemType.MACHINE, 10000, false, 1, "Game/Map/Buildings/PierreGeneralStore/Dehydrator.png"));
        inventory.add(new ShopItem("Oil", Item.ItemType.INGREDIENT, 200, true, -1, "Game/Map/Buildings/PierreGeneralStore/Oil.png"));
        inventory.add(new ShopItem("Vinegar", Item.ItemType.INGREDIENT, 200, true, -1, "Game/Map/Buildings/PierreGeneralStore/Vinegar.png"));
    }

    public ArrayList<ShopItem> getInventory() {
        return inventory;
    }
}
