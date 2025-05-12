package com.yourgame.model.enums;

import java.util.ArrayList;

import com.yourgame.model.Item.Item;

public enum AnimalType {
    HEN(1,4),
    DUCK(2,8),
    RABBIT(4,12),
    DINOSAUR(7,8),
    COW(1,4),
    GOAT(2,8),
    SHEEP(3,12);

    public final int productPerDay;
    public final int capacity;
    public final ArrayList<Item> products = new ArrayList<>();
    AnimalType(int productPerDay , int capacity) {
        this.productPerDay = productPerDay;
        this.capacity = capacity;
    }
}
