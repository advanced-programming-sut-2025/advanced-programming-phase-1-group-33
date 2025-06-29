package com.yourgame.model.Animals;

public enum HabitatSize {
    Regular(4),
    Big(8),
    Deluxe(12);

    private final int capacity;

    HabitatSize(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
