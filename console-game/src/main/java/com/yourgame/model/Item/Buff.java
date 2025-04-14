package com.yourgame.model.Item;

import com.yourgame.model.Player;

public class Buff {
    private String name;            // Name of the buff (e.g., "Speed Boost")
    private String description;     // Description of what the buff does
    private int duration;           // Duration of the buff (in turns or seconds)
    private String effectType;      // What the buff affects (e.g., "Speed", "Strength", "Health")
    private double effectAmount;    // The magnitude of the effect (e.g., 1.25 for 25% increase)

    // Constructor
    public Buff(String name, String description, int duration, String effectType, double effectAmount) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.effectType = effectType;
        this.effectAmount = effectAmount;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public String getEffectType() {
        return effectType;
    }

    public double getEffectAmount() {
        return effectAmount;
    }

    // Method to apply the buff effect (this would be part of your game mechanics logic)
    public void applyBuff(Player player) {
        // Example: Apply buff effect to a player
        if ("Speed".equals(effectType)) {
            player.setSpeed(player.getSpeed() * effectAmount); // Increase speed by the buff amount
        } else if ("Health".equals(effectType)) {
            player.setHealth(player.getHealth() + effectAmount); // Increase health
        }
        // You can extend this with other types of buffs (e.g., Strength, Defense, etc.)
    }

    // Method to remove the buff effect (for when the buff expires)
    public void removeBuff(Player player) {
        if ("Speed".equals(effectType)) {
            player.setSpeed(player.getSpeed() / effectAmount); // Revert speed change
        } else if ("Health".equals(effectType)) {
            player.setHealth(player.getHealth() - effectAmount); // Revert health increase
        }
    }

    // Method to check if the buff is still active
    public boolean isActive() {
        return duration > 0;
    }

    // Method to update the buff (e.g., decrease duration over time)
    public void updateBuff() {
        if (duration > 0) {
            duration--;
        }
    }
}
