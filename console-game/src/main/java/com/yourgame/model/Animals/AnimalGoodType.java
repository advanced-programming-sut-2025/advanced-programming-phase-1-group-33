package com.yourgame.model.Animals;


import com.yourgame.model.ManuFactor.Ingredient;

public enum AnimalGoodType implements Ingredient {
    Egg(50),
    LargeEgg(95),
    DuckEgg(95),
    DuckFeather(250),
    Wool(340),
    RabbitFoot(565),
    DinosaurEgg(350),
    Milk(125),
    LargeMilk(190),
    GoatMilk(225),
    LargeGoatMilk(345),
    Truffle(625);

    private final int price;

    AnimalGoodType(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
