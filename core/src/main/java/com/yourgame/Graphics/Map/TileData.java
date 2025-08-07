package com.yourgame.Graphics.Map;

public class TileData {
    public static final int TILE_SIZE = 16;

    private boolean walkable;
    private boolean spawnable;
    private Teleport teleport; // Null for non-teleportable tiles
    private MapElement element;

    public TileData() {
        walkable = true;
        spawnable = false;
        teleport = null;
        element = null;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setSpawnable(boolean spawnable) {
        this.spawnable = spawnable;
    }

    public boolean isSpawnable() {
        return spawnable && element == null;
    }

    public void setTeleport(Teleport teleport) {
        this.teleport = teleport;
    }

    public Teleport getTeleport() {
        return teleport;
    }

    public void setElement(MapElement element) {
        this.element = element;
    }

    public MapElement getElement() {
        return element;
    }
}
