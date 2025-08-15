package com.yourgame.model.Animals.AnimalPackage;

import com.badlogic.gdx.graphics.Texture;
import com.yourgame.model.Map.Elements.BuildingType;

public enum AnimalType {
    // Coop Animals
    CHICKEN("Chicken", BuildingType.COOP, 800, AnimalProductType.Egg, 1),
    DUCK("Duck", BuildingType.COOP, 1200, AnimalProductType.DuckEgg, 2),
    RABBIT("Rabbit", BuildingType.COOP, 8000, AnimalProductType.Wool, 4),
    DINOSAUR("Dinosaur", BuildingType.COOP, 10000, AnimalProductType.DinosaurEgg, 7),

    // Barn Animals
    COW("Cow", BuildingType.BARN, 1500, AnimalProductType.Milk, 1),
    GOAT("Goat", BuildingType.BARN, 4000, AnimalProductType.GoatMilk, 2),
    SHEEP("Sheep", BuildingType.BARN, 8000, AnimalProductType.Wool, 3),
    PIG("Pig", BuildingType.BARN, 16000, AnimalProductType.Truffle, 1);

    private final String name;
    private final BuildingType home;
    private final int cost;
    private final AnimalProductType product;
    private final int daysToProduce;

    AnimalType(String name, BuildingType home, int cost, AnimalProductType product, int daysToProduce) {
        this.name = name;
        this.home = home;
        this.cost = cost;
        this.product = product;
        this.daysToProduce = daysToProduce;
    }

    public String getName() { return name; }
    public BuildingType getHome() { return home; }
    public int getCost() { return cost; }
    public AnimalProductType getProduct() { return product; }
    public int getDaysToProduce() { return daysToProduce; }
    public Texture getTexture() { return new Texture("Game/Animal/" + name + ".png"); }
}
