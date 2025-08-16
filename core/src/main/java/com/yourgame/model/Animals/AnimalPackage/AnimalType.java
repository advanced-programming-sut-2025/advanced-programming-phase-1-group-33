package com.yourgame.model.Animals.AnimalPackage;

import com.badlogic.gdx.graphics.Texture;
import com.yourgame.model.Map.Elements.BuildingType;

public enum AnimalType {
    // Coop Animals
    CHICKEN("Chicken", BuildingType.COOP, 800, AnimalProductType.Egg, AnimalProductType.LargeEgg),
    DUCK("Duck", BuildingType.COOP, 1200, AnimalProductType.DuckEgg, AnimalProductType.DuckFeather),
    RABBIT("Rabbit", BuildingType.COOP, 8000, AnimalProductType.Wool, AnimalProductType.RabbitFoot),
    DINOSAUR("Dinosaur", BuildingType.COOP, 10000, AnimalProductType.DinosaurEgg, null), // Only one product type

    // Barn Animals
    COW("Cow", BuildingType.BARN, 1500, AnimalProductType.Milk, AnimalProductType.LargeMilk),
    GOAT("Goat", BuildingType.BARN, 4000, AnimalProductType.GoatMilk, AnimalProductType.LargeGoatMilk),
    SHEEP("Sheep", BuildingType.BARN, 8000, AnimalProductType.Wool, null),
    PIG("Pig", BuildingType.BARN, 16000, AnimalProductType.Truffle, null);

    private final String name;
    private final BuildingType home;
    private final int cost;
    private final AnimalProductType regularProduct;
    private final AnimalProductType deluxeProduct; // Can be null if there's only one product type

    AnimalType(String name, BuildingType home, int cost, AnimalProductType regularProduct, AnimalProductType deluxeProduct) {
        this.name = name;
        this.home = home;
        this.cost = cost;
        this.regularProduct = regularProduct;
        this.deluxeProduct = deluxeProduct;
    }

    public String getName() { return name; }
    public BuildingType getHome() { return home; }
    public int getCost() { return cost; }
    public AnimalProductType getRegularProduct() { return regularProduct; }
    public AnimalProductType getDeluxeProduct() { return deluxeProduct; }
    public Texture getTexture() { return new Texture("Game/Animal/" + name + ".png"); }
}
