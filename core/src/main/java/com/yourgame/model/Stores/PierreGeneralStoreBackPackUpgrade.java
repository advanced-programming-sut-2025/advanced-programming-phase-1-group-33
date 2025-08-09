package com.yourgame.model.Stores;


import com.yourgame.model.Item.Inventory.BackpackType;

public class PierreGeneralStoreBackPackUpgrade extends ShopItem {

    private final BackpackType backpackType;


    public PierreGeneralStoreBackPackUpgrade(String name, BackpackType type ,int price, int dailyLimit) {
        super(name, price, dailyLimit);
        this.backpackType = type;
    }

    public BackpackType getBackpackType() {
        return backpackType;
    }
}
