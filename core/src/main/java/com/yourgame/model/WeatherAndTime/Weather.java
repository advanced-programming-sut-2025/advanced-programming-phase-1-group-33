package com.yourgame.model.WeatherAndTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Weather {
    Sunny(
            "sunny",
            "SunnyButton",
            1.5,
            new ArrayList<Season>(Arrays.asList(Season.Spring, Season.Summer, Season.Fall, Season.Winter))),
    Rainy(
            "rainy",
            "RainnyButton",
            1.2,
            new ArrayList<Season>(Arrays.asList(Season.Spring, Season.Summer, Season.Fall))),
    Stormy(
            "stormy",
            "StormyButton",
            0.5,
            new ArrayList<Season>(Arrays.asList(Season.Spring, Season.Summer, Season.Fall))),
    Snowy(
            "snowy",
            "SnowyButton",
            1,
            new ArrayList<Season>(List.of(Season.Winter)));

    private final String name;
    private final String pathToButton;

    private final double effectivenessOnFishing;
    private final ArrayList<Season> seasons = new ArrayList<>();

    Weather(String name, String pathToButton, double effectivenessOnFishing, ArrayList<Season> seasons) {
        this.name = name;
        this.pathToButton = pathToButton;
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

    public String getButtonPath() {
        return pathToButton;
    }
}
