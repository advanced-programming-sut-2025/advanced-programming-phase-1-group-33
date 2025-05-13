package com.yourgame.model.Map;
import com.yourgame.model.enums.TileType;

public class Tile {
    private TileType type;
    private Object content; // Could be Item, ResourceNode, Building, etc.

    public Tile(TileType type) {
        this.type = type;
        // this.content = null;
    }

    // Getters and Setters

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
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
