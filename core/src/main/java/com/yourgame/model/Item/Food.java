package com.yourgame.model.Item;


import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Stores.Sellable;

import java.util.HashMap;

public enum Food implements Ingredient, Sellable {
    FriedEgg(50, 35),
    BakedFish(75, 100),
    Salad(113, 110),
    Omelet(100, 125),
    PumpkinPie(225, 385),
    Spaghetti(75, 120),
    Pizza(150, 300),
    Tortilla(50, 50),
    MakiRoll(100, 220),
    TripleShotEspresso(200, 450),
    Cookie(90, 140),
    HashBrowns(90, 120),
    Pancakes(90, 80),
    FruitSalad(263, 450),
    RedPlate(240, 400),
    Bread(50, 60),
    SalmonDinner(125, 300),
    VegetableMedley(165, 120),
    FarmersLunch(200, 150),
    SurvivalBurger(125, 180),
    DishOTheSea(150, 220),
    SeaFormPudding(175, 300),
    JojaCola(13,25),
    TroutSoup(100,100),
    MinersTreat(125, 200);

    private final int energy;
    private final int sellPrice;
    private static final HashMap<String, Food> stringToFood = new HashMap<>();

    static {
        for (Food values : values()) {
            stringToFood.put(values.name().toLowerCase(), values);
        }
    }

    Food(int energy, int sellPrice) {
        this.energy = energy;
        this.sellPrice = sellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public static Food getFoodByName(String name) {
        return stringToFood.getOrDefault(name.toLowerCase(), null);
    }
}
