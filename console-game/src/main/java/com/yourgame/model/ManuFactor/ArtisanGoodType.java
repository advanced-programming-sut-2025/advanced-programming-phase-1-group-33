package com.yourgame.model.ManuFactor;

public enum ArtisanGoodType implements Ingredient {
    GoldBar(0, 450),
    Honey(75, 350),
    CheeseByMilk(100, 230),
    CheeseByLargeMilk(100, 345),
    GoatCheeseByMilk(100, 400),
    GoatCheeseByLargeMilk(100, 600),
    Beer(50, 200),
    Vinegar(13, 100),
    Coffee(75, 150),
    Mead(100, 300),
    PaleAle(50, 300),
    Raisins(125, 600),
    Coal(0, 50),
    Cloth(0, 470),
    Mayonnaise(50, 190),
    DuckMayonnaise(75, 37),
    DinosaurMayonnaise(125, 800),
    TruffleOil(38, 1065),
    Oil(13, 100),
    DriedMushroom(50, 100),
    DriedFruit(45, 150),
    Jelly(23, 85),
    Juice(65, 220),
    Pickles(43, 120),
    SmokedFish(95, 195),
    Wine(10, 120),
    IronBar(0, 200),
    IridiumBar(0, 540),
    CopperBar(0, 295);

    private int sellPrice;
    private int energy;

    ArtisanGoodType(int energy, int sellPrice) {
        this.sellPrice = sellPrice;
        this.energy = energy;
    }

    
    public int getSellPrice() {
        return sellPrice;
    }
    public int getEnergy() {
        return energy;
    }

    /**
     * Looks up an ArtisanGoodType by name (ignoring case).
     * 
     * @param name the name of the good to find.
     * @return the matching ArtisanGoodType, or null if not found.
     */
    public static ArtisanGoodType getByName(String name) {
        for (ArtisanGoodType type : ArtisanGoodType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
