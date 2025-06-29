package com.yourgame.model.Stores;


import com.yourgame.model.Animals.HabitatSize;
import com.yourgame.model.Animals.HabitatType;

public class CarpenterShopFarmBuildingsItem extends ShopItem {

    private final int woodCost;
    private final int StoneCost;
    private final HabitatType habitatType;
    private final HabitatSize habitatSize;
    private final boolean isShippingBin;


    public CarpenterShopFarmBuildingsItem(String name,int price, int woodCost, int stoneCost,int dailyLimit) {
        super(name, price, dailyLimit);
        this.woodCost = woodCost;
        this.StoneCost = stoneCost;
        this.isShippingBin = true;
        this.habitatType = null;
        this.habitatSize = null;
    }

    public CarpenterShopFarmBuildingsItem(String name, HabitatType habitatType, HabitatSize habitatSize, int price, int woodCost, int stoneCost, int dailyLimit) {
        super(name, price, dailyLimit);
        this.habitatType = habitatType;
        this.habitatSize = habitatSize;
        this.woodCost = woodCost;
        this.StoneCost = stoneCost;
        this.isShippingBin = false;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public boolean isShippingBin() {
        return isShippingBin;
    }

    public HabitatSize getHabitatSize() {
        return habitatSize;
    }

    public HabitatType getHabitatType() {
        return habitatType;
    }

    public int getStoneCost() {
        return StoneCost;
    }
}
