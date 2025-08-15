package com.yourgame.model.Animals.FishPackage;

import com.yourgame.model.Animals.FishType;
import com.yourgame.model.Item.ArtisanIngredient;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.WeatherAndTime.Season;

import java.util.ArrayList;

public enum Fish implements Ingredient, ArtisanIngredient {
    Salmon("Salmon", 75, Season.Fall, 68),
    Sardine("Sardine", 40, Season.Fall, 0),
    Shad("Shad", 60, Season.Fall, 0),
    BlueDiscus("BlueDiscus", 120, Season.Fall, 0),
    MidnightCarp("MidnightCarp", 150, Season.Winter, 0),
    Squid("Squid", 80, Season.Winter, 0),
    Tuna("Tuna", 100, Season.Winter, 0),
    Perch("Perch", 55, Season.Winter, 0),
    Flounder("Flounder", 100, Season.Spring, 0),
    Lionfish("Lionfish", 100, Season.Spring, 0),
    Herring("Herring", 30, Season.Spring, 0),
    GhostFish("GhostFish", 45, Season.Spring, 0),
    Tilapia("Tilapia", 75, Season.Summer, 0),
    Dorado("Dorado", 100, Season.Summer, 0),
    Sunfish("Sunfish", 30, Season.Summer, 0),
    RainbowTrout("RainbowTrout", 65, Season.Summer, 0),

    // LEGENDARY_FISH
    LEGEND("Legend", 5000, Season.Spring, 0),
    GLACIER_FISH("GlacierFish", 1000, Season.Winter, 0),
    ANGLER("Angler", 900, Season.Fall, 0),
    CRIMSON_FISH("CrimsonFish", 1500, Season.Summer, 0);

    private final String name;
    private final int price;
    private final Season season;
    private int energy;

    Fish(String name, int price, Season season, int energy) {
        this.name = name;
        this.price = price;
        this.season = season;
        this.energy = energy;
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


    @Override
    public int getBaseEnergy() {
        return energy;
    }

    @Override
    public int getBasePrice() {
        return price;
    }
}

