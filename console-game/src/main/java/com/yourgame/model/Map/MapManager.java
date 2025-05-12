package com.yourgame.model.Map;

import java.util.HashMap;
import java.util.Map;

public class MapManager {
    private Map<String, GameMap> maps;


    public MapManager() {
        maps = new HashMap<>();
    }

    public void addMap(GameMap map) {
        maps.put(map.getMapId(), map);
    }

    public GameMap getMap(String mapId) {
        return maps.get(mapId);
    }

    public Map<String, GameMap> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, GameMap> maps) {
        this.maps = maps;
    }

    // Given a current map and a coordinate, check if the tile is a portal
    // and return the destination if so.
    public Portal checkPortal(GameMap currentMap, Coordinate coordinate) {
        Tile tile = currentMap.getTileAt(coordinate);
        if (tile.getType() == com.yourgame.model.enums.TileType.PORTAL && tile.getContent() instanceof Portal) {
            return (Portal) tile.getContent();
        }
        return null;
    }
}