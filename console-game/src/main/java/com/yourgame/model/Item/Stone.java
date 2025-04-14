package com.yourgame.model.Item;

import com.yourgame.model.enums.ItemType;
import com.yourgame.model.enums.StoneType;

public class Stone extends Item{
    private StoneType type;
    public Stone(String id, String name, String description, ItemType type, int value) {
        super(id, name, description, type, value);
        this.type = StoneType.Stone;
    }
}
