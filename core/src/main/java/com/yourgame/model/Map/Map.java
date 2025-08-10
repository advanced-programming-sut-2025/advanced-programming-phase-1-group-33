package com.yourgame.model.Map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.yourgame.model.Farming.ForagingCrop;
import com.yourgame.model.Farming.ForagingCropElement;
import com.yourgame.model.Farming.Tree;
import com.yourgame.model.Farming.TreeType;
import com.yourgame.model.Map.Elements.Rock;
import com.yourgame.model.Map.Elements.WoodElement;
import com.yourgame.model.WeatherAndTime.Season;
import com.yourgame.model.WeatherAndTime.TimeSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    // Spawning elements
    private static final Random rand = new Random();
    private static final Tree oakPrototype;
    private static final Tree pinePrototype;
    private static final Tree maplePrototype;
    static {
        oakPrototype = new Tree(TreeType.OakTree, new TimeSystem(), null, 0, 0);
        oakPrototype.setMature();

        pinePrototype = new Tree(TreeType.PineTree, new TimeSystem(), null, 0, 0);
        pinePrototype.setMature();

        maplePrototype = new Tree(TreeType.MapleTree, new TimeSystem(), null, 0, 0);
        maplePrototype.setMature();
    }

    protected final String name;
    protected final TiledMap tiledMap;
    protected final int mapWidth, mapHeight;
    protected final MapObjects spawnPoints;
    protected final MapObjects collisions;
    protected final MapObjects teleporters;
    protected final Tile[][] tileStates; // runtime data
    protected final List<MapElement> elements;

    public Map(String name, String pathToTmx) {
        this.name = name;

        this.tiledMap = new TmxMapLoader().load(pathToTmx);
        this.mapWidth = tiledMap.getProperties().get("width", Integer.class);
        this.mapHeight = tiledMap.getProperties().get("height", Integer.class);

        this.spawnPoints = tiledMap.getLayers().get("SpawnPoints").getObjects();
        this.collisions = tiledMap.getLayers().get("Collisions").getObjects();
        this.teleporters = tiledMap.getLayers().get("Teleporters").getObjects();

        this.tileStates = new Tile[mapWidth][mapHeight];
        this.elements = new ArrayList<>();

        initTileData();
    }

    private void initTileData() {
        // Initialize all tiles
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                tileStates[x][y] = new Tile(x, y);
            }
        }

        initCollisions();
        initTeleporters();
    }

    private void initCollisions() {
        for (MapObject object : collisions) {
            if (object instanceof RectangleMapObject rectObj) {
                Rectangle rect = rectObj.getRectangle();

                int startX = (int)(rect.x / Tile.TILE_SIZE);
                int endX   = (int)((rect.x + rect.width) / Tile.TILE_SIZE);
                int startY = (int)(rect.y / Tile.TILE_SIZE);
                int endY   = (int)((rect.y + rect.height) / Tile.TILE_SIZE);

                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        if (isInBounds(x, y, mapWidth, mapHeight)) {
                            tileStates[x][y].setWalkable(false);
                        }
                    }
                }
            } else if (object instanceof PolygonMapObject polyObj) {
                Polygon polygon = polyObj.getPolygon();
                float[] verts = polygon.getTransformedVertices();

                float minX = Float.MAX_VALUE, maxX = Float.MIN_VALUE;
                float minY = Float.MAX_VALUE, maxY = Float.MIN_VALUE;

                for (int i = 0; i < verts.length; i += 2) {
                    minX = Math.min(minX, verts[i]);
                    maxX = Math.max(maxX, verts[i]);
                    minY = Math.min(minY, verts[i + 1]);
                    maxY = Math.max(maxY, verts[i + 1]);
                }

                int startX = (int)(minX / Tile.TILE_SIZE);
                int endX   = (int)(maxX / Tile.TILE_SIZE);
                int startY = (int)(minY / Tile.TILE_SIZE);
                int endY   = (int)(maxY / Tile.TILE_SIZE);

                polygon.setPosition(polygon.getX(), polygon.getY());

                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        float tileCenterX = x * Tile.TILE_SIZE + Tile.TILE_SIZE / 2f;
                        float tileCenterY = y * Tile.TILE_SIZE + Tile.TILE_SIZE / 2f;

                        if (polygon.contains(tileCenterX, tileCenterY) && isInBounds(x, y, mapWidth, mapHeight)) {
                            tileStates[x][y].setWalkable(false);
                        }
                    }
                }
            }
        }
    }

    private void initTeleporters() {
        for (MapObject object : teleporters) {
            if (object instanceof RectangleMapObject rectObj) {
                Rectangle rect = rectObj.getRectangle();

                int startX = (int)(rect.x / Tile.TILE_SIZE);
                int endX   = (int)((rect.x + rect.width) / Tile.TILE_SIZE);
                int startY = (int)(rect.y / Tile.TILE_SIZE);
                int endY   = (int)((rect.y + rect.height) / Tile.TILE_SIZE);

                String destination = object.getProperties().get("dest", String.class);
                String spawnName = object.getProperties().get("spawnName", String.class);
                Teleport teleport = new Teleport(destination, spawnName);

                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        if (isInBounds(x, y, mapWidth, mapHeight)) {
                            tileStates[x][y].setTeleport(teleport);
                        }
                    }
                }
            }
        }
    }

    protected boolean isInBounds(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public Tile getTile(int tileX, int tileY) {
        return tileStates[tileX][tileY];
    }

    public Vector2 getSpawnPoint(String spawnName) {
        MapObject object = spawnPoints.get(spawnName);

        if (object instanceof PointMapObject) {
            return ((PointMapObject) object).getPoint();
        }

        return new Vector2();
    }

    public Vector2 getSpawnPoint() {
        return getSpawnPoint("spawn");
    }

    public boolean isTileBlocked(float worldX, float worldY) {
        int tileX = (int)(worldX / Tile.TILE_SIZE);
        int tileY = (int)(worldY / Tile.TILE_SIZE);

        if (tileX < 0 || tileY < 0 || tileX >= tileStates.length || tileY >= tileStates[0].length)
            return true;

        return !tileStates[tileX][tileY].isWalkable();
    }

    public Teleport getTeleport(float worldX, float worldY) {
        int tileX = (int)(worldX / Tile.TILE_SIZE);
        int tileY = (int)(worldY / Tile.TILE_SIZE);

        if (tileX < 0 || tileY < 0 || tileX >= tileStates.length || tileY >= tileStates[0].length)
            return null;

        return tileStates[tileX][tileY].getTeleport();
    }

    public List<MapElement> getMapElements() {
        return elements;
    }

    public boolean addElement(MapElement element) {
        if (!isElementPlaceable(element)) return false;
        elements.add(element);

        java.awt.Rectangle bounds = element.getTileBounds();
        for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
            for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
                Tile tile = tileStates[x][y];
                tile.setElement(element);
                tile.setWalkable(false);
            }
        }

        return true;
    }

    private boolean isElementPlaceable(MapElement element) {
        if (element == null) return false;
        java.awt.Rectangle bounds = element.getTileBounds();
        for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
            for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
                Tile tile = tileStates[x][y];
                if (tile == null || !tile.isSpawnable()) return false;
            }
        }
        return true;
    }

    public void removeElement(MapElement element) {
        elements.remove(element);
        java.awt.Rectangle bounds = element.getTileBounds();
        for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
            for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
                Tile tile = tileStates[x][y];
                tile.setElement(null);
                tile.setWalkable(true);
            }
        }
    }

    public void spawnRandomElements(Season season) {
        spawnRandomTrees();
        spawnRandomWoods();
        spawnRandomRocks();
        spawnRandomForagingCrops(season);
    }

    public void spawnObject(MapElement prototype, int count) {
        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(mapWidth);
            int y = rand.nextInt(mapHeight);
            MapElement element = prototype.clone(x, y);
            addElement(element);
        }
    }

    public void spawnRandomTrees() {
        int oakNumber = 10 + rand.nextInt(10);
        spawnObject(oakPrototype, oakNumber);

        int mapleNumber = 10 + rand.nextInt(10);
        spawnObject(maplePrototype, mapleNumber);

        int pineNumber = 10 + rand.nextInt(10);
        spawnObject(pinePrototype, pineNumber);
    }

    public void spawnRandomWoods() {
        int stumpNumber = 10 + rand.nextInt(10);
        spawnObject(new WoodElement(true, 0, 0), stumpNumber);

        int trunkNumber = 5 + rand.nextInt(5);
        spawnObject(new WoodElement(false, 0, 0), trunkNumber);
    }

    public void spawnRandomRocks() {
        int smallNumber = 15 + rand.nextInt(10);
        spawnObject(new Rock(true, 0, 0), smallNumber);

        int bigNumber = 20 + rand.nextInt(5);
        spawnObject(new Rock(false, 0, 0), bigNumber);
    }

    public void spawnRandomForagingCrops(Season season) {
        List<ForagingCrop> foragingCrops = ForagingCrop.getCropsBySeason(season);
        int number = 5 + rand.nextInt(3);
        for (int i = 0; i < number; i++) {
            int index = rand.nextInt(foragingCrops.size());
            ForagingCropElement foraging = new ForagingCropElement(foragingCrops.get(index), 0, 0);
            spawnObject(foraging, 1);
        }
    }
}
