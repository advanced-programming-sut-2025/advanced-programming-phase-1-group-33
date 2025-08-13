package com.yourgame.model.Map.Store.PierreGeneralStore;

import com.yourgame.model.Farming.TreeSource;
import com.yourgame.model.Map.Store.ShopItem;

public class PierreGeneralStoreSaplingItem extends ShopItem {

    private final TreeSource source;


    public PierreGeneralStoreSaplingItem(String name, ItemType type, int price, boolean isStackable, int dailyLimit, String filePath, TreeSource source) {
        super(name, type, price, isStackable, dailyLimit, filePath);
        this.source = source;
    }

   public TreeSource getSource() {
        return source;
   }
}
