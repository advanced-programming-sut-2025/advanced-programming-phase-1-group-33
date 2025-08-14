package com.yourgame.model.Crafting;

public class CraftingRecipeSource {
    private final int miningSkillLevelNeeded;
    private final int foragingSkillLevelNeeded;
    private final int farmingSkillLevelNeeded;
    private final boolean isStarter;
    private boolean isBought;

    public CraftingRecipeSource(int miningSkillLevelNeeded, int foragingSkillLevelNeeded, int farmingSkillLevelNeeded, boolean isStarter, boolean isBought) {
        this.miningSkillLevelNeeded = miningSkillLevelNeeded;
        this.foragingSkillLevelNeeded = foragingSkillLevelNeeded;
        this.farmingSkillLevelNeeded = farmingSkillLevelNeeded;
        this.isStarter = isStarter;
        this.isBought = isBought;
    }

    public int getMiningSkillLevelNeeded() {
        return miningSkillLevelNeeded;
    }

    public int getForagingSkillLevelNeeded() {
        return foragingSkillLevelNeeded;
    }

    public int getFarmingSkillLevelNeeded() {
        return farmingSkillLevelNeeded;
    }

    public boolean isStarter() {
        return isStarter;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }
}
