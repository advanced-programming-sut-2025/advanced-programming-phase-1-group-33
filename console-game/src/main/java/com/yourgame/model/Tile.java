package com.yourgame.model;
import com.yourgame.model.enums.TileType;

public class Tile {
    private TileType type;
    private boolean walkable;
    private Object content; // Could be Item, ResourceNode, Building, etc.

    public Tile(TileType type, boolean walkable) {
        this.type = type;
        this.walkable = walkable;
        this.content = null;
    }

    // Getters and Setters

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public boolean isOccupied() {
        return content != null;
    }

    public void clearContent() {
        this.content = null;
    }
}
