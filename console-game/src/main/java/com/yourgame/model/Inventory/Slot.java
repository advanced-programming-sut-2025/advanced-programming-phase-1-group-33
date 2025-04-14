package com.yourgame.model.Inventory;

import com.yourgame.model.Item.Item;

public class Slot {
    private final Item item;
    private int count;

    public Slot(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}
