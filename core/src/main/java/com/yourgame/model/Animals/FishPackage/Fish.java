package com.yourgame.model.Animals.FishPackage;

import com.yourgame.model.Animals.FishType;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.WeatherAndTime.Season;

import java.util.ArrayList;

public enum Fish implements Ingredient {
    Salmon("Salmon", 75, Season.Fall),
    Sardine("Sardine", 40, Season.Fall),
    Shad("Shad", 60, Season.Fall),
    BlueDiscus("BlueDiscus", 120, Season.Fall),
    MidnightCarp("MidnightCarp", 150, Season.Winter),
    Squid("Squid", 80, Season.Winter),
    Tuna("Tuna", 100, Season.Winter),
    Perch("Perch", 55, Season.Winter),
    Flounder("Flounder", 100, Season.Spring),
    Lionfish("Lionfish", 100, Season.Spring),
    Herring("Herring", 30, Season.Spring),
    GhostFish("GhostFish", 45, Season.Spring),
    Tilapia("Tilapia", 75, Season.Summer),
    Dorado("Dorado", 100, Season.Summer),
    Sunfish("Sunfish", 30, Season.Summer),
    RainbowTrout("RainbowTrout", 65, Season.Summer),

    // LEGENDARY_FISH
    Legend("Legend", 5000, Season.Spring),
    GlacierFish("GlacierFish", 1000, Season.Winter),
    Angler("Angler", 900, Season.Fall),
    CrimsonFish("CrimsonFish", 1500, Season.Summer);

    private final String name;
    private final int price;
    private final Season season;

    Fish(String name, int price, Season season) {
        this.name = name;
        this.price = price;
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Season getSeason() {
        return season;
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
}

