package com.yourgame.Graphics.Map;

import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Season;

import java.util.HashMap;
import java.util.List;

public class MapManager {
    private final MapData town;
    private final HashMap<String, MapData> buildings;
    private final HashMap<Player, MapData> farms;
    private final HashMap<Player, MapData> houses;
    private final HashMap<Player, MapData> playersCurrentMap;

    public MapManager(List<Player> players) {
        town = new MapData("town", "Game/Map/town.tmx");

        buildings = new HashMap<>();
        buildings.put("blacksmith", new MapData("blacksmith", "Game/Map/Buildings/blacksmith.tmx"));
        buildings.put("carpenter", new MapData("carpenter", "Game/Map/Buildings/carpenter.tmx"));
        buildings.put("fish-shop", new MapData("fish-shop", "Game/Map/Buildings/fish-shop.tmx"));
        buildings.put("JojaMart", new MapData("JojaMart", "Game/Map/Buildings/JojaMart.tmx"));
        buildings.put("marnie-ranch", new MapData("marnie-ranch", "Game/Map/Buildings/marnie-ranch.tmx"));
        buildings.put("pierre-store", new MapData("pierre-store", "Game/Map/Buildings/pierre-store.tmx"));
        buildings.put("saloon", new MapData("saloon", "Game/Map/Buildings/saloon.tmx"));

        farms = new HashMap<>();
        houses = new HashMap<>();
        playersCurrentMap = new HashMap<>();
        for (Player player : players) {
            MapData farm = new MapData(player.getUsername() + "-farm", "Game/Map/standard-farm.tmx");
            farm.spawnRandomElements(Season.Winter);
            farms.put(player, farm);
            houses.put(
                player,
                new MapData(player.getUsername() + "-house", "Game/Map/Buildings/farm-house.tmx")
            );
            playersCurrentMap.put(player, farm);
        }
    }

    public MapData getTown() {
        return town;
    }

    public MapData getFarm(Player player) {
        return farms.get(player);
    }

    public MapData getHouse(Player player) {
        return houses.get(player);
    }

    public MapData getPlayersCurrentMap(Player player) {
        return playersCurrentMap.get(player);
    }

    public MapData getBuilding(String buildingName) {
        return buildings.get(buildingName);
    }

    public MapData getBlacksmith() {
        return buildings.get("blacksmith");
    }

    public MapData getCarpenter() {
        return buildings.get("carpenter");
    }

    public MapData getFishShop() {
        return buildings.get("fish-shop");
    }

    public MapData getJojaMart() {
        return buildings.get("JojaMart");
    }

    public MapData getMarnieRanch() {
        return buildings.get("marnie-ranch");
    }

    public MapData getPierreStore() {
        return buildings.get("pierre-store");
    }

    public MapData getSaloon() {
        return buildings.get("saloon");
    }
}
