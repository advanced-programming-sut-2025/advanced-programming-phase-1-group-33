package com.yourgame.model.WeatherAndTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Weather {
    Sunny(
        "sunny",
        "SunnyButton",
        1,
        1.5,
        new ArrayList<>(Arrays.asList(Season.Spring, Season.Summer, Season.Fall, Season.Winter))),
    Rainy(
        "rainy",
        "RainyButton",
        1.5,
        1.2,
        new ArrayList<>(Arrays.asList(Season.Spring, Season.Summer, Season.Fall))),
    Stormy(
        "stormy",
        "StormyButton",
        1.5,
        0.5,
        new ArrayList<>(Arrays.asList(Season.Spring, Season.Summer, Season.Fall))),
    Snowy(
        "snowy",
        "SnowyButton",
        2,
        1,
        new ArrayList<>(List.of(Season.Winter)));

    private final String name;
    private final String pathToButton;
    public final double energyCoefficient;
    private final double effectivenessOnFishing;
    private final ArrayList<Season> seasons = new ArrayList<>();

    Weather(String name, String pathToButton, double energyCoefficient, double effectivenessOnFishing, ArrayList<Season> seasons) {
        this.name = name;
        this.pathToButton = pathToButton;
        this.energyCoefficient = energyCoefficient;
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
