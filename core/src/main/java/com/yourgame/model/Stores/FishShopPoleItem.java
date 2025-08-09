package com.yourgame.model.Stores;


import com.yourgame.model.Item.Tools.PoleStage;

public class FishShopPoleItem extends ShopItem{

    private final PoleStage type;
    private final int fishingSkillRequired;


    public FishShopPoleItem(String name, PoleStage type, int fishingSkillRequired, int price, int dailyLimit) {
        super(name, price, dailyLimit);
        this.type = type;
        this.fishingSkillRequired = fishingSkillRequired;
    }

    public PoleStage getType() {
        return type;
    }

    public int getFishingSkillRequired() {
        return fishingSkillRequired;
    }
}
