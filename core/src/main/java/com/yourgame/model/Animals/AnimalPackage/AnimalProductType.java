package com.yourgame.model.Animals.AnimalPackage;

import com.yourgame.model.ManuFactor.Ingredient;

public enum AnimalProductType implements Ingredient {
    Egg("Egg", 50),
    LargeEgg("LargeEgg", 95),
    DuckEgg("DuckEgg", 95),
    DuckFeather("DuckFeather", 250),
    Wool("Wool", 340),
    RabbitFoot("RabbitFoot", 565),
    DinosaurEgg("DinosaurEgg", 350),
    Milk("Milk", 125),
    LargeMilk("LargeMilk", 190),
    GoatMilk("GoatMilk", 225),
    LargeGoatMilk("LargeGoatMilk", 345),
    Truffle("Truffle", 625);

    private final String name;
    private final int price;

    AnimalProductType(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
