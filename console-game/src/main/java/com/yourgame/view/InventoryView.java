package com.yourgame.view;

import com.yourgame.model.Inventory.BackPack;
import com.yourgame.model.Item.Item;

import java.util.Map;

//public class InventoryView {
//
//    public void displayInventory(BackPack inventory) {
//        System.out.println("Inventory:");
//        for (Map.Entry<Item, Integer> entry : inventory.getItems().entrySet()) {
//            Item item = entry.getKey();
//            int count = entry.getValue();
//            System.out.println("- " + item.getName() + " (x" + count + "): " + item.getDescription());
//        }
//    }
//
//    public void displayItemTooltip(Item     item) {
//        System.out.println("Item: " + item.getName());
//        System.out.println("Description: " + item.getDescription());
//        System.out.println("Sell Price: " + item.getSellPrice());
//        System.out.println("Stackable: " + (item.isStackable() ? "Yes" : "No"));
//    }
//}