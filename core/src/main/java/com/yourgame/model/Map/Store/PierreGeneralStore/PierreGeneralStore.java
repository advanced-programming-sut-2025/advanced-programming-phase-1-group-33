package com.yourgame.model.Map.Store.PierreGeneralStore;

import java.util.ArrayList;

import com.yourgame.model.Farming.Seeds;
import com.yourgame.model.Farming.TreeSource;
import com.yourgame.model.Map.Store.ShopItem;
import com.yourgame.model.Map.Store.Store;
<<<<<<< HEAD
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.TimeObserver;
import com.yourgame.model.WeatherAndTime.TimeSystem;
=======
>>>>>>> 02f48e0ca227dfe367fab0a89b09519eaf2783e3

public class PierreGeneralStore extends Store implements TimeObserver {
    private ArrayList<ShopItem> inventory;

    public PierreGeneralStore(String name, String pathToTmx, int startHour, int endHour) {
        super(name, pathToTmx, startHour, endHour);
        loadInventory();
    }

    @Override
    public void loadInventory() {
        inventory = new ArrayList<>();

        // Backpack Upgrades
//        inventory.add(new PierreGeneralStoreBackPackUpgrade(
//        "Large Pack", Item.ItemType.BACKPACK, 2000, true, 1, "Game/Map/Buildings/PierreGeneralStore/Backpack.png", BackpackType.Big
//        ));
//        inventory.add(new PierreGeneralStoreBackPackUpgrade(
//            "Deluxe Pack", Item.ItemType.BACKPACK, 10000, true, 1, "Game/Map/Buildings/PierreGeneralStore/36_Backpack.png", BackpackType.Deluxe
//        ));

        // Saplings
        inventory.add(new ShopItem(new TreeSource.TreeSourceItem(TreeSource.Apple_Sapling), -1));
        inventory.add(new ShopItem(new TreeSource.TreeSourceItem(TreeSource.Apricot_Sapling), -1));
        inventory.add(new ShopItem(new TreeSource.TreeSourceItem(TreeSource.Cherry_Sapling), -1));
        inventory.add(new ShopItem(new TreeSource.TreeSourceItem(TreeSource.Orange_Sapling), -1));
        inventory.add(new ShopItem(new TreeSource.TreeSourceItem(TreeSource.Peach_Sapling), -1));
        inventory.add(new ShopItem(new TreeSource.TreeSourceItem(TreeSource.Pomegranate_Sapling), -1));

        // Spring Seeds
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Parsnip_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Bean_Starter), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Cauliflower_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Potato_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Tulip_Bulb), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Kale_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Jazz_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Garlic_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Rice_Shoot), 5));

        // Summer Seeds
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Melon_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Tomato_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Blueberry_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Pepper_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Wheat_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Radish_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Poppy_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Spangle_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Hops_Starter), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Corn_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Sunflower_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Red_Cabbage_Seeds), 5));


        // Fall Seeds
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Eggplant_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Corn_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Pumpkin_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Bok_Choy_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Yam_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Cranberry_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Sunflower_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Fairy_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Amaranth_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Grape_Starter), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Wheat_Seeds), 5));
        inventory.add(new ShopItem(new Seeds.SeedItem(Seeds.Artichoke_Seeds), 5));

        // Misc Items
//        inventory.add(new ShopItem("Rice", Item.ItemType.CROP, 200, true, -1, "Game/Map/Buildings/PierreGeneralStore/Rice.png"));
//        inventory.add(new ShopItem("Bouquet", null, 1000, true, 2, "Game/Map/Buildings/PierreGeneralStore/Bouquet.png"));
//        inventory.add(new ShopItem("Dehydrator", Item.ItemType.MACHINE, 10000, false, 1, "Game/Map/Buildings/PierreGeneralStore/Dehydrator.png"));
//        inventory.add(new ShopItem("Oil", Item.ItemType.INGREDIENT, 200, true, -1, "Game/Map/Buildings/PierreGeneralStore/Oil.png"));
//        inventory.add(new ShopItem("Vinegar", Item.ItemType.INGREDIENT, 200, true, -1, "Game/Map/Buildings/PierreGeneralStore/Vinegar.png"));
    }

    public ArrayList<ShopItem> getInventory() {
        return inventory;
    }


    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        loadInventory();
    }
}
