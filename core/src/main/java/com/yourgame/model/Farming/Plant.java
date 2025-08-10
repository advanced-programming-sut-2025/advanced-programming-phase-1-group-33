package com.yourgame.model.Farming;

import com.yourgame.model.Map.MapElement;
import com.yourgame.model.WeatherAndTime.TimeObserver;

import java.awt.*;

public abstract class Plant extends MapElement implements TimeObserver {
    protected final Fertilizer fertilizer;
    protected boolean wateredToday;
    protected int currentStage;
    protected int daysSinceLastStage;
    protected int daysSinceLastHarvest;

    public Plant(ElementType type, Rectangle pixelBounds, float health, Fertilizer fertilizer) {
        super(type, pixelBounds, health);
        this.fertilizer = fertilizer;
    }

    public abstract boolean isMature();

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
}
