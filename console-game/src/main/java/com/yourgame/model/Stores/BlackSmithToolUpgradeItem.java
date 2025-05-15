package com.yourgame.model.Stores;


import com.yourgame.model.ManuFactor.ArtisanGoodType;

public class BlackSmithToolUpgradeItem extends ShopItem {

    private final int requiredAmountForUpgrade;
    private final ArtisanGoodType goodTypeForUpgrade;

    public BlackSmithToolUpgradeItem(String name, ArtisanGoodType goodTypeForUpgrade, int requiredAmountForUpgrade , int price, Integer dailyLimit) {
        super(name, price, dailyLimit);
        this.goodTypeForUpgrade = goodTypeForUpgrade;
        this.requiredAmountForUpgrade = requiredAmountForUpgrade;
    }

    public int getRequiredAmountForUpgrade() {
        return requiredAmountForUpgrade;
    }

    public ArtisanGoodType getGoodTypeForUpgrade() {
        return goodTypeForUpgrade;
    }

}
