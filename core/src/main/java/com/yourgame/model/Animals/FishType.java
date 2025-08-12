package com.yourgame.model.Animals;

import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Skill.Skill;
import com.yourgame.model.WeatherAndTime.Season;

import java.util.ArrayList;
import java.util.HashMap;

public enum FishType implements Ingredient {
    Salmon(75, Season.Fall),
    Sardine(40, Season.Fall),
    Shad(60, Season.Fall),
    BlueDiscus(120, Season.Fall),
    MidnightCarp(150, Season.Winter),
    Squid(80, Season.Winter),
    Tuna(100, Season.Winter),
    Perch(55, Season.Winter),
    Flounder(100, Season.Spring),
    Lionfish(100, Season.Spring),
    Herring(30, Season.Spring),
    GhostFish(45, Season.Spring),
    Tilapia(75, Season.Summer),
    Dorado(100, Season.Summer),
    Sunfish(30, Season.Summer),
    RainbowTrout(65, Season.Summer),

    //LEGENDARY_FISH
    Legend(5000, Season.Spring),
    GlacierFish(1000, Season.Winter),
    Angler(900, Season.Fall),
    CrimsonFish(1500, Season.Summer);


    private final int price;
    private final Season season;
    private final static HashMap<String, FishType> stringToFishType = new HashMap<>();

    static {
        for (FishType value : FishType.values()) {
            stringToFishType.put(value.name().toLowerCase(), value);
        }
    }


    FishType(int price, Season season) {
        this.price = price;
        this.season = season;
    }


    public int getPrice() {
        return price;
    }

    public Season getSeason() {
        return season;
    }

    public static FishType getFishTypeByName(String name) {
        return stringToFishType.getOrDefault(name.toLowerCase(), null);
    }

    public static ArrayList<FishType> getFishesBySeason(Season season) {
        ArrayList<FishType> fishes = new ArrayList<>();
        for (FishType fish : FishType.values()) {
            if (fish.equals(Legend) || fish.equals(GlacierFish) || fish.equals(Angler) || fish.equals(CrimsonFish))
                continue;
            if (fish.getSeason() == season)
                fishes.add(fish);
        }
        return fishes;
    }

    public static ArrayList<FishType> getFishesBySeason(Season season, int fishingLevel) {
        ArrayList<FishType> fishes = getFishesBySeason(season);
        if (fishingLevel == Skill.MAX_LEVEL) {
            fishes.add(
                    switch (season) {
                        case Spring -> Legend;
                        case Winter -> GlacierFish;
                        case Fall -> Angler;
                        case Summer -> CrimsonFish;
                        default -> null;
                    });
        }
        return fishes;
    }
}
