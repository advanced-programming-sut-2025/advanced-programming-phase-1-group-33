package com.yourgame.model.Item.Inventory;

public enum BackpackType {
    Primary(12),
    Big(24),
    Deluxe(36);

    public final int capacity;

    BackpackType(int capacity) {
        this.capacity = capacity;
    }
}
