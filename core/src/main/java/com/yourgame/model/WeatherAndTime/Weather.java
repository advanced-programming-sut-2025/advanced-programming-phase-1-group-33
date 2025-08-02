package com.yourgame.model.WeatherAndTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Weather {
    Sunny(
            "sunny", 1.5,
            new ArrayList<Season>(Arrays.asList(Season.Spring, Season.Summer, Season.Fall, Season.Winter))),
    Rainy(
            "rainy", 1.2,
            new ArrayList<Season>(Arrays.asList(Season.Spring, Season.Summer, Season.Fall))),
    Stormy(
            "stormy", 0.5,
            new ArrayList<Season>(Arrays.asList(Season.Spring, Season.Summer, Season.Fall))),
    Snowy(
            "snowy", 1,
            new ArrayList<Season>(List.of(Season.Winter)));

    private final String name;
    private final double effectivenessOnFishing;
    private final ArrayList<Season> seasons = new ArrayList<>();

    Weather(String name, double effectivenessOnFishing, ArrayList<Season> seasons) {
        this.name = name;
        this.effectivenessOnFishing = effectivenessOnFishing;
        this.seasons.addAll(seasons);
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public String getName() {
        return name;
    }

    public double getEffectivenessOnFishing() {
        return effectivenessOnFishing;
    }
}
