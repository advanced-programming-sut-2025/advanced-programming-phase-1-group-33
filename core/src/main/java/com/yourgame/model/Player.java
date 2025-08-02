package com.yourgame.model;

import com.badlogic.gdx.math.Vector2;
import com.yourgame.model.Inventory.Backpack;
import com.yourgame.model.Inventory.Tools.Axe;
import com.yourgame.model.Inventory.Tools.Hoe;
import com.yourgame.model.Inventory.Tools.Pickaxe;
import com.yourgame.model.Inventory.Tools.Scythe;
import com.yourgame.model.Inventory.Tools.Tool;
import com.yourgame.model.Inventory.Tools.WateringCan;
import com.yourgame.model.Skill.Ability;

/**
 * Represents the Player's data model in the game.
 * This class holds the state and attributes of the player but contains no
 * game logic related to rendering or input. It's a Plain Old Java Object (POJO).
 */
public class Player {

    // Core Player Info
    private final String username;
    private final String nickname;
    private final int maxEnergy = 200;
    private int energy;

    // Game World State (for a graphical, real-time game)
    private final Vector2 position;
    private final Vector2 velocity;
    private int direction; // 0=Down, 1=Right, 2=Up, 3=Left

    // Inventory and Tools
    // private final Backpack backpack;
    private Tool currentTool;

    // Skills and Abilities
    // private final Ability ability;

    // Player Status
    private boolean isFainted = false;
    private boolean isMarried = false;


    public Player(String username, String nickname, Vector2 initialPosition) {
        this.username = username;
        this.nickname = nickname;
        this.position = initialPosition;
        this.velocity = new Vector2(0, 0);
        this.direction = 0; // Default facing down
        this.energy = maxEnergy;

        // this.backpack = new Backpack(/* Assuming some BackpackType enum */);
        // initializeDefaultTools();
        // Set the first tool as the current one by default
        // if (!this.backpack.getTools().isEmpty()) {
        //     this.currentTool = this.backpack.getTools().get(0);
        // }

        // this.ability = new Ability(this);
    }

    /**
     * Populates the player's backpack with the default set of tools.
     */
    // private void initializeDefaultTools() {
    //     backpack.getTools().add(new Hoe());
    //     backpack.getTools().add(new Pickaxe());
    //     backpack.getTools().add(new Axe());
    //     backpack.getTools().add(new WateringCan());
    //     backpack.getTools().add(new Scythe());
    //     // You can add default items like coins or wood here as well
    // }

    // --- GETTERS AND SETTERS ---

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getEnergy() {
        return energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void consumeEnergy(int amount) {
        this.energy -= amount;
        if (this.energy <= 0) {
            this.energy = 0;
            setFainted(true);
            // The game logic will check isFainted() and handle the consequences.
        }
    }

    public void restoreEnergy(int amount) {
        this.energy += amount;
        if (this.energy > maxEnergy) {
            this.energy = maxEnergy;
        }
    }

    public boolean isFainted() {
        return isFainted;
    }

    public void setFainted(boolean fainted) {
        isFainted = fainted;
    }
    
    // public Backpack getBackpack() {
    //     return backpack;
    // }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }
    
    // public Ability getAbility() {
    //     return ability;
    // }

    public String getUsername() {
        return username;
    }
}
