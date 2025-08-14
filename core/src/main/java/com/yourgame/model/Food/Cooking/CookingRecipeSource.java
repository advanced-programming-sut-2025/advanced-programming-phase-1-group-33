package com.yourgame.model.Food.Cooking;

public class CookingRecipeSource {
    private final int miningSkillLevelNeeded;
    private final int foragingSkillLevelNeeded;
    private final int fishingSkillLevelNeeded;
    private final int farmingSkillLevelNeeded;
    private final boolean isStarter;
    private final boolean isLeahReward;
    private boolean isBought;

    public CookingRecipeSource(int miningSkillLevelNeeded, int foragingSkillLevelNeeded, int fishingSkillLevelNeeded, int farmingSkillLevelNeeded, boolean isStarter, boolean isLeahReward, boolean isBought) {
        this.miningSkillLevelNeeded = miningSkillLevelNeeded;
        this.foragingSkillLevelNeeded = foragingSkillLevelNeeded;
        this.fishingSkillLevelNeeded = fishingSkillLevelNeeded;
        this.farmingSkillLevelNeeded = farmingSkillLevelNeeded;
        this.isStarter = isStarter;
        this.isLeahReward = isLeahReward;
        this.isBought = isBought;
    }

    public int getMiningSkillLevelNeeded() {
        return miningSkillLevelNeeded;
    }

    public int getForagingSkillLevelNeeded() {
        return foragingSkillLevelNeeded;
    }

    public int getFishingSkillLevelNeeded() {
        return fishingSkillLevelNeeded;
    }

    public int getFarmingSkillLevelNeeded() {
        return farmingSkillLevelNeeded;
    }

    public boolean isStarter() {
        return isStarter;
    }

    public boolean isLeahReward() {
        return isLeahReward;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }
}
