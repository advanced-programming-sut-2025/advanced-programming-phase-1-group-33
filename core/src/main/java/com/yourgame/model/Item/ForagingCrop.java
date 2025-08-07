package com.yourgame.model.Item;

import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.WeatherAndTime.Season;

import java.util.ArrayList;
import java.util.HashMap;

public enum ForagingCrop implements Ingredient {
    Common_Mushroom(Season.Special, 40, 38),
    Daffodil(Season.Spring, 30, 0),
    Dandelion(Season.Spring, 40, 25),
    Leek(Season.Spring, 60, 40),
    Morel(Season.Spring, 150, 20),
    Salmonberry(Season.Spring, 5, 25),
    Spring_Onion(Season.Spring, 8, 13),
    Wild_Horseradish(Season.Spring, 50, 13),
    Fiddlehead_Fern(Season.Summer, 90, 25),
    Grape(Season.Summer, 80, 38),
    Red_Mushroom(Season.Summer, 75, -50),
    Spice_Berry(Season.Summer, 80, 25),
    Sweet_Pea(Season.Summer, 50, 0),
    Blackberry(Season.Fall, 25, 25),
    Chanterelle(Season.Fall, 160, 75),
    Hazelnut(Season.Fall, 40, 38),
    Purple_Mushroom(Season.Fall, 90, 30),
    Wild_Plum(Season.Fall, 80, 25),
    Crocus(Season.Winter, 60, 0),
    Crystal_Fruit(Season.Winter, 150, 63),
    Holly(Season.Winter, 80, -37),
    Snow_Yam(Season.Winter, 100, 30),
    Winter_Root(Season.Winter, 70, 25);

    private final Season season;
    private final int baseSellPrice;
    private final int energy;
    private final static HashMap<String, ForagingCrop> stringToForagingCrop = new HashMap<>();

    static {
        for (ForagingCrop value : ForagingCrop.values()) {
            stringToForagingCrop.put(value.name().toLowerCase(), value);
        }
    }

    ForagingCrop(Season season, int baseSellPrice, int energy) {
        this.season = season;
        this.baseSellPrice = baseSellPrice;
        this.energy = energy;
    }

    public String getName(){
        return name();
    }

    public Season getSeason() {
        return season;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    public static ArrayList<ForagingCrop> getCropsBySeason(Season season) {
        ArrayList<ForagingCrop> crops = new ArrayList<>();
        for (ForagingCrop crop : values()) {
            if (crop.season == season) {
                crops.add(crop);
            }
        }
        if (season != Season.Special) {
            crops.add(ForagingCrop.Common_Mushroom);
        }
        return crops;
    }

    public static ForagingCrop getForagingCropByName(String name) {
        if (name == null || name.isEmpty())
            return null;
        return stringToForagingCrop.getOrDefault(name.toLowerCase(), null);
    }

    public static ForagingCrop getByName(String name) {
        for (ForagingCrop type : ForagingCrop.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

}
