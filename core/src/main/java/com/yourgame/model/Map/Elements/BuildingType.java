package com.yourgame.model.Map.Elements;

public enum BuildingType {
    COOP("Coop", 7, 8, "Game/Map/Buildings/coop.tmx"),
    BARN("Barn", 8, 8, "Game/Map/Buildings/barn.tmx");

    private final String name;
    private final int tileWidth;
    private final int tileHeight;
    private final String interiorMapPath;

    BuildingType(String name, int tileWidth, int tileHeight, String interiorMapPath) {
        this.name = name;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.interiorMapPath = interiorMapPath;
    }

    public String getName() { return name; }
    public int getTileWidth() { return tileWidth; }
    public int getTileHeight() { return tileHeight; }
    public String getInteriorMapPath() { return interiorMapPath; }
}
