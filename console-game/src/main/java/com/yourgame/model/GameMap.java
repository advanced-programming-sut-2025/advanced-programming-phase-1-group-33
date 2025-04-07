package com.yourgame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMap {
    private Tile[][] tiles;
    private List<Building> buildings;
    private Map<Coordinate, Item> spawnedForageables;
    private Map<Coordinate, Resource> spawnedResources;

    public GameMap(Tile[][] tiles, List<Building> buildings) {
        this.tiles = tiles;
        this.buildings = buildings;
        this.spawnedForageables = new HashMap<>();
        this.spawnedResources = new HashMap<>();
    }

    public Tile getTileAt(Coordinate coordinate) {
        return tiles[coordinate.getX()][coordinate.getY()];
    }

    public boolean isOccupied(Coordinate coordinate) {
        // Placeholder logic for checking occupancy
        for (Building building : buildings) {
            if (building.getCoordinate().equals(coordinate)) {
                return true;
            }
        }
        return spawnedForageables.containsKey(coordinate) || spawnedResources.containsKey(coordinate);
    }

    public List<Coordinate> findPath(Coordinate start, Coordinate end) {
        // Placeholder pathfinding logic
        return new ArrayList<>();
    }

    public void placeObject(Coordinate coordinate, Object object) {
        // This method needs specific logic based on object type
        if (object instanceof Item) {
            spawnedForageables.put(coordinate, (Item) object);
        } else if (object instanceof Resource) {
            spawnedResources.put(coordinate, (Resource) object);
        } else if (object instanceof Building) {
            buildings.add((Building) object);
        }
    }

    public void removeObject(Coordinate coordinate) {
        spawnedForageables.remove(coordinate);
        spawnedResources.remove(coordinate);
        buildings.removeIf(b -> b.getCoordinate().equals(coordinate));
    }

    public void spawnForageables() {
        // Sample placeholder logic to spawn items
    }

    // Getters and Setters

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public Map<Coordinate, Item> getSpawnedForageables() {
        return spawnedForageables;
    }

    public void setSpawnedForageables(Map<Coordinate, Item> spawnedForageables) {
        this.spawnedForageables = spawnedForageables;
    }

    public Map<Coordinate, Resource> getSpawnedResources() {
        return spawnedResources;
    }

    public void setSpawnedResources(Map<Coordinate, Resource> spawnedResources) {
        this.spawnedResources = spawnedResources;
    }
}
