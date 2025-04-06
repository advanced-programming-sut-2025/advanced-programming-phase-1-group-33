package com.yourgame.model;

import java.util.List;
import java.util.Map;

public class GameState {
    private List<Player> players;
    private int currentTurnPlayerIndex;
    private TimeSystem gameTime;
    private WeatherSystem weather;
    private GameMap map;
    private List<NPC> npcs;
    private List<Shop> shops;

    public GameState(List<Player> players, TimeSystem gameTime, WeatherSystem weather, GameMap map, List<NPC> npcs, List<Shop> shops) {
        this.players = players;
        this.currentTurnPlayerIndex = 0; // Start with the first player
        this.gameTime = gameTime;
        this.weather = weather;
        this.map = map;
        this.npcs = npcs;
        this.shops = shops;
    }

    public GameState() {

    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void nextTurn() {
        currentTurnPlayerIndex = (currentTurnPlayerIndex + 1) % players.size();
        // Additional logic for advancing game time and updating state can be added here
    }

    public void saveState() {
        // Logic for saving the game state (e.g., to a file or database)
    }

    public void loadState() {
        // Logic for loading the game state (e.g., from a file or database)
    }

    // Getters and setters for the attributes can be added here
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentTurnPlayerIndex() {
        return currentTurnPlayerIndex;
    }

    public void setCurrentTurnPlayerIndex(int currentTurnPlayerIndex) {
        this.currentTurnPlayerIndex = currentTurnPlayerIndex;
    }

    public TimeSystem getGameTime() {
        return gameTime;
    }

    public void setGameTime(TimeSystem gameTime) {
        this.gameTime = gameTime;
    }

    public WeatherSystem getWeather() {
        return weather;
    }

    public void setWeather(WeatherSystem weather) {
        this.weather = weather;
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public void setNpcs(List<NPC> npcs) {
        this.npcs = npcs;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public void startGame() {
    }
}
