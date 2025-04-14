package com.yourgame.model.enums;

import com.yourgame.model.Map.Tile;

public enum ToolType {
    HOE("Hoe", 1, 10, "Tilling the soil."),
    PICKAXE("Pickaxe", 2, 15, "Breaking rocks."),
    AXE("Axe", 3, 12, "Chopping wood."),
    WATERING_CAN("Watering Can", 1, 5, "Watering crops."),
    FISHING_POLE("Fishing Pole", 1, 8, "Fishing in water."),
    SCYTHE("Scythe", 3, 20, "Harvesting crops."),
    MILK_PAIL("Milk Pail", 2, 10, "Milking animals."),
    SHEARS("Shears", 2, 8, "Shearing sheep.");

    private final String name;
    private final int level;
    private final int energyCost;
    private final String actionDescription;

    // Constructor for enum constants
    ToolType(String name, int level, int energyCost, String actionDescription) {
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
