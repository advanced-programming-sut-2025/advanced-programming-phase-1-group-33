package com.yourgame.model.Animals.AnimalPackage;

import com.yourgame.model.Item.ArtisanIngredient;

public enum AnimalProductType implements ArtisanIngredient {
    Egg("Egg", 50, 25),
    LargeEgg("LargeEgg", 95, 0),
    DuckEgg("DuckEgg", 95, 0),
    DuckFeather("DuckFeather", 250, 0),
    Wool("Wool", 340, 0),
    RabbitFoot("RabbitFoot", 565, 0),
    DinosaurEgg("DinosaurEgg", 350, 0),
    Milk("Milk", 125, 0),
    LargeMilk("LargeMilk", 190, 0),
    GoatMilk("GoatMilk", 225, 0),
    LargeGoatMilk("LargeGoatMilk", 345, 0),
    Truffle("Truffle", 625, 0);

    private final String name;
    private final int price;
    private final int energy;

    AnimalProductType(String name, int price, int energy) {
        this.name = name;
        this.price = price;
        this.energy = energy;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }


    @Override
    public int getBaseEnergy() {
        return energy;
    }

    @Override
    public int getBasePrice() {
        return price;
    }
}
