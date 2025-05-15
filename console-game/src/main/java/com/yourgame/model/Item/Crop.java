package com.yourgame.model.Item;

import com.yourgame.model.App;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Map.Placeable;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.awt.*;

public class Crop implements Ingredient, Growable , Placeable {
    private final CropType type;
    private int levelOfGrowth;
    private TimeSystem lastGrowthTime;
    private TimeSystem lastHarvestTime;
    private TimeSystem lastWaterTime;
    private final Fertilizer fertilizer;
    private final int numberOfDaysCanBeAliveWithoutWater;
    private final Rectangle bounds;



    public Crop(CropType type, TimeSystem timeOfPlanting, Fertilizer fertilizer , int x ,  int y) {
        this.type = type;
        this.lastGrowthTime = timeOfPlanting.clone();
        this.lastWaterTime = timeOfPlanting.clone();
        this.fertilizer = fertilizer;
        if (fertilizer == null) {
            levelOfGrowth = 0;
            numberOfDaysCanBeAliveWithoutWater = 2;
        }
        else {
            if (fertilizer.equals(Fertilizer.WaterFertilizer)) {
                levelOfGrowth = 0;
                numberOfDaysCanBeAliveWithoutWater = 3;
            } else {
                levelOfGrowth = 1;
                numberOfDaysCanBeAliveWithoutWater = 2;
            }
        }
        this.bounds = new Rectangle(x, y,1, 1);
    }

    public int calculatePrice() {
        return 0;
    }

    public CropType getType() {
        return type;
    }

    public void grow(TimeSystem today) {
        if (isComplete())
            return;

        int timeForGrow = type.getTimeForGrow(levelOfGrowth);

        if (lastGrowthTime.getDate() + timeForGrow == today.getDate()) {
            levelOfGrowth++;
            lastGrowthTime = today;
        }

    }

    public boolean canGrowAgain() {
        return !type.isOneTime();
    }

    public boolean harvest() {
        if (!isComplete() || type.isOneTime())
            return false;

        TimeSystem today = App.getGameState().getGameTime().clone();
        int timeForGrow = type.getRegrowthTime();

        if (lastHarvestTime == null || lastHarvestTime.getDate() + timeForGrow <= today.getDate()) {
            lastHarvestTime = today;
            return true;
        }
        return false;
    }

    public boolean isComplete() {
        return levelOfGrowth >= type.getNumberOfStages();
    }

    public void watering() {
        lastWaterTime = App.getGameState().getGameTime().clone();
    }

    public boolean canBeAlive(TimeSystem today) {
        return today.getDate() <= lastWaterTime.getDate() + numberOfDaysCanBeAliveWithoutWater;
    }

    public int getNumberOfDaysToComplete() {
        int passedDays = 0;
        for (int i = 0; i < levelOfGrowth; i++) {
            passedDays += type.getTimeForGrow(i);
        }
        passedDays += App.getGameState().getGameTime().getDate() - lastGrowthTime.getDate();
        return type.getTotalHarvestTime() - passedDays;
    }

    public int getCurrentStage() {
        return levelOfGrowth;
    }

    public boolean hasWateredToday() {
        return App.getGameState().getGameTime().getDate() == lastWaterTime.getDate();
    }

    public boolean hasFertilized() {
        return fertilizer != null;
    }

    public Fertilizer getFertilizer() {
        return fertilizer;
    }

    public String getNameOfProduct() {
        return type.getName();
    }

    public String getName() {
        return type.name();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public char getSymbol() {
        return '*';
    }

    public int getSellPrice() {
        return type.getBaseSellPrice();
    }
}