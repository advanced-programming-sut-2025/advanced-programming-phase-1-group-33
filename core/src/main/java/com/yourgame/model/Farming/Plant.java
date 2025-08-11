package com.yourgame.model.Farming;

import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.MapElement;
import com.yourgame.model.WeatherAndTime.TimeObserver;

import java.awt.*;
import java.util.List;

public abstract class Plant extends MapElement implements TimeObserver {
    protected final Fertilizer fertilizer;
    protected boolean wateredToday;
    protected int currentStage;
    protected int daysSinceLastStage;
    protected int daysSinceLastHarvest;
    protected int daysSinceWatered;
    protected boolean hasProduct;
    protected final int maxDryDays;

    public Plant(ElementType type, Rectangle pixelBounds, float health, Fertilizer fertilizer) {
        super(type, pixelBounds, health);
        this.fertilizer = fertilizer;
        if (fertilizer == Fertilizer.Growth_Fertilizer) {
            currentStage = 1;
            maxDryDays = 2;
        } else {
            currentStage = 0;
            maxDryDays = 3;
        }
    }

    public abstract boolean isMature();
    public abstract List<Item> harvest();

    public Fertilizer getFertilizer() {
        return fertilizer;
    }

    public void setWateredToday(boolean wateredToday) {
        this.wateredToday = wateredToday;
    }

    public boolean isWateredToday() {
        return wateredToday;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public boolean hasProduct() {
        return hasProduct;
    }

    public void setHasProduct(boolean hasProduct) {
        this.hasProduct = hasProduct;
    }
}
