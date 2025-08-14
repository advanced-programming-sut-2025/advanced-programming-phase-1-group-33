package com.yourgame.model.ManuFactor.Artisan;

public enum ArtisanProductType {
    Honey("Honey", 75, 350, 96),
    CheeseByMilk("Cheese", 100, 230, 3),
    CheeseByLargeMilk("LargeCheese", 100, 345, 3),
    GoatCheeseByMilk("GoatCheese", 100, 400, 3),
    GoatCheeseByLargeMilk("LargeGoatCheese", 100, 600, 3),
    Beer("Beer", 50, 200, 24),
    Vinegar("Vinegar", 13, 100, 10),
    Coffee("Coffee", 75, 150, 2),
    Mead("Mead", 100, 300, 10),
    PaleAle("PaleAle", 50, 300, 72),
    Raisins("Raisins", 125, 600, 0),
    Coal("Coal", 0, 50, 1),
    Cloth("Cloth", 0, 470, 4),
    Mayonnaise("Mayonnaise", 50, 190, 3),
    DuckMayonnaise("DuckMayonnaise", 75, 375, 3),
    DinosaurMayonnaise("DinosaurMayonnaise", 125, 800, 3),
    TruffleOil("TruffleOil", 38, 1065, 6),
    Oil("Oil", 13, 100, 0),
    DriedMushroom("DriedMushrooms", 50, 100, 0),
    DriedFruit("DriedFruit", 45, 150, 0),
    Jelly("Jelly", 23, 85, 72),
    Juice("Juice", 65, 220, 96),
    Pickles("Pickles", 43, 120, 6),
    SmokedFish("SmokedFish", 95, 195, 1),
    Wine("Wine", 10, 120, 168);

    private final String name;
    private final int energy;
    private final int sellPrice;
    private final int processingHour;

    ArtisanProductType(String name, int energy, int sellPrice, int processingHour) {
        this.name = name;
        this.energy = energy;
        this.sellPrice = sellPrice;
        this.processingHour = processingHour;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    public int getProcessingHour() {
        return processingHour;
    }
}
