package com.yourgame.model.Animals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public enum AnimalType {
    Chicken(HabitatType.Coop , HabitatSize.Regular, 800, 1,
            new ArrayList<>(Arrays.asList(AnimalGoodType.Egg, AnimalGoodType.LargeEgg))),
    Duck(HabitatType.Coop , HabitatSize.Big, 1200, 2,
            new ArrayList<>(Arrays.asList(AnimalGoodType.DuckEgg, AnimalGoodType.DuckFeather))),
    Rabbit(HabitatType.Coop , HabitatSize.Deluxe, 8000, 4,
            new ArrayList<>(Arrays.asList(AnimalGoodType.Wool, AnimalGoodType.RabbitFoot))),
    Dinosaur(HabitatType.Coop , HabitatSize.Big, 14000, 7,
            new ArrayList<>(List.of(AnimalGoodType.DinosaurEgg))),
    Cow(HabitatType.Barn , HabitatSize.Regular, 1500, 1,
            new ArrayList<>(Arrays.asList(AnimalGoodType.Milk, AnimalGoodType.LargeMilk))),
    Goat(HabitatType.Barn , HabitatSize.Big, 4000, 2,
            new ArrayList<>(Arrays.asList(AnimalGoodType.GoatMilk, AnimalGoodType.LargeGoatMilk))),
    Sheep(HabitatType.Barn , HabitatSize.Deluxe, 8000, 3,
            new ArrayList<>(List.of(AnimalGoodType.Wool))),
    Pig(HabitatType.Barn , HabitatSize.Deluxe, 16000, 0,
            new ArrayList<>(List.of(AnimalGoodType.Truffle)));


    private final HabitatType habitatType;
    private final HabitatSize habitatSize;
    private final int Price;
    private final int daysToGetProduct;
    private final static HashMap<String, AnimalType> stringToAnimalType = new HashMap<>();
    private final ArrayList<AnimalGoodType> animalGoodTypes;

    static {
        for (AnimalType value : AnimalType.values()) {
            stringToAnimalType.put(value.name().toLowerCase(), value);
        }
    }

     AnimalType(HabitatType habitatType, HabitatSize habitatSize, int price, int daysToGetProduct, ArrayList<AnimalGoodType> animalGoodTypes){
        this.habitatType = habitatType;
         this.habitatSize = habitatSize;
         this.Price = price;
         this.daysToGetProduct = daysToGetProduct;
         this.animalGoodTypes = animalGoodTypes;
     }

    public int getPrice() {
        return Price;
    }

    public HabitatType getAnimalHabitat() {
        return habitatType;
    }

    public static AnimalType getAnimalTypeByInput(String input) {
        return stringToAnimalType.getOrDefault(input, null);
    }

    public int getDaysToGetProduct() {
         return daysToGetProduct;
    }

    public ArrayList<AnimalGoodType> getAnimalGoods() {
         return animalGoodTypes;
    }
}
