package com.yourgame.model;
import com.yourgame.model.enums.ItemType;

public class Item {
    private String id;
    private String name;
    private String description;
    private ItemType type;
    private int value; // Could represent in-game currency or utility

    public Item(String id, String name, String description, ItemType type, int value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSellPrice() {
        return "";
    }

    public boolean isStackable() {
        return false;
    }
}
