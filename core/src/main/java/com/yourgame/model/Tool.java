package com.yourgame.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Represents a tool or item that can be placed in the inventory.
 */
public class Tool {
    private final String name;
    private final TextureRegion textureRegion;
    // You can add more properties later, e.g., description, durability, action, etc.

    public Tool(String name, TextureRegion textureRegion) {
        this.name = name;
        this.textureRegion = textureRegion;
    }

    public String getName() {
        return name;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
}
