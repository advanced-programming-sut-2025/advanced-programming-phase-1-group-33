package com.yourgame.model.ManuFactor.Artisan;

import com.yourgame.model.ManuFactor.ArtisanGoodType;

public enum ArtisanProductType {
    GoldBar("Gold Bar", 0, 450),
    Honey("Honey", 75, 350),
    CheeseByMilk("Cheese", 100, 230),
    CheeseByLargeMilk("LargeCheese", 100, 345),
    GoatCheeseByMilk("GoatCheese", 100, 400),
    GoatCheeseByLargeMilk("LargeGoatCheese", 100, 600),
    Beer("Beer", 50, 200),
    Vinegar("Vinegar", 13, 100),
    Coffee("Coffee", 75, 150),
    Mead("Mead", 100, 300),
    PaleAle("PaleAle", 50, 300),
    Raisins("Raisins", 125, 600),
    Coal("Coal", 0, 50),
    Cloth("Cloth", 0, 470),
    Mayonnaise("Mayonnaise", 50, 190),
    DuckMayonnaise("DuckMayonnaise", 75, 375),
    DinosaurMayonnaise("DinosaurMayonnaise", 125, 800),
    TruffleOil("TruffleOil", 38, 1065),
    Oil("Oil", 13, 100),
    DriedMushroom("DriedMushrooms", 50, 100),
    DriedFruit("DriedFruit", 45, 150),
    Jelly("Jelly", 23, 85),
    Juice("Juice", 65, 220),
    Pickles("Pickles", 43, 120),
    SmokedFish("SmokedFish", 95, 195),
    Wine("Wine", 10, 120),
    IronBar("IronBar", 0, 200),
    IridiumBar("IridiumBar", 0, 540),
    CopperBar("CopperBar", 0, 295);

    private final String name;
    private final int energy;
    private final int sellPrice;

    ArtisanProductType(String name, int energy, int sellPrice) {
        this.name = name;
        this.energy = energy;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getEnergy() {
        return energy;
    }
}

