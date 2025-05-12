package com.yourgame.model.Building;
import java.awt.Dimension;
import java.util.Map;

import com.yourgame.model.Item.Item;
import com.yourgame.model.Map.Coordinate;
import com.yourgame.model.Map.GameMap;
import com.yourgame.model.Map.Tile;

import java.util.ArrayList;
import java.util.List;
import com.yourgame.model.Item.Resource;

public class FarmMap extends GameMap {
    private List<String> crops;

    public FarmMap(String mapId, Tile[][] tiles, List<Building> buildings) {
        super(mapId, tiles, buildings);
        this.crops = new ArrayList<>();
    }

    public List<String> getCrops() {
        return crops;
    }

    public void addCrop(String crop) {
        crops.add(crop);
    }
}
