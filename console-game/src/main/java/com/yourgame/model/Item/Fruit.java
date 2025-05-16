package com.yourgame.model.Item;

import com.yourgame.model.ManuFactor.ArtisanGoodType;
import com.yourgame.model.ManuFactor.Ingredient;
import com.yourgame.model.Stores.Sellable;

public enum Fruit implements Ingredient , Sellable {
    Apricot(75, 100),
    Cherry(75, 100),
    Banana(75, 100),
    Mango(75, 100),
    Orange(75, 100),
    Peach(75, 100),
    Apple(75, 100),
    Pomegranate(75, 100),
    OakResin(75, 100),
    MapleSyrup(75, 100),
    PineTar(75, 100),
    Sap(75, 100),
    CommonMushroom(75, 100),
    MysticSyrup(75, 100);

    private final int energy;
    private final int baseSellPrice;


    Fruit(int energy, int baseSellPrice) {
        this.energy = energy;
        this.baseSellPrice = baseSellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    public int getSellPrice() {
        return baseSellPrice;
    }

    public static Fruit getByName(String name) {
        for (Fruit type : Fruit.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

}
