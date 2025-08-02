package com.yourgame.model.Stores;


import com.yourgame.model.Item.ForagingMineral;

public class BlackSmithStocksItem extends ShopItem {

    private final ForagingMineral type;

    public BlackSmithStocksItem(String name, ForagingMineral type, int price, Integer dailyLimit) {
        super(name, price, dailyLimit);
        this.type = type;
    }

    public ForagingMineral getType() {
        return type;
    }

}
