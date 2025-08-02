package com.yourgame.model.Stores;


import com.yourgame.model.Item.TreeSource;

public class PierreGeneralStoreSaplingItem extends ShopItem {

    private final TreeSource source;


    public PierreGeneralStoreSaplingItem(String name, TreeSource source,int price, int dailyLimit) {
        super(name, price, dailyLimit);
        this.source = source;
    }

   public TreeSource getSource() {
        return source;
   }
}
