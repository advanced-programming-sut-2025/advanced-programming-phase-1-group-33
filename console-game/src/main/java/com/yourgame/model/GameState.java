package com.yourgame.model;

import java.util.List;

public class GameState {
    private List<Player> players;
    private int currentTurnPlayerIndex;
    private TimeSystem gameTime;
    private WeatherSystem weather;
    private Map map;
    private List<NPC> npcs;
    private List<Shop> shops;

    public GameState(List<Player> players, TimeSystem gameTime, WeatherSystem weather, Map map, List<NPC> npcs, List<Shop> shops) {
        this.players = players;
        this.currentTurnPlayerIndex = 0; // Start with the first player
        this.gameTime = gameTime;
        this.weather = weather;
        this.map = map;
        this.npcs = npcs;
        this.shops = shops;
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
}