package com.yourgame.model.Farming;

import com.yourgame.model.WeatherAndTime.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public enum TreeType {
    ApricotTree(TreeSource.Apricot_Sapling, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.Apricot, 1, 59, true, 38, Season.Spring),
    CherryTree(TreeSource.Cherry_Sapling, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.Cherry, 1, 80, true, 38, Season.Spring),
    BananaTree(TreeSource.Banana_Sapling, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.Banana, 1, 150, true, 75, Season.Summer),
    MangoTree(TreeSource.Mango_Sapling, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.Mango, 1, 130, true, 100, Season.Summer),
    OrangeTree(TreeSource.Orange_Sapling, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.Orange, 1, 100, true, 38, Season.Summer),
    PeachTree(TreeSource.Peach_Sapling, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.Peach, 1, 140, true, 38, Season.Summer),
    AppleTree(TreeSource.Apple_Sapling, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.Apple, 1, 100, true, 38, Season.Fall),
    PomegranateTree(TreeSource.Pomegranate_Sapling, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.Pomegranate, 1, 140, true, 38, Season.Fall),
    OakTree(TreeSource.Acorn, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.OakResin, 7, 150, false, 0, Season.Winter),
    MapleTree(TreeSource.Maple_Seed, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.MapleSyrup, 9, 200, false, 0, Season.Winter),
    PineTree(TreeSource.Pine_Cone, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.PineTar, 5, 100, false, 0, Season.Winter),
    MahoganyTree(TreeSource.Mahogany_Seed, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.Sap, 1, 2, true, -2, Season.Winter),
    MushroomTree(TreeSource.Mushroom_Tree_Seed, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.CommonMushroom, 1, 40, true, 38, Season.Winter),
    MysticTree(TreeSource.Mystic_Tree_Seed, new ArrayList<>(Arrays.asList(7, 7, 7, 7)), 28,
            Fruit.MysticSyrup, 7, 1000, true, 500, Season.Winter),;

    private final TreeSource source;
    private final ArrayList<Integer> stages;
    private final int totalHarvestTime;
    private final Fruit fruit;
    private final int harvestCycle;
    private final int fruitBaseSellPrice;
    private final boolean isFruitEdible;
    private final int fruitEnergy;
    private final Season season;
    private final static HashMap<TreeSource, TreeType> sourceToType = new HashMap<>();

    static {
        for (TreeType value : TreeType.values()) {
            sourceToType.put(value.getSource(), value);
        }
    }

    public static TreeType getTreeTypeBySource(TreeSource source) {
        return sourceToType.getOrDefault(source, null);
    }

    TreeType(TreeSource source, ArrayList<Integer> stages, int totalHarvestTime, Fruit fruit,
             final int harvestCycle, int fruitBaseSellPrice, boolean isFruitEdible, int fruitEnergy, Season season) {
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruit = fruit;
        this.harvestCycle = harvestCycle;
        this.fruitBaseSellPrice = fruitBaseSellPrice;
        this.isFruitEdible = isFruitEdible;
        this.fruitEnergy = fruitEnergy;
        this.season = season;
    }

    public TreeSource getSource() {
        return source;
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public int getTimeForGrow(int level) {
        return stages.get(level);
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getHarvestCycle() {
        return harvestCycle;
    }

    public int getFruitBaseSellPrice() {
        return fruitBaseSellPrice;
    }

    public boolean isFruitEdible() {
        return isFruitEdible;
    }

    public int getFruitEnergy() {
        return fruitEnergy;
    }

    public Season getSeason() {
        return season;
    }

    public String getName(){
        return name();
    }
}
