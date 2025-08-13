package com.yourgame.model.Item.Inventory;

import com.yourgame.model.Item.Item;

import java.util.ArrayList;
import java.util.Iterator;

public class Inventory {
    private final ArrayList<InventorySlot> slots;
    private Item selectedItem;
    private int capacity;
    private boolean isCapacityUnlimited;
    private int itemCount;

    public Inventory(int initialCapacity) {
        selectedItem = null;
        this.capacity = initialCapacity;
        this.slots = new ArrayList<>();
        this.isCapacityUnlimited = false;
        this.itemCount = 0;
    }

    public void selectItem(Item item) {
        for (InventorySlot slot : slots) {
            if (slot.item().equals(item)) selectedItem = slot.item();
        }
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public int getItemQuantity(Item item) {
        for (InventorySlot slot : slots) {
            if (slot.item().equals(item)) return slot.quantity();
        }
        return 0;
    }

    public boolean addItem(Item item, int quantity) {
        if (item.isStackable()) {
            for (InventorySlot slot : slots) {
                if (slot.isStackableWith(item)) {
                    slot.increaseQuantity(quantity);
                    return true;
                }
            }
        }

        if (isInventoryFull()) return false;

        slots.add(new InventorySlot(item, quantity));
        itemCount++;
        return true;
    }

    /**
     * @return the amount of the specific item that has been removed
     */
    public int reduceItemQuantity(Item item, int quantity) {
        for (InventorySlot slot : slots) {
            if (slot.item().equals(item)) {
                int beforeChangeQuantity = slot.quantity();
                slot.reduceQuantity(quantity);
                if (slot.quantity() == 0) removeItem(item);
                return Math.min(quantity, beforeChangeQuantity);
            }
        }
        return 0;
    }

    /**
     * @return the amount of the specific item that has been removed
     */
    public int removeItem(Item item) {
        if (item.getItemType() == Item.ItemType.TOOL) return 0;

        Iterator<InventorySlot> iterator = slots.iterator();
        while (iterator.hasNext()) {
            InventorySlot slot = iterator.next();
            if (slot.item().equals(item) && item.isStackable()) {
                int quantity = slot.quantity();
                itemCount--;
                iterator.remove();
                return quantity;
            }
        }
        return 0;
    }

    public ArrayList<InventorySlot> getSlots() {
        return slots;
    }

    public InventorySlot getSlot(int index) {
        if (index < 0 || index >= slots.size()) return null;
        return slots.get(index);
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean setCapacity(int capacity) {
        if (capacity < itemCount) return false;
        this.capacity = capacity;
        return true;
    }

    public void setIsCapacityUnlimited(boolean condition) {
        this.isCapacityUnlimited = condition;
    }

    public int getItemCount() {
        return itemCount;
    }

    public boolean isInventoryFull() {
        return !isCapacityUnlimited && (itemCount >= capacity);
    }
}
