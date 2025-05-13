package com.yourgame.model;

import java.util.List;

import com.yourgame.model.Map.GameMap;
import com.yourgame.model.Map.MapManager;
import com.yourgame.model.Npc.NPC;
import com.yourgame.model.Shop.Shop;
import com.yourgame.model.UserInfo.Player;
import com.yourgame.model.Weather.TimeSystem;
import com.yourgame.model.Weather.Weather;

public class GameState {
    private Player currentPlayer;

    private List<Player> players;
    private int currentTurnPlayerIndex;
    private TimeSystem gameTime;
    private Weather weather;
    private GameMap currentMap;
    private MapManager mapManager; 
    private List<NPC> npcs;
    private List<Shop> shops;

    public GameState( List<Player> players, Weather weather, MapManager mapManager, List<NPC> npcs, List<Shop> shops) {
        this.players = players;
        this.currentTurnPlayerIndex = 0; // Start with the first player
        this.gameTime = new TimeSystem();
        this.weather = weather;
        this.mapManager = mapManager;
        this.currentMap = mapManager.getMap("farmMap");
        this.npcs = npcs;
        this.shops = shops;
    }

    public GameState() {

    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public void setMapManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void nextTurn() {
        if(currentTurnPlayerIndex == players.size() - 1){
            gameTime.advancedHour(1);
        }
        currentTurnPlayerIndex = (currentTurnPlayerIndex + 1) % players.size();
        setCurrentPlayer(players.get(currentTurnPlayerIndex));
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


    public GameMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(GameMap currentMap) {
        this.currentMap = currentMap;
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
