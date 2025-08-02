package com.yourgame.model.Item;

import com.yourgame.model.WeatherAndTime.TimeSystem;

public interface Growable {
    void grow(TimeSystem today);
    boolean canGrowAgain();
    boolean isComplete();
    boolean harvest();
    void watering();
    boolean canBeAlive(TimeSystem today);
    int getNumberOfDaysToComplete();
    int getCurrentStage();
    boolean hasWateredToday();
    boolean hasFertilized();
    Fertilizer getFertilizer();
    String getName();
    String getNameOfProduct();
}
