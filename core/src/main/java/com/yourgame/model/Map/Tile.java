package com.yourgame.model.Map;

import com.yourgame.model.Farming.Fertilizer;

public class Tile {
    public static final int TILE_SIZE = 16;

    public enum DirtState {NON_FARMABLE, NORMAL, PLOWED, WATERED}

    private DirtState dirtState;
    private Fertilizer fertilizer;
    private boolean walkable;
    private boolean spawnable;
    private Teleport teleport; // Null for non-teleportable tiles
    private MapElement element;
    public final int tileX, tileY;

    public Tile(int tileX, int tileY) {
        dirtState = DirtState.NON_FARMABLE;
        fertilizer = null;
        walkable = true;
        spawnable = false;
        teleport = null;
        element = null;
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public DirtState getDirtState() {
        return dirtState;
    }

    public void setDirtState(DirtState dirtState) {
        this.dirtState = dirtState;
    }

    public Fertilizer getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(Fertilizer fertilizer) {
        this.fertilizer = fertilizer;
    }

    public boolean isWatered() {
        return dirtState == DirtState.WATERED;
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
