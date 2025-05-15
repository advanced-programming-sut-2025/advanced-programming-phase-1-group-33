package com.yourgame.model.Stores;

import com.yourgame.model.Animals.AnimalType;
import com.yourgame.model.Animals.HabitatSize;
import com.yourgame.model.Animals.HabitatType;

public class MarnieRanchLiveStockItem extends ShopItem {

    private final AnimalType animalType;
    private final HabitatType habitatTypeRequired;
    private final HabitatSize habitatSizeRequired;

    public MarnieRanchLiveStockItem(String name, AnimalType animalType, HabitatType habitatTypeRequired, HabitatSize habitatSizeRequired, int price, int dailyLimit) {

        super(name, price, dailyLimit);
        this.animalType = animalType;
        this.habitatTypeRequired = habitatTypeRequired;
        this.habitatSizeRequired = habitatSizeRequired;

    }

    public AnimalType getType() {
        return animalType;
    }

    public HabitatType getHabitatTypeRequired() {
        return habitatTypeRequired;
    }

    public HabitatSize getHabitatSizeRequired() {
        return habitatSizeRequired;
    }

}
