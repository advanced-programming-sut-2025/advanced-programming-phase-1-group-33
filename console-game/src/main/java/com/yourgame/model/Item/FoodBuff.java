package com.yourgame.model.Item;

public class FoodBuff {
    private int increment;
    private int duration;
    private String affectedField;

    public FoodBuff() {}

    public FoodBuff(String affectedField, int increment, int duration) {
        this.affectedField = affectedField;
        this.increment = increment;
        this.duration = duration;
    }

    public int getIncrement() {
        return increment;
    }

    public String getAffectedField() {
        return affectedField;
    }

    public int getDuration() {
        return duration;
    }
}
