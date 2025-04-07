package com.yourgame.model;

import com.yourgame.model.enums.ItemType;

public class Food extends Item {
    private int energyRestored;
    private Buff buff;
    
    public Food(String id, String name, String description, int sellPrice, boolean isStackable,
                int energyRestored, Buff buff) {
        super(id, name, description, ItemType.FOOD, sellPrice);
        this.energyRestored = energyRestored;
        this.buff = buff;
    }
    
    public int getEnergyRestored() {
        return energyRestored;
    }
    
    public Buff getBuff() {
        return buff;
    }
    
    @Override
    public void use() {
        System.out.println("Consuming food: " + getName());
        // Implement energy restoration and buff application logic here.
    }
}