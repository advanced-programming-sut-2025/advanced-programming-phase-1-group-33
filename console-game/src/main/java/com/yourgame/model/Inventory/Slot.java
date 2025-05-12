package com.yourgame.model.Inventory;

import com.yourgame.model.Item.Food;
import com.yourgame.model.enums.ItemTypes.FoodTypes;
import com.yourgame.model.Item.Item;
import com.yourgame.model.enums.ItemType;
import com.yourgame.model.enums.Quality;

public class Slot {
    private Item item;
    private int count;

    public Slot() {
    }

    public Slot(ItemType type, int count) {
        Item item = null;
        this.item = item;
        this.count = count;
    }

    public Slot(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Slot [item=" + item.getName() + ", count=" + count + "]";
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
