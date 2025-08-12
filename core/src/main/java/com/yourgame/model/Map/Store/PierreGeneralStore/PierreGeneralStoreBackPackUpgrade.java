package com.yourgame.model.Map.Store.PierreGeneralStore;

import com.yourgame.model.Item.Inventory.BackpackType;
import com.yourgame.model.Map.Store.ShopItem;

public class PierreGeneralStoreBackPackUpgrade extends ShopItem {

    private final BackpackType backpackType;


    public PierreGeneralStoreBackPackUpgrade(String name, ItemType type, int price, boolean isStackable, int dailyLimit, String filePath, BackpackType backpackType) {
        super(name, type, price, isStackable, dailyLimit, filePath);
        this.backpackType = backpackType;
    }

    public BackpackType getBackpackType() {
        return backpackType;
    }
}
