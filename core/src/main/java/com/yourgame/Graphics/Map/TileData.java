package com.yourgame.Graphics.Map;

public class TileData {
    public static final int TILE_SIZE = 16;

    private boolean blocked;
    private String teleportDestination; // Null for non-teleportable tiles

    public TileData() {
        blocked = false;
        teleportDestination = null;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean blocked() {
        return blocked;
    }

    public void setTeleportDestination(String destination) {
        teleportDestination = destination;
    }

    public String getTeleportDestination() {
        return teleportDestination;
    }
}
