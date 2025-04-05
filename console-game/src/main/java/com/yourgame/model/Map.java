package com.yourgame.model;

import java.util.List;
import java.util.Map;

public class Map {
    private Tile[][] tiles;
    private List<Building> buildings;
    private Map<Coordinate, Item> spawnedForageables;
    private Map<Coordinate, ResourceNode> spawnedResources;

    public Map(Tile[][] tiles, List<Building> buildings) {
        this.tiles = tiles;
        this.buildings = buildings;
        this.spawnedForageables = new HashMap<>();
        this.spawnedResources = new HashMap<>();
    }

    public Tile getTileAt(Coordinate coordinate) {
        return tiles[coordinate.getX()][coordinate.getY()];
    }

    public boolean isOccupied(Coordinate coordinate) {
        // Check if the tile at the given coordinate is occupied by a building or another object
        return false; // Placeholder implementation
    }

    public List<Coordinate> findPath(Coordinate start, Coordinate end) {
        // Implement pathfinding logic here
        return new ArrayList<>(); // Placeholder implementation
    }

    public void placeObject(Coordinate coordinate, PlaceableObject object) {
        // Logic to place an object on the map
    }

    public void removeObject(Coordinate coordinate) {
        // Logic to remove an object from the map
    }

    public void spawnForageables() {
        // Logic to spawn forageable items on the map
    }
}