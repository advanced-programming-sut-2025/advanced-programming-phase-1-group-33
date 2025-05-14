package com.yourgame.model.Skill;

import com.yourgame.model.Recipes.CookingRecipe;
import com.yourgame.model.Recipes.CraftingRecipes;
import com.yourgame.model.UserInfo.Player;

public class Ability {
    private int farmingLevel = 0;
    private int farmingRate = 0;
    private int miningLevel = 0;
    private int miningRate = 0;
    private int foragingLevel = 0;
    private int foragingRate = 0;
    private int fishingLevel = 0;
    private int fishingRate = 0;
    private final Player player;
    private final static int maxLevel = 4;

    public Ability(Player player) {
        this.player = player;
    }

    public static int getMaxLevel() {
        return maxLevel;
    }

    public static int getRequiredPointsForLevel(int level) {
        return 100 * level + 50;
    }

    public int getFarmingLevel() {
        return farmingLevel;
    }

    public int getFarmingRate() {
        return farmingRate;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getMiningRate() {
        return miningRate;
    }

    public int getForagingLevel() {
        return foragingLevel;
    }

    public int getForagingRate() {
        return foragingRate;
    }

    public int getFishingLevel() {
        return fishingLevel;
    }

    public int getFishingRate() {
        return fishingRate;
    }

    public void increaseFarmingRate(int amount) {
        farmingRate += amount;
        while (farmingLevel < maxLevel && farmingRate >= getRequiredPointsForLevel(farmingLevel + 1)) {
            farmingRate -= getRequiredPointsForLevel(farmingLevel + 1);
            increaseFarmingLevel();
        }
    }

    public void increaseMiningRate(int amount) {
        miningRate += amount;
        while (miningLevel < maxLevel && miningRate >= getRequiredPointsForLevel(miningLevel + 1)) {
            miningRate -= getRequiredPointsForLevel(miningLevel + 1);
            increaseMiningLevel();
        }
    }

    public void increaseForagingRate(int amount) {
        foragingRate += amount;
        while (foragingLevel < maxLevel && foragingRate >= getRequiredPointsForLevel(foragingLevel + 1)) {
            foragingRate -= getRequiredPointsForLevel(foragingLevel + 1);
            increaseForagingLevel();
        }
    }

    public void increaseFishingRate(int amount) {
        fishingRate += amount;
        while (fishingLevel < maxLevel && fishingRate >= getRequiredPointsForLevel(fishingLevel + 1)) {
            fishingRate -= getRequiredPointsForLevel(fishingLevel + 1);
            increaseFishingLevel();
        }
    }

    private void increaseFarmingLevel() {
        farmingLevel += 1;
        if (farmingLevel == 1) {
            player.getBackpack().addRecipe(CraftingRecipes.Sprinkler);
            player.getBackpack().addRecipe(CraftingRecipes.BeeHouse);
            player.getBackpack().addRecipe(CookingRecipe.FarmersLunch);
        }
        else if (farmingLevel == 2) {
            player.getBackpack().addRecipe(CraftingRecipes.QualitySprinkler);
            player.getBackpack().addRecipe(CraftingRecipes.DeluxeScarecrow);
            player.getBackpack().addRecipe(CraftingRecipes.CheesePress);
            player.getBackpack().addRecipe(CraftingRecipes.PreservesJar);
        }
        else if (farmingLevel == 3) {
            player.getBackpack().addRecipe(CraftingRecipes.IridiumSprinkler);
            player.getBackpack().addRecipe(CraftingRecipes.OilMaker);
            player.getBackpack().addRecipe(CraftingRecipes.Keg);
            player.getBackpack().addRecipe(CraftingRecipes.Loom);
        }
    }

    private void increaseMiningLevel() {
        miningLevel += 1;
        if (miningLevel == 1) {
            player.getBackpack().addRecipe(CraftingRecipes.CherryBomb);
            player.getBackpack().addRecipe(CookingRecipe.MinersTreat);
        }
        if (miningLevel == 2)
            player.getBackpack().addRecipe(CraftingRecipes.Bomb);
        if (miningLevel == 3)
            player.getBackpack().addRecipe(CraftingRecipes.MegaBomb);
    }

    private void increaseForagingLevel() {
        foragingLevel += 1;
        if (foragingLevel == 1)
            player.getBackpack().addRecipe(CraftingRecipes.CharcoalKiln);
        if (foragingLevel == 2)
            player.getBackpack().addRecipe(CookingRecipe.VegetableMedley);
        if (foragingLevel == 3)
            player.getBackpack().addRecipe(CookingRecipe.SurvivalBurger);
        if (foragingLevel == 4)
            player.getBackpack().addRecipe(CraftingRecipes.MysticTreeSeed);
    }

    private void increaseFishingLevel() {
        fishingLevel += 1;
        if (fishingLevel == 2)
            player.getBackpack().addRecipe(CookingRecipe.DishOTheSea);
        if (fishingLevel == 3)
            player.getBackpack().addRecipe(CookingRecipe.SeaFormPudding);
    }

}
