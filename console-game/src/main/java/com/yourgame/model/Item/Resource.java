package com.yourgame.model.Item;

import com.yourgame.model.enums.ItemType;

public class Resource extends Item{

    public Resource(String id, String name, String description, int sellPrice, boolean isStackable) {
        super(id, name, description, ItemType.RESOURCE, sellPrice);
    }

    @Override
    public void use() {
        System.out.println("Using resource: " + getName());
        // Implement resource-specific logic if needed.
    }
}
