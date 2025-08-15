package com.yourgame.model.Resource;

import com.yourgame.model.Item.ArtisanIngredient;

public enum ResourceType implements ArtisanIngredient {
    Stone("Stone",2,0),
    Fiber("Fiber",1,0),
    Wood("Wood",2,0);

    private final String name;
    private final int value;
    private final int energy;

    ResourceType(String name, int value,  int energy) {
        this.name = name;
        this.value = value;
        this.energy = energy;
    }

    public String getName() {
        return name;
    }


    @Override
    public int getBaseEnergy() {
        return energy;
    }

    @Override
    public int getBasePrice() {
        return value;
    }
}
