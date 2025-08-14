package com.yourgame.model.Crafting;

public enum CraftedItemType {
    CherryBomb("CherryBomb", 50),
    Bomb("Bomb", 50),
    MegaBomb("MegaBomb", 50),
    Sprinkler("Sprinkler", 0),
    QualitySprinkler("QualitySprinkler", 0),
    IridiumSprinkler("IridiumSprinkler", 0),
    CharcoalKiln("CharcoalKiln", 0),
    Furnace("Furnace", 0),
    Scarecrow("Scarecrow", 0),
    DeluxeScarecrow("DeluxeScarecrow", 0),
    BeeHouse("BeeHouse", 0),
    CheesePress("CheesePress", 0),
    Keg("Keg", 0),
    Loom("Loom", 0),
    MayonnaiseMachine("MayonnaiseMachine", 0),
    OilMaker("OilMaker", 0),
    PreservesJar("PreservesJar", 0),
    Dehydrator("Dehydrator", 0),
    GrassStarter("GrassStarter", 0),
    FishSmoker("FishSmoker", 0),
    MysticTreeSeed("MysticTreeSeed", 100);

    private final String name;
    private final int price;

    CraftedItemType(String name, int price) {
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
