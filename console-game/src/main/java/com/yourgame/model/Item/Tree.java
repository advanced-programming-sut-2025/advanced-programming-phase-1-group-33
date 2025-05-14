package com.yourgame.model.Item;

import com.yourgame.model.App;
import com.yourgame.model.Placeable;
import com.yourgame.model.WeatherAndTime.TimeSystem;


import java.awt.*;

public class Tree implements Growable, Placeable {
    private final TreeType type;
    private int levelOfGrowth;
    private TimeSystem lastGrowthTime;
    private TimeSystem lastHarvestTime;
    private TimeSystem lastWaterTime;
    private final Fertilizer fertilizer;
    private final int numberOfDaysCanBeAliveWithoutWater;
    private Rectangle bounds;

    public Tree(TreeType type, TimeSystem timeOfPlanting, Fertilizer fertilizer, int x, int y, int width, int height){
        this.type = type;
        this.lastGrowthTime = timeOfPlanting.clone();
        this.lastWaterTime = timeOfPlanting.clone();
        this.fertilizer = fertilizer;
        if (fertilizer == null) {
            this.levelOfGrowth = 0;
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
        this.bounds = new Rectangle(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public char getSymbol() {
        return 'T';
    }

    public TreeType getType() {
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
        return true;
    }

    public boolean isComplete() {
        return levelOfGrowth >= type.getStages().size();
    }

    public boolean harvest() {
        if (!isComplete())
            return false;

        TimeSystem today = App.getGameState().getGameTime().clone();
        int timeForGrow = type.getHarvestCycle();

        if (lastHarvestTime == null || lastHarvestTime.getDate() + timeForGrow <= today.getDate()) {
            lastHarvestTime = today;
            return true;
        }
        return false;
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
        return type.getFruit().name();
    }

    public String getName() {
        return type.name();
    }

}
