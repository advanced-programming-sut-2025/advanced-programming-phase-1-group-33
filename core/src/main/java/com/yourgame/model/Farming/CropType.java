package com.yourgame.model.Farming;

import com.yourgame.model.Item.ArtisanIngredient;
import com.yourgame.model.ManuFactor.Artisan.ArtisanProduct;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.WeatherAndTime.Season;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum CropType implements Ingredient, ArtisanIngredient {
    Blue_Jazz(Seeds.Jazz_Seeds,
            new ArrayList<>(Arrays.asList(1, 2, 2, 2)),
            7, true, 0, 50, true, 45,
            new ArrayList<>(List.of(Season.Spring)), false),
    Carrot(Seeds.Carrot_Seeds,
            new ArrayList<>(Arrays.asList(1, 1, 1)),
            3, true, 0, 35, true, 75,
            new ArrayList<>(List.of(Season.Spring)), false),
    Cauliflower(Seeds.Cauliflower_Seeds,
            new ArrayList<>(Arrays.asList(1, 2, 4, 4, 1)),
            12, true, 0, 175, true, 75,
            new ArrayList<>(List.of(Season.Spring)), true),
    Coffee_Bean(Seeds.Coffee_Bean,
            new ArrayList<>(Arrays.asList(1, 2, 2, 3, 2)),
            10, false, 2, 15, false, 0,
            new ArrayList<>(Arrays.asList(Season.Spring, Season.Summer)), false),
    Garlic(Seeds.Garlic_Seeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1)),
            4, true, 0, 60, true, 20,
            new ArrayList<>(List.of(Season.Spring)), false),
    Green_Bean(Seeds.Bean_Starter,
            new ArrayList<>(Arrays.asList(1, 1, 1, 3, 4)),
            10, false, 3, 40, true, 25,
            new ArrayList<>(List.of(Season.Spring)), false),
    Kale(Seeds.Kale_Seeds,
            new ArrayList<>(Arrays.asList(1, 2, 2, 1)),
            6, true, 0, 110, true, 50,
            new ArrayList<>(List.of(Season.Spring)), false),
    Parsnip(Seeds.Parsnip_Seeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1)),
            4, true, 0, 35, true, 25,
            new ArrayList<>(List.of(Season.Spring)), false),
    Potato(Seeds.Potato_Seeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 2, 1)),
            6, true, 0, 80, true, 25,
            new ArrayList<>(List.of(Season.Spring)), false),
    Rhubarb(Seeds.Rhubarb_Seeds,
            new ArrayList<>(Arrays.asList(2, 2, 2, 3, 4)),
            13, true, 0, 220, false, 0,
            new ArrayList<>(List.of(Season.Spring)), false),
    Strawberry(Seeds.Strawberry_Seeds,
            new ArrayList<>(Arrays.asList(1, 1, 2, 2, 2)),
            8, false, 4, 120, true, 50,
            new ArrayList<>(List.of(Season.Spring)), false),
    Tulip(Seeds.Tulip_Bulb,
            new ArrayList<>(Arrays.asList(1, 1, 2, 2)),
            6, true, 0, 30, true, 45,
            new ArrayList<>(List.of(Season.Spring)), false),
    Unmilled_Rice(Seeds.Rice_Shoot,
            new ArrayList<>(Arrays.asList(1, 2, 2, 3)),
            8, true, 0, 30, true, 3,
            new ArrayList<>(List.of(Season.Spring)), false),
    Blueberry(Seeds.Blueberry_Seeds,
            new ArrayList<>(Arrays.asList(1, 3, 3, 4, 2)),
            13, false, 4, 50, true, 25,
            new ArrayList<>(List.of(Season.Summer)), false),
    Corn(Seeds.Corn_Seeds,
            new ArrayList<>(Arrays.asList(2, 3, 3, 3, 3)),
            14, false, 4, 50, true, 25,
            new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall)), false),
    Hops(Seeds.Hops_Starter,
            new ArrayList<>(Arrays.asList(1, 1, 2, 3, 4)),
            11, false, 1, 25, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false),
    Hot_Pepper(Seeds.Pepper_Seeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1)),
            5, false, 3, 40, true, 13,
            new ArrayList<>(List.of(Season.Summer)), false),
    Melon(Seeds.Melon_Seeds,
            new ArrayList<>(Arrays.asList(1, 2, 3, 3, 3)),
            12, true, 0, 250, true, 113,
            new ArrayList<>(List.of(Season.Summer)), true),
    Poppy(Seeds.Poppy_Seeds,
            new ArrayList<>(Arrays.asList(1, 2, 2, 2)),
            7, true, 0, 140, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false),
    Radish(Seeds.Radish_Seeds,
            new ArrayList<>(Arrays.asList(2, 1, 2, 1)),
            6, true, 0, 90, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false),
    Red_Cabbage(Seeds.Red_Cabbage_Seeds,
            new ArrayList<>(Arrays.asList(2, 1, 2, 2, 2)),
            9, true, 0, 260, true, 75,
            new ArrayList<>(List.of(Season.Summer)), false),
    Starfruit(Seeds.Starfruit_Seeds,
            new ArrayList<>(Arrays.asList(2, 3, 2, 3, 3)),
            13, true, 0, 750, true, 125,
            new ArrayList<>(List.of(Season.Summer)), false),
    Summer_Spangle(Seeds.Spangle_Seeds,
            new ArrayList<>(Arrays.asList(1, 2, 3, 1)),
            8, true, 0, 90, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false),
    Summer_Squash(Seeds.Summer_Squash_Seeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 2, 1)),
            6, false, 3, 45, true, 63,
            new ArrayList<>(List.of(Season.Summer)), false),
    Sunflower(Seeds.Sunflower_Seeds,
            new ArrayList<>(Arrays.asList(1, 2, 3, 2)),
            8, true, 0, 80, true, 45,
            new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall)), false),
    Tomato(Seeds.Tomato_Seeds,
            new ArrayList<>(Arrays.asList(2, 2, 2, 2, 3)),
            11, false, 4, 60, true, 20,
            new ArrayList<>(List.of(Season.Summer)), false),
    Wheat(Seeds.Wheat_Seeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1)),
            4, true, 0, 25, false, 0,
            new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall)), false),
    Amaranth(Seeds.Amaranth_Seeds,
            new ArrayList<>(Arrays.asList(1, 2, 2, 2)),
            7, true, 0, 150, true, 50,
            new ArrayList<>(List.of(Season.Fall)), false),
    Artichoke(Seeds.Artichoke_Seeds,
            new ArrayList<>(Arrays.asList(2, 2, 1, 2, 1)),
            8, true, 0, 160, true, 30,
            new ArrayList<>(List.of(Season.Fall)), false),
    Beet(Seeds.Beet_Seeds,
            new ArrayList<>(Arrays.asList(1, 1, 2, 2)),
            6, true, 0, 100, true, 30,
            new ArrayList<>(List.of(Season.Fall)), false),
    Bok_Choy(Seeds.Bok_Choy_Seeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1)),
            4, true, 0, 80, true, 25,
            new ArrayList<>(List.of(Season.Fall)), false),
    Broccoli(Seeds.Broccoli_Seeds,
            new ArrayList<>(Arrays.asList(2, 2, 2, 2)),
            8, false, 4, 70, true, 63,
            new ArrayList<>(List.of(Season.Fall)), false),
    Cranberry(Seeds.Cranberry_Seeds,
            new ArrayList<>(Arrays.asList(1, 2, 1, 1, 2)),
            7, false, 5, 75, true, 38,
            new ArrayList<>(List.of(Season.Fall)), false),
    Eggplant(Seeds.Eggplant_Seeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1)),
            5, false, 5, 60, true, 20,
            new ArrayList<>(List.of(Season.Fall)), false),
    Fairy_Rose(Seeds.Fairy_Seeds,
            new ArrayList<>(Arrays.asList(1, 4, 4, 3)),
            12, true, 0, 290, true, 45,
            new ArrayList<>(List.of(Season.Fall)), false),
    Grape(Seeds.Grape_Starter,
            new ArrayList<>(Arrays.asList(1, 1, 2, 3, 3)),
            10, false, 3, 80, true, 38,
            new ArrayList<>(List.of(Season.Fall)), false),
    Pumpkin(Seeds.Pumpkin_Seeds,
            new ArrayList<>(Arrays.asList(1, 2, 3, 4, 3)),
            13, true, 0, 320, false, 0,
            new ArrayList<>(List.of(Season.Fall)), true),
    Yam(Seeds.Yam_Seeds,
            new ArrayList<>(Arrays.asList(1, 3, 3, 3)),
            10, true, 0, 160, true, 45,
            new ArrayList<>(List.of(Season.Fall)), false),
    SweetGemBerry(Seeds.Rare_Seed,
            new ArrayList<>(Arrays.asList(2, 4, 6, 6, 6)),
            24, true, 0, 3000, false, 0,
            new ArrayList<>(List.of(Season.Fall)), false),
    PowderMelon(Seeds.Powdermelon_Seeds,
            new ArrayList<>(Arrays.asList(1, 2, 1, 2, 1)),
            7, true, 0, 60, true, 63,
            new ArrayList<>(List.of(Season.Winter)), true),
    AncientFruit(Seeds.Ancient_Seeds,
            new ArrayList<>(Arrays.asList(2, 7, 7, 7, 5)),
            28, false, 7, 550, false, 0,
            new ArrayList<>(Arrays.asList(Season.Spring, Season.Summer, Season.Fall)), false);

    private final Seeds source;
    private final ArrayList<Integer> stages;
    private final int totalHarvestTime;
    private final boolean oneTime;
    private final int regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final ArrayList<Season> Seasons;
    private final boolean canBecomeGiant;

    private final static HashMap<String, CropType> stringToCropType = new HashMap<>();
    // Add a map for Seeds to CropType lookup
    private final static HashMap<Seeds, CropType> seedsToCropType = new HashMap<>();


    static {
        for (CropType value : CropType.values()) {
            stringToCropType.put(value.name().toLowerCase(), value);
            // Populate the Seeds to CropType map
            if (value.source != null) { // Handle cases like MixedSeeds in your original Seeds enum
                seedsToCropType.put(value.source, value);
            }
        }
    }

    CropType(Seeds source, ArrayList<Integer> stages, int totalHarvestTime, boolean oneTime, int regrowthTime,
            int baseSellPrice, boolean isEdible, int energy, ArrayList<Season> seasons,
            boolean canBecomeGiant) {
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        Seasons = seasons;
        this.canBecomeGiant = canBecomeGiant;
    }

    public String getName() {
        return name();
    }

    public Seeds getSource() {
        return source;
    }

    public int getTimeForGrow(int level) {
        return stages.get(level);
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public int getNumberOfStages() {
        return stages.size();
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public ArrayList<Season> getSeasons() {
        return Seasons;
    }

    public boolean CanBecomeGiant() {
        return canBecomeGiant;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String cropTypeDetail() {
        return "CropType: " + getName() +
                "\n  Source Seed: " + (source != null ? source.name() : "None") +
                "\n  Stages: " + stages +
                "\n  Total Harvest Time: " + totalHarvestTime +
                "\n  One Time Harvest: " + oneTime +
                "\n  Regrowth Time: " + regrowthTime +
                "\n  Base Sell Price: " + baseSellPrice +
                "\n  Edible: " + isEdible +
                "\n  Energy: " + energy +
                "\n  Seasons: " + Seasons +
                "\n  Can Become Giant: " + canBecomeGiant;
    }

    public static CropType getCropTypeByName(String name) {
        if (name == null || name.isEmpty())
            return null;
        return stringToCropType.getOrDefault(name.toLowerCase(), null);
    }

    // Add a static method to look up CropType by Seed
    public static CropType getCropForSeed(Seeds seed) {
        if (seed == null) {
            return null;
        }
        return seedsToCropType.get(seed);
    }

    @Override
    public int getBaseEnergy() {
        return energy;
    }

    @Override
    public int getBasePrice() {
        return baseSellPrice;
    }
}
