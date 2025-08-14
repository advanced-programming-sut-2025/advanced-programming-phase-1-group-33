package com.yourgame.model.ManuFactor.Artisan;

import com.yourgame.model.Animals.AnimalPackage.AnimalProduct;
import com.yourgame.model.Animals.AnimalPackage.AnimalProductType;
import com.yourgame.model.Farming.CropItem;
import com.yourgame.model.Farming.CropType;
import com.yourgame.model.ManuFactor.ArtisanGoodType;

import java.util.ArrayList;
import java.util.Arrays;

public enum ArtisanProductType {
    Honey("Honey", 75, 350, 4),
    CheeseByMilk("Cheese", 100, 230 , 3),
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
    Mayonnaise("Mayonnaise", 50, 0, 3),
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

    private static ArrayList<CropType> fruits = new ArrayList<>(Arrays.asList(
            CropType.AncientFruit,
            CropType.Blueberry,
            CropType.Cranberry,
            CropType.Grape,
            CropType.Hot_Pepper,
            CropType.Melon,
            CropType.PowderMelon,
            CropType.Rhubarb,
            CropType.Starfruit,
            CropType.Strawberry
    ));

    ArrayList<CropType> vegetables = new ArrayList<>(Arrays.asList(
            CropType.Amaranth,
            CropType.Artichoke,
            CropType.Beet,
            CropType.Bok_Choy,
            CropType.Broccoli,
            CropType.Carrot,
            CropType.Cauliflower,
            CropType.Corn,
            CropType.Eggplant,
            CropType.Garlic,
            CropType.Green_Bean,
            CropType.Hops,
            CropType.Kale,
            CropType.Parsnip,
            CropType.Potato,
            CropType.Pumpkin,
            CropType.Radish,
            CropType.Red_Cabbage,
            CropType.Summer_Squash,
            CropType.Tomato,
            CropType.Unmilled_Rice,
            CropType.Wheat,
            CropType.Yam
    ));

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

    public int getEnergy() {
        return energy;
    }

    public int getProcessingHour() {
        return processingHour;
    }

    public int calculateJuiceEnergy(CropItem item) {
        if(vegetables.contains(item.getCropType())) {
            return item.getCropType().getEnergy() * 2;
        }
        return -1;
    }

    public int calculateJuicePrice(CropItem item) {
        if(vegetables.contains(item.getCropType())) {
            return (int)(item.getValue() * 1.75f);
        }
        return -1;
    }

    public int calculateWineEnergy(CropItem item) {
        if(fruits.contains(item.getCropType())) {
            return (int)(item.getCropType().getEnergy() * 1.75f);
        }
        return -1;
    }

    public int calculateWinePrice(CropItem item) {
        if(fruits.contains(item.getCropType())) {
            return item.getValue() * 3;
        }
        return -1;
    }

    public int calculateDriedFruitPrice(CropItem item) {
        if(fruits.contains(item.getCropType()) && item.getCropType() != CropType.Grape) {
            return (int)(item.getCropType().getBaseSellPrice() * 7.5f + 20);
        }
        return -1;
    }

    public int calculateMayonnaisePrice(AnimalProduct item) {
        if(item.getAnimalProductType().equals(AnimalProductType.Egg))
            return 190;
        else if(item.getAnimalProductType().equals(AnimalProductType.LargeEgg))
            return 237;
        return -1;
    }
}

