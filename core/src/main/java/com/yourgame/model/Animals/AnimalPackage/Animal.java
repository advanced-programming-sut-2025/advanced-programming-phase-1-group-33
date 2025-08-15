package com.yourgame.model.Animals.AnimalPackage;

import com.yourgame.model.WeatherAndTime.TimeObserver;
import com.yourgame.model.WeatherAndTime.TimeSystem;

public class Animal implements TimeObserver {
    private final AnimalType type;
    private String name; // Can be set by the player
    private int friendship;
    private boolean wasFed;
    private boolean wasPet;
    private int daysUntilProductReady;

    public Animal(AnimalType type) {
        this.type = type;
        this.name = type.getName(); // Default name
        this.friendship = 0;
        this.wasFed = false;
        this.wasPet = false;
        this.daysUntilProductReady = type.getDaysToProduce();
    }

    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        if (wasPet) {
            friendship += 15;
        }
        if (wasFed) {
            daysUntilProductReady--;
        } else {
            friendship -= 20;
        }

        wasPet = false;
        wasFed = false;

        friendship = Math.max(0, Math.min(1000, friendship));
    }

    public boolean canProduceProduct() {
        return daysUntilProductReady <= 0;
    }

    public void collectProduct() {
        if (canProduceProduct()) {
            daysUntilProductReady = type.getDaysToProduce();
        }
    }

    public AnimalType getType() { return type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public void pet() { this.wasPet = true; }
}
