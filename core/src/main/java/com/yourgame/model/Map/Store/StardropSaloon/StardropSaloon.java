package com.yourgame.model.Map.Store.StardropSaloon;

import com.yourgame.model.Food.Food;
import com.yourgame.model.Food.FoodType;
import com.yourgame.model.Item.Inventory.InventorySlot;
import com.yourgame.model.Item.Item;
import com.yourgame.model.ManuFactor.Artisan.ArtisanProduct;
import com.yourgame.model.ManuFactor.Artisan.EdibleArtisanProduct.EdibleArtisanProductType;
import com.yourgame.model.Map.Store.Store;
import com.yourgame.model.WeatherAndTime.TimeObserver;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.util.ArrayList;

public class StardropSaloon extends Store implements TimeObserver {
    private ArrayList<InventorySlot> inventory;

    public StardropSaloon(String name, String pathToTmx, int startHour, int endHour) {
        super(name, pathToTmx, startHour, endHour);
        loadInventory();
    }

    @Override
    public void loadInventory() {
        inventory = new ArrayList<>();
        inventory.add(new InventorySlot(new ArtisanProduct(EdibleArtisanProductType.Beer),-1));
        inventory.add(new InventorySlot(new Food(FoodType.Salad),-1));
        inventory.add(new InventorySlot(new Food(FoodType.Bread),-1));
        inventory.add(new InventorySlot(new Food(FoodType.Spaghetti),-1));
        inventory.add(new InventorySlot(new Food(FoodType.Pizza),-1));
        inventory.add(new InventorySlot(new ArtisanProduct(EdibleArtisanProductType.Coffee),-1));
        inventory.add(new InventorySlot(new StardropSaloonItem("HashBrowns Recipe", Item.ItemType.RECIPE
        ,50,false),1));
        inventory.add(new InventorySlot(new StardropSaloonItem("Omelet Recipe", Item.ItemType.RECIPE
            ,100,false),1));
        inventory.add(new InventorySlot(new StardropSaloonItem("Pancakes Recipe", Item.ItemType.RECIPE
            ,100,false),1));
        inventory.add(new InventorySlot(new StardropSaloonItem("Bread Recipe", Item.ItemType.RECIPE
            ,100,false),1));
        inventory.add(new InventorySlot(new StardropSaloonItem("Tortilla Recipe", Item.ItemType.RECIPE
            ,100,false),1));
        inventory.add(new InventorySlot(new StardropSaloonItem("Pizza Recipe", Item.ItemType.RECIPE
            ,150,false),1));
        inventory.add(new InventorySlot(new StardropSaloonItem("MakiRoll Recipe", Item.ItemType.RECIPE
            ,300,false),1));
        inventory.add(new InventorySlot(new StardropSaloonItem("TripleShotEspresso Recipe", Item.ItemType.RECIPE
            ,5000,false),1));
        inventory.add(new InventorySlot(new StardropSaloonItem("Cookie Recipe", Item.ItemType.RECIPE
            ,300,false),1));
    }

    public ArrayList<InventorySlot> getInventory() {
        return inventory;
    }


    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        loadInventory();
    }
}
