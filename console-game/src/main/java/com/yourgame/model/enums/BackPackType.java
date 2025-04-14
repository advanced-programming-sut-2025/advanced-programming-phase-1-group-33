package com.yourgame.model.enums;

public enum BackPackType {
    DEFAULT(12), 
    BIG_BACKPACK(24),
    DELOUX(10000000);

    private final int maxCapacity;

    BackPackType(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}