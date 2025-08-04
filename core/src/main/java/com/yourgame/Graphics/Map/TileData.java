package com.yourgame.Graphics.Map;

public class TileData {
    public static final int TILE_SIZE = 16;

    private boolean blocked;
    private Teleport teleport; // Null for non-teleportable tiles

    public TileData() {
        blocked = false;
        teleport = null;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean blocked() {
        return blocked;
    }

    public void setTeleport(Teleport teleport) {
        this.teleport = teleport;
    }

    public Teleport getTeleport() {
        return teleport;
    }
}
