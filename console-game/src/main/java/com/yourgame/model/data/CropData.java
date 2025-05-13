package com.yourgame.model.data;

import com.yourgame.model.Weather.Season;

import java.util.List;

public class CropData {
    private String name;
    private String seedName;
    private List<Integer> stages; // Days per stage
    private int regrowDays;
    private List<Season> seasons;
    private int baseSellPrice;
    private boolean isGiantCropPossible;

    public CropData(String name, String seedName, List<Integer> stages, int regrowDays,
                    List<Season> seasons, int baseSellPrice, boolean isGiantCropPossible) {
        this.name = name;
        this.seedName = seedName;
        this.stages = stages;
        this.regrowDays = regrowDays;
        this.seasons = seasons;
        this.baseSellPrice = baseSellPrice;
        this.isGiantCropPossible = isGiantCropPossible;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSeedName() {
        return seedName;
    }

    public List<Integer> getStages() {
        return stages;
    }

    public int getRegrowDays() {
        return regrowDays;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isGiantCropPossible() {
        return isGiantCropPossible;
    }
}