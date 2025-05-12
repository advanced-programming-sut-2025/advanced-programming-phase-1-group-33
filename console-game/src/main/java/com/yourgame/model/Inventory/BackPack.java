package com.yourgame.model.Inventory;


import com.yourgame.model.enums.BackPackType;
import com.yourgame.model.Item.*;
import java.util.ArrayList;

public class BackPack {
    private ArrayList<Slot> slots = new ArrayList<>();
    private BackPackType type;

    public BackPack() {

    }

    public BackPack(BackPackType type) {
        this.type = type;
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public BackPackType getType() {
        return type;
    }

    public void setType(BackPackType type) {
        this.type = type;
    }

    public Slot getSlotByItemName(String itemName) {
        for (Slot slot : slots) {
            if (slot.getItem().getName().compareToIgnoreCase(itemName) == 0) {
                return slot;
            }
        }
        return null;
    }


    public String showInventory() {
        StringBuilder output = new StringBuilder();
        for (Slot slot : slots) {
            output.append(slot.toString()).append("\n");
        }
        return output.toString();
    }

    public String showTools() {
        StringBuilder tools = new StringBuilder();
        for (Slot slot : slots) {
            if (slot.getItem() instanceof Tool)
                tools.append(slot.toString()).append("\n");
        }
        return tools.toString();
    }
}
