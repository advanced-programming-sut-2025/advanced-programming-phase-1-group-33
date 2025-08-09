package com.yourgame.model.Map;

import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.WeatherAndTime.Season;

import java.util.HashMap;
import java.util.List;

public class MapManager {
    private final Map town;
    private final HashMap<String, Map> buildings;
    private final HashMap<Player, Map> farms;
    private final HashMap<Player, Map> houses;
    private final HashMap<Player, Map> playersCurrentMap;

    public MapManager(List<Player> players) {
        town = new Map("town", "Game/Map/town.tmx");

        buildings = new HashMap<>();
        buildings.put("blacksmith", new Map("blacksmith", "Game/Map/Buildings/blacksmith.tmx"));
        buildings.put("carpenter", new Map("carpenter", "Game/Map/Buildings/carpenter.tmx"));
        buildings.put("fish-shop", new Map("fish-shop", "Game/Map/Buildings/fish-shop.tmx"));
        buildings.put("JojaMart", new Map("JojaMart", "Game/Map/Buildings/JojaMart.tmx"));
        buildings.put("marnie-ranch", new Map("marnie-ranch", "Game/Map/Buildings/marnie-ranch.tmx"));
        buildings.put("pierre-store", new Map("pierre-store", "Game/Map/Buildings/pierre-store.tmx"));
        buildings.put("saloon", new Map("saloon", "Game/Map/Buildings/saloon.tmx"));

        farms = new HashMap<>();
        houses = new HashMap<>();
        playersCurrentMap = new HashMap<>();
        for (Player player : players) {
            Farm farm = new Farm(player.getUsername() + "-farm", "Game/Map/standard-farm.tmx");
            farm.spawnRandomElements(Season.Spring);
            farms.put(player, farm);
            houses.put(
                player,
                new Map(player.getUsername() + "-house", "Game/Map/Buildings/farm-house.tmx")
            );
            playersCurrentMap.put(player, farm);
        }
    }

    public Map getTown() {
        return town;
    }

    public Map getFarm(Player player) {
        return farms.get(player);
    }

    public Map getHouse(Player player) {
        return houses.get(player);
    }

    public Map getPlayersCurrentMap(Player player) {
        return playersCurrentMap.get(player);
    }

    public Map getBuilding(String buildingName) {
        return buildings.get(buildingName);
    }

    public Map getBlacksmith() {
        return buildings.get("blacksmith");
    }

    public Map getCarpenter() {
        return buildings.get("carpenter");
    }

    public Map getFishShop() {
        return buildings.get("fish-shop");
    }

    public Map getJojaMart() {
        return buildings.get("JojaMart");
    }

    public Map getMarnieRanch() {
        return buildings.get("marnie-ranch");
    }

    public Map getPierreStore() {
        return buildings.get("pierre-store");
    }

    public Map getSaloon() {
        return buildings.get("saloon");
    }
}
