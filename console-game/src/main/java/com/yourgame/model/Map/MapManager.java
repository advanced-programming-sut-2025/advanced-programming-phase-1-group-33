package com.yourgame.model.Map;

import java.util.HashMap;
import java.util.Map;

import com.yourgame.model.enums.TileType;
import com.yourgame.model.Map.Portal;

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
        if (tile.getType() == TileType.PORTAL && tile.getContent() instanceof Portal) {
            return (Portal) tile.getContent();
        }
        return null;
    }

    public Tile[][] getDefaultFarmTiles(int x, int y) {
        Tile[][] defaultTiles = new Tile[x][y]; 

        for(int i = 0 ; i< x; i++){
            for(int j = 0; j < y ; j++){
                defaultTiles[i][j] = new Tile(TileType.GRASS); 
            }
        }
        for(int i = x/2 - 2 ; i < x/2 + 3; i++){
            for(int j = y/2 - 2 ; j < y/2 + 3 ; j++){
                defaultTiles[i][j] = new Tile(TileType.WATER); 
            }
        }
        
        return defaultTiles; 
    }


    public Tile[][] getDefaultBeachTiles(int x, int y) {
        Tile[][] defaultTiles = new Tile[x][y]; 

        for(int i = 0 ; i< x; i++){
            for(int j = 0; j < y/2 ; j++){
                defaultTiles[i][j] = new Tile(TileType.SAND); 
            }
            for(int j = y/2; j < y ; j++){
                defaultTiles[i][j] = new Tile(TileType.WATER); 
            }
        }
        return defaultTiles; 

    }
}