package com.yourgame.model.Map;

import com.yourgame.model.Farming.Fertilizer;
import com.yourgame.model.Farming.Plant;

public class Tile {
    public static final int TILE_SIZE = 16;

    public enum DirtState {
        NON_FARMABLE, NORMAL, PLOWED, WATERED,
        PLOWED_GROWTH, PLOWED_WATER, WATERED_GROWTH, WATERED_WATER // These are fertilized tiles
    }

    private DirtState dirtState;
    private Fertilizer fertilizer;
    private boolean isWatered;
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
        if (fertilizer == Fertilizer.Growth_Fertilizer) {
            if (dirtState == DirtState.PLOWED) dirtState = DirtState.PLOWED_GROWTH;
            else if (dirtState == DirtState.WATERED) dirtState = DirtState.WATERED_GROWTH;
        } else {
            if (dirtState == DirtState.PLOWED) dirtState = DirtState.PLOWED_WATER;
            else if (dirtState == DirtState.WATERED) dirtState = DirtState.WATERED_WATER;
        }
    }

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
        if (element instanceof Plant plant) plant.setWateredToday(watered);
        if (watered) {
            if (dirtState == DirtState.PLOWED) {
                dirtState = DirtState.WATERED;
            } else if (dirtState == DirtState.PLOWED_GROWTH) {
                dirtState = DirtState.WATERED_GROWTH;
            } else if (dirtState == DirtState.PLOWED_WATER) {
                dirtState = DirtState.WATERED_WATER;
            }
        } else {
            if (dirtState == DirtState.WATERED) {
                dirtState = DirtState.PLOWED;
            } else if (dirtState == DirtState.WATERED_GROWTH) {
                dirtState = DirtState.PLOWED_GROWTH;
            } else if (dirtState == DirtState.WATERED_WATER) {
                dirtState = DirtState.PLOWED_WATER;
            }
        }
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
