package com.yourgame.model.enums.ItemTypes;

import com.yourgame.model.Inventory.Slot;

public interface Item {
    public interface ItemType {
        Slot createAmountOfItem(int amount);

        String getName();

        String name();
    }
}
