package com.yourgame.model.enums;

import com.yourgame.model.Map.Tile;

public enum StoneType {
    Stone("Stone", 1, 10, "Sefid."),
    Wood("Wood", 2, 8, "Siah.");

    private final String name;
    private final int level;
    private final int energyCost;
    private final String actionDescription;

    // Constructor for enum constants
    StoneType(String name, int level, int energyCost, String actionDescription) {
        this.name = name;
        this.level = level;
        this.energyCost = energyCost;
        this.actionDescription = actionDescription;
    }

    // Getters for the enum fields
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    // Method to simulate using the tool
    public void use() {
        System.out.println("Using the " + name + ": " + actionDescription);
        // Additional logic for using the tool (e.g., interacting with game tiles)
    }

    // Example method to check if this tool can be used on a Tile
    public boolean canUseOn(Tile tile) {
        // Example check (you can add specific logic based on tile types)
        return true; // All tools can be used on all tiles in this basic implementation
    }
}
