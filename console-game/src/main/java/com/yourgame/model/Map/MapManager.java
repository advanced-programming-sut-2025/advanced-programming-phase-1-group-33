package com.yourgame.model.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.yourgame.model.enums.TileType;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.Building.FarmMap;
import com.yourgame.model.Item.Item;

public class MapManager {
    private Map<String, GameMap> maps;

    public MapManager(List<Player> players) {
        this.maps = new HashMap<>();
        initializeMap(players);
    }

    private void initializeMap(List<Player> players) {
        for (Player player : players) {
            addMap(getFarmMap(player));
        }
        addMap(getVilageMap(players));
        addMap(getBeachMap());
    }

    private GameMap getFarmMap(Player player) {
        String playerFarmId = player.getUsername() + "Farm";
        Tile[][] farmTiles = getDefaultFarmTiles(32, 32);
        FarmMap farmMap = new FarmMap(playerFarmId, farmTiles, new ArrayList<>());
        player.initializeFarm(farmMap);
        return farmMap;
    }

    private GameMap getBeachMap() {
        Tile[][] villageTiles = getDefaultBeachTiles(100, 100);

        GameMap gameMap = new GameMap("beachMap", villageTiles, new ArrayList<>());
        return gameMap;
    }

    private GameMap getVilageMap(List<Player> players) {
        Tile[][] villageTiles = getDefaultVillageTiles(100, 100);

        for (int i = 0; i < players.size(); i++) {
            villageTiles[i][0] = new Tile(TileType.PORTAL);
            villageTiles[i][0].setContent(new Portal(players.get(i).getFarmMapReference().getMapId(), 1, 0));
        }

        GameMap gameMap = new GameMap("villageMap", villageTiles, new ArrayList<>());
        return gameMap;

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

    public Tile[][] getTileContentFiller(Tile[][] tiles, Object content, int x1, int x2, int y1, int y2) {
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                tiles[i][j].setContent(content);
            }
        }
        return tiles;
    }

    public Tile[][] getTileFiller(Tile[][] tiles, TileType tileType, int x1, int x2, int y1, int y2) {
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                tiles[i][j] = new Tile(tileType);
            }
        }
        return tiles;
    }

    public Tile[][] getDefaultFarmTiles(int x, int y) {
        Tile[][] defaultTiles = new Tile[x][y];
        // Main is Grass
        defaultTiles = getTileFiller(defaultTiles, TileType.GRASS, 0, x, 0, y);
        // Base Bulding
        defaultTiles = getTileFiller(defaultTiles, TileType.BUILDING, 1, 7, 1, 6);
        // Placing The Lake
        defaultTiles = getTileFiller(defaultTiles, TileType.WATER, 13, 17, 12, 17);
        // Place the portal at a valid coordinate, for example at the bottom-right
        // corner.
        int portalRow = x - 1;
        int portalCol = y - 1;
        defaultTiles[portalRow][portalCol] = new Tile(TileType.PORTAL);

        // Set portal content with desired destination info (e.g., destination map ID
        // and a coordinate)
        defaultTiles[portalRow][portalCol].setContent(new Portal("villageMap", 1, 1));

        return defaultTiles;
    }

    public Tile[][] getDefaultVillageTiles(int x, int y) {
        Tile[][] defaultTiles = new Tile[x][y];
        defaultTiles = getTileFiller(defaultTiles, TileType.GRASS, 0, x, 0, y);

        defaultTiles = getTileFiller(defaultTiles, TileType.PORTAL, x - 1, x, 0, y);
        Portal beachPortal = new Portal("beachMap", 0, 0);
        defaultTiles = getTileContentFiller(defaultTiles, beachPortal, x - 1, x, 0, y);

        return defaultTiles;
    }

    public Tile[][] getDefaultBeachTiles(int x, int y) {
        Tile[][] defaultTiles = new Tile[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y / 2; j++) {
                defaultTiles[i][j] = new Tile(TileType.SAND);
            }
            for (int j = y / 2; j < y; j++) {
                defaultTiles[i][j] = new Tile(TileType.WATER);
            }
        }

        int portalRow = x - 1;
        int portalCol = y - 1;
        defaultTiles[portalRow][portalCol] = new Tile(TileType.PORTAL);
        // Set portal content with desired destination info (e.g., destination map ID
        // and a coordinate)
        defaultTiles[portalRow][portalCol].setContent(new Portal("villageMap", 0, 0));

        return defaultTiles;

    }


    public void populateRandomItems(GameMap map, List<Item> items) {
        Tile[][] tiles = map.getTiles();
        Random random = new Random();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                // For example, 10% chance to drop an item.
                if (tiles[i][j].getType().isWalkable() && !tiles[i][j].isOccupied() && random.nextDouble() < 0.1) {
                    // Pick a random item from the list.
                    Item randomItem = items.get(random.nextInt(items.size()));
                    map.placeObject(new Coordinate(i, j), randomItem);
                }
            }
        }
    }
}