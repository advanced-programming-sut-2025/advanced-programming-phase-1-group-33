package com.yourgame.model.Item;

import com.yourgame.model.enums.ItemType;
import com.yourgame.model.enums.ResourceType;

public class Resource extends Item{

    ResourceType type;

    public Resource(String id, String name, String description, int sellPrice, boolean isStackable) {
        super(id, name, description, ItemType.RESOURCE, sellPrice);
        this.type = ResourceType.Metal;
    }

    @Override
    public void use() {
        System.out.println("Using resource: " + getName());
        // Implement resource-specific logic if needed.
    }
}
