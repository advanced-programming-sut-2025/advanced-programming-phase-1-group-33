
package com.yourgame.model;

public class Tool extends Item {
    private int level;          // The level of the tool, e.g., wooden, steel, iridium, etc.
    private int energyCost;     // The energy consumed by using this tool

    // Constructor
    public Tool(String name, String description, int sellPrice, boolean isStackable, int level, int energyCost) {
        super(name, description, sellPrice, isStackable);
        this.level = level;
        this.energyCost = energyCost;
    }

    // Getters and setters
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public void setEnergyCost(int energyCost) {
        this.energyCost = energyCost;
    }

    // Specific method for tools (example: checking if the tool can be used on a certain tile)
    public boolean canUseOn(Tile tile) {
        // Placeholder for actual implementation (e.g., hoe can be used on soil tiles)
        return true; // For example, all tools can be used on all tiles for now.
    }

    // Override use method from Item class
    @Override
    public void use() {
        System.out.println("Using the tool: " + getName());
        // Add specific logic here based on the type of tool (e.g., hoe, pickaxe)
    }
}
