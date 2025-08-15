package com.yourgame.model.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.yourgame.model.App;
import com.yourgame.model.WeatherAndTime.TimeObserver;
import com.yourgame.model.WeatherAndTime.TimeSystem;
import com.yourgame.model.WeatherAndTime.Weather;

public class Farm extends Map implements TimeObserver {
    private final TiledMapTileLayer farmingLayer;
    private final TiledMapTileLayer greenHouseLayer;
    private final TiledMapTile plowedTile;
    private final TiledMapTile wateredTile;
    private final TiledMapTile plowedGrowthTile;
    private final TiledMapTile plowedWaterTile;
    private final TiledMapTile wateredGrowthTile;
    private final TiledMapTile wateredWaterTile;

    public Farm(String name, String pathToTmx) {
        super(name, pathToTmx);
        this.farmingLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Farming");
        this.greenHouseLayer = (TiledMapTileLayer) tiledMap.getLayers().get("GreenHouse");
        this.plowedTile = tiledMap.getTileSets().getTileSet("outdoors-spring").getTile(786);
        this.wateredTile = tiledMap.getTileSets().getTileSet("outdoors-spring").getTile(2161);
        this.plowedGrowthTile = tiledMap.getTileSets().getTileSet("outdoors-spring").getTile(929);
        this.plowedWaterTile = tiledMap.getTileSets().getTileSet("outdoors-spring").getTile(928);
        this.wateredGrowthTile = tiledMap.getTileSets().getTileSet("outdoors-spring").getTile(888);
        this.wateredWaterTile = tiledMap.getTileSets().getTileSet("outdoors-spring").getTile(887);
        initSpawnable();
        initFishable();
    }

    private void initSpawnable() {
        MapLayer spawnable = tiledMap.getLayers().get("Spawnables");
        if (spawnable == null) return;

        for (MapObject object : spawnable.getObjects()) {
            if (object instanceof RectangleMapObject rectObj) {
                Rectangle rect = rectObj.getRectangle();

                int startX = (int)(rect.x / Tile.TILE_SIZE);
                int endX   = (int)((rect.x + rect.width) / Tile.TILE_SIZE);
                int startY = (int)(rect.y / Tile.TILE_SIZE);
                int endY   = (int)((rect.y + rect.height) / Tile.TILE_SIZE);

                for (int x = startX; x <= endX; x++) {
                    for (int y = startY; y <= endY; y++) {
                        if (isInBounds(x, y, mapWidth, mapHeight)) {
                            tileStates[x][y].setSpawnable(true);
                            tileStates[x][y].setDirtState(Tile.DirtState.NORMAL);
                        }
                    }
                }
            }
        }
    }

    private void setFishable(Polygon polygon) {
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
                    tileStates[x][y].setFishable(true);
                }
            }
        }
    }

    private void initFishable() {
        MapObject smallLake = collisions.get("small-lake");
        if (smallLake instanceof PolygonMapObject polyObj) {
            setFishable(polyObj.getPolygon());
        }

        MapObject bigLake = collisions.get("big-lake");
        if (bigLake instanceof PolygonMapObject polyObj) {
            setFishable(polyObj.getPolygon());
        }
    }

    public void updateTileVisuals(int tileX, int tileY) {
        Tile data = getTile(tileX, tileY);
        if (data == null || farmingLayer == null) return;

        TiledMapTileLayer.Cell cell = farmingLayer.getCell(tileX, tileY);
        if (cell == null) {
            cell = new TiledMapTileLayer.Cell();
            farmingLayer.setCell(tileX, tileY, cell);
        }

        switch (data.getDirtState()) {
            case PLOWED -> cell.setTile(plowedTile);
            case WATERED -> cell.setTile(wateredTile);
            case PLOWED_GROWTH -> cell.setTile(plowedGrowthTile);
            case WATERED_GROWTH -> cell.setTile(wateredGrowthTile);
            case PLOWED_WATER -> cell.setTile(plowedWaterTile);
            case WATERED_WATER -> cell.setTile(wateredWaterTile);
            default -> cell.setTile(null);
        }
    }

    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        spawnRandomElements(timeSystem.getSeason());
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                tileStates[x][y].setWatered(timeSystem.getWeather() == Weather.Rainy);
                updateTileVisuals(x, y);
            }
        }
        if (timeSystem.getWeather() == Weather.Stormy) {
            App.getGameState().getThunderManager().triggerThunderStrike(this);
        }
    }

    public void setGreenHouseLayerVisible(boolean visible) {
        greenHouseLayer.setVisible(visible);
    }
}
