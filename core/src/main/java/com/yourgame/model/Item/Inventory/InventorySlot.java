package com.yourgame.model.Item.Inventory;

import com.yourgame.model.Item.Item;

public class InventorySlot {
    private final Item item;
    private int quantity;

    public InventorySlot(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item item() {
        return item;
    }

    public int quantity() {
        return quantity;
    }

    public void reduceQuantity(int quantity) {
        this.quantity = Math.max(this.quantity - quantity, 0);
    }

    public void increaseQuantity(int quantity) {
        this.quantity += Math.abs(quantity);
    }

    public boolean isStackableWith(Item other) {
        return item.isStackable() && item.equals(other);
    }
}
