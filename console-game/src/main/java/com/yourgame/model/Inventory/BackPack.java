package com.yourgame.model.Inventory;

import java.util.ArrayList;

import com.yourgame.model.enums.BackPackType;

public class BackPack {
    private final ArrayList<Slot> slots = new ArrayList<>();
    private BackPackType type;

    public BackPack(BackPackType type) {
        this.type = type;
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public void setType(BackPackType type) {
        this.type = type;
    }

    public BackPackType getType() {
        return type;
    }


}
