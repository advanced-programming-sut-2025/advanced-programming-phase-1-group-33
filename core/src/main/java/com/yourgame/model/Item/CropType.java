package com.yourgame.model.Item;

import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.WeatherAndTime.Season;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum CropType implements Ingredient {
    BlueJazz(Seeds.JazzSeeds,
            new ArrayList<>(Arrays.asList(1, 2, 2, 2)),
            7, true, 0, 50, true, 45,
            new ArrayList<>(List.of(Season.Spring)), false),
    Carrot(Seeds.CarrotSeeds,
            new ArrayList<>(Arrays.asList(1, 1, 1)),
            3, true, 0, 35, true, 75,
            new ArrayList<>(List.of(Season.Spring)), false),
    Cauliflower(Seeds.CauliflowerSeeds,
            new ArrayList<>(Arrays.asList(1, 2, 4, 4, 1)),
            12, true, 0, 175, true, 75,
            new ArrayList<>(List.of(Season.Spring)), true),
    CoffeeBean(Seeds.CoffeeBean,
            new ArrayList<>(Arrays.asList(1, 2, 2, 3, 2)),
            10, false, 2, 15, false, 0,
            new ArrayList<>(Arrays.asList(Season.Spring, Season.Summer)), false),
    Garlic(Seeds.GarlicSeeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1)),
            4, true, 0, 60, true, 20,
            new ArrayList<>(List.of(Season.Spring)), false),
    GreenBean(Seeds.BeanStarter,
            new ArrayList<>(Arrays.asList(1, 1, 1, 3, 4)),
            10, false, 3, 40, true, 25,
            new ArrayList<>(List.of(Season.Spring)), false),
    Kale(Seeds.KaleSeeds,
            new ArrayList<>(Arrays.asList(1, 2, 2, 1)),
            6, true, 0, 110, true, 50,
            new ArrayList<>(List.of(Season.Spring)), false),
    Parsnip(Seeds.ParsnipSeeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1)),
            4, true, 0, 35, true, 25,
            new ArrayList<>(List.of(Season.Spring)), false),
    Potato(Seeds.PotatoSeeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 2, 1)),
            6, true, 0, 80, true, 25,
            new ArrayList<>(List.of(Season.Spring)), false),
    Rhubarb(Seeds.RhubarbSeeds,
            new ArrayList<>(Arrays.asList(2, 2, 2, 3, 4)),
            13, true, 0, 220, false, 0,
            new ArrayList<>(List.of(Season.Spring)), false),
    Strawberry(Seeds.StrawberrySeeds,
            new ArrayList<>(Arrays.asList(1, 1, 2, 2, 2)),
            8, false, 4, 120, true, 50,
            new ArrayList<>(List.of(Season.Spring)), false),
    Tulip(Seeds.TulipBulb,
            new ArrayList<>(Arrays.asList(1, 1, 2, 2)),
            6, true, 0, 30, true, 45,
            new ArrayList<>(List.of(Season.Spring)), false),
    UnMilledRice(Seeds.RiceShoot,
            new ArrayList<>(Arrays.asList(1, 2, 2, 3)),
            8, true, 0, 30, true, 3,
            new ArrayList<>(List.of(Season.Spring)), false),
    Blueberry(Seeds.BlueberrySeeds,
            new ArrayList<>(Arrays.asList(1, 3, 3, 4, 2)),
            13, false, 4, 50, true, 25,
            new ArrayList<>(List.of(Season.Summer)), false),
    Corn(Seeds.CornSeeds,
            new ArrayList<>(Arrays.asList(2, 3, 3, 3, 3)),
            14, false, 4, 50, true, 25,
            new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall)), false),
    Hops(Seeds.HopsStarter,
            new ArrayList<>(Arrays.asList(1, 1, 2, 3, 4)),
            11, false, 1, 25, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false),
    HotPepper(Seeds.PepperSeeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1)),
            5, false, 3, 40, true, 13,
            new ArrayList<>(List.of(Season.Summer)), false),
    Melon(Seeds.MelonSeeds,
            new ArrayList<>(Arrays.asList(1, 2, 3, 3, 3)),
            12, true, 0, 250, true, 113,
            new ArrayList<>(List.of(Season.Summer)), true),
    Poppy(Seeds.PoppySeeds,
            new ArrayList<>(Arrays.asList(1, 2, 2, 2)),
            7, true, 0, 140, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false),
    Radish(Seeds.RadishSeeds,
            new ArrayList<>(Arrays.asList(2, 1, 2, 1)),
            6, true, 0, 90, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false),
    RedCabbage(Seeds.RedCabbageSeeds,
            new ArrayList<>(Arrays.asList(2, 1, 2, 2, 2)),
            9, true, 0, 260, true, 75,
            new ArrayList<>(List.of(Season.Summer)), false),
    Starfruit(Seeds.StarfruitSeeds,
            new ArrayList<>(Arrays.asList(2, 3, 2, 3, 3)),
            13, true, 0, 750, true, 125,
            new ArrayList<>(List.of(Season.Summer)), false),
    SummerSpangle(Seeds.SpangleSeeds,
            new ArrayList<>(Arrays.asList(1, 2, 3, 1)),
            8, true, 0, 90, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false),
    SummerSquash(Seeds.SummerSquashSeeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 2, 1)),
            6, false, 3, 45, true, 63,
            new ArrayList<>(List.of(Season.Summer)), false),
    Sunflower(Seeds.SunflowerSeeds,
            new ArrayList<>(Arrays.asList(1, 2, 3, 2)),
            8, true, 0, 80, true, 45,
            new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall)), false),
    Tomato(Seeds.TomatoSeeds,
            new ArrayList<>(Arrays.asList(2, 2, 2, 2, 3)),
            11, false, 4, 60, true, 20,
            new ArrayList<>(List.of(Season.Summer)), false),
    Wheat(Seeds.WheatSeeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1)),
            4, true, 0, 25, false, 0,
            new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall)), false),
    Amaranth(Seeds.AmaranthSeeds,
            new ArrayList<>(Arrays.asList(1, 2, 2, 2)),
            7, true, 0, 150, true, 50,
            new ArrayList<>(List.of(Season.Fall)), false),
    Artichoke(Seeds.ArtichokeSeeds,
            new ArrayList<>(Arrays.asList(2, 2, 1, 2, 1)),
            8, true, 0, 160, true, 30,
            new ArrayList<>(List.of(Season.Fall)), false),
    Beet(Seeds.BeetSeeds,
            new ArrayList<>(Arrays.asList(1, 1, 2, 2)),
            6, true, 0, 100, true, 30,
            new ArrayList<>(List.of(Season.Fall)), false),
    BokChoy(Seeds.BokChoySeeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1)),
            4, true, 0, 80, true, 25,
            new ArrayList<>(List.of(Season.Fall)), false),
    Broccoli(Seeds.BroccoliSeeds,
            new ArrayList<>(Arrays.asList(2, 2, 2, 2)),
            8, false, 4, 70, true, 63,
            new ArrayList<>(List.of(Season.Fall)), false),
    Cranberries(Seeds.CranberrySeeds,
            new ArrayList<>(Arrays.asList(1, 2, 1, 1, 2)),
            7, false, 5, 75, true, 38,
            new ArrayList<>(List.of(Season.Fall)), false),
    Eggplant(Seeds.EggplantSeeds,
            new ArrayList<>(Arrays.asList(1, 1, 1, 1)),
            5, false, 5, 60, true, 20,
            new ArrayList<>(List.of(Season.Fall)), false),
    FairyRose(Seeds.FairySeeds,
            new ArrayList<>(Arrays.asList(1, 4, 4, 3)),
            12, true, 0, 290, true, 45,
            new ArrayList<>(List.of(Season.Fall)), false),
    Grape(Seeds.GrapeStarter,
            new ArrayList<>(Arrays.asList(1, 1, 2, 3, 3)),
            10, false, 3, 80, true, 38,
            new ArrayList<>(List.of(Season.Fall)), false),
    Pumpkin(Seeds.PumpkinSeeds,
            new ArrayList<>(Arrays.asList(1, 2, 3, 4, 3)),
            13, true, 0, 320, false, 0,
            new ArrayList<>(List.of(Season.Fall)), true),
    Yam(Seeds.YamSeeds,
            new ArrayList<>(Arrays.asList(1, 3, 3, 3)),
            10, true, 0, 160, true, 45,
            new ArrayList<>(List.of(Season.Fall)), false),
    SweetGemBerry(Seeds.RareSeed,
            new ArrayList<>(Arrays.asList(2, 4, 6, 6, 6)),
            24, true, 0, 3000, false, 0,
            new ArrayList<>(List.of(Season.Fall)), false),
    PowderMelon(Seeds.PowdermelonSeeds,
            new ArrayList<>(Arrays.asList(1, 2, 1, 2, 1)),
            7, true, 0, 60, true, 63,
            new ArrayList<>(List.of(Season.Winter)), true),
    AncientFruit(Seeds.AncientSeeds,
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
}
