package com.yourgame.Graphics.Map;

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

public class MapData {
    private final String name;
    private final TiledMap tiledMap;
    private final int mapWidth, mapHeight;
    private final MapObjects spawnPoints;
    private final MapObjects collisions;
    private final MapObjects teleporters;
    private final TileData[][] tileStates; // runtime data

    public MapData(String name, String pathToTmx) {
        this.name = name;

        this.tiledMap = new TmxMapLoader().load(pathToTmx);
        this.mapWidth = tiledMap.getProperties().get("width", Integer.class);
        this.mapHeight = tiledMap.getProperties().get("height", Integer.class);

        this.spawnPoints = tiledMap.getLayers().get("SpawnPoints").getObjects();
        this.collisions = tiledMap.getLayers().get("Collisions").getObjects();
        this.teleporters = tiledMap.getLayers().get("Teleporters").getObjects();

        this.tileStates = new TileData[mapWidth][mapHeight];
        initTileData();
    }

    private void initTileData() {
        // Initialize all tiles
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                tileStates[x][y] = new TileData();
            }
        }

        // === Handle COLLISIONS ===
        for (MapObject object : collisions) {
            if (object instanceof RectangleMapObject rectObj) {
                Rectangle rect = rectObj.getRectangle();

                int startX = (int)(rect.x / TileData.TILE_SIZE);
                int endX   = (int)((rect.x + rect.width) / TileData.TILE_SIZE);
                int startY = (int)(rect.y / TileData.TILE_SIZE);
                int endY   = (int)((rect.y + rect.height) / TileData.TILE_SIZE);

                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        if (inBounds(x, y, mapWidth, mapHeight)) {
                            tileStates[x][y].setBlocked(true);
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

                int startX = (int)(minX / TileData.TILE_SIZE);
                int endX   = (int)(maxX / TileData.TILE_SIZE);
                int startY = (int)(minY / TileData.TILE_SIZE);
                int endY   = (int)(maxY / TileData.TILE_SIZE);

                polygon.setPosition(polygon.getX(), polygon.getY());

                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        float tileCenterX = x * TileData.TILE_SIZE + TileData.TILE_SIZE / 2f;
                        float tileCenterY = y * TileData.TILE_SIZE + TileData.TILE_SIZE / 2f;

                        if (polygon.contains(tileCenterX, tileCenterY) && inBounds(x, y, mapWidth, mapHeight)) {
                            tileStates[x][y].setBlocked(true);
                        }
                    }
                }
            }
        }

        // === Handle TELEPORTERS ===
        for (MapObject object : teleporters) {
            if (object instanceof RectangleMapObject rectObj) {
                Rectangle rect = rectObj.getRectangle();

                int startX = (int)(rect.x / TileData.TILE_SIZE);
                int endX   = (int)((rect.x + rect.width) / TileData.TILE_SIZE);
                int startY = (int)(rect.y / TileData.TILE_SIZE);
                int endY   = (int)((rect.y + rect.height) / TileData.TILE_SIZE);

                String destination = object.getProperties().get("dest", String.class);
                String spawnName = object.getProperties().get("spawnName", String.class);
                Teleport teleport = new Teleport(destination, spawnName);

                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        if (inBounds(x, y, mapWidth, mapHeight)) {
                            tileStates[x][y].setTeleport(teleport);
                        }
                    }
                }
            }
        }
    }

    private boolean inBounds(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TileData getTileData(int x, int y) {
        return tileStates[x][y];
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
        int tileX = (int)(worldX / TileData.TILE_SIZE);
        int tileY = (int)(worldY / TileData.TILE_SIZE);

        if (tileX < 0 || tileY < 0 || tileX >= tileStates.length || tileY >= tileStates[0].length)
            return true;

        return tileStates[tileX][tileY].blocked();
    }

    public Teleport getTeleport(float worldX, float worldY) {
        int tileX = (int)(worldX / TileData.TILE_SIZE);
        int tileY = (int)(worldY / TileData.TILE_SIZE);

        if (tileX < 0 || tileY < 0 || tileX >= tileStates.length || tileY >= tileStates[0].length)
            return null;

        return tileStates[tileX][tileY].getTeleport();
    }
}
