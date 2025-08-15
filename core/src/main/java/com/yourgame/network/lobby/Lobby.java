package com.yourgame.network.lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lobby {
    private String id;
    private String name;
    private String adminUsername;
    private List<String> players = new ArrayList<>();
    private boolean isPrivate;
    private String password;
    private boolean isVisible;
    private long lastActivityTime;

    /**
     * Constructs a new Lobby instance.
     * * @param name The name of the lobby.
     * @param adminUsername The username of the player who created the lobby.
     * @param isPrivate A flag indicating if the lobby is private.
     * @param password The password for the private lobby (can be null for public lobbies).
     * @param isVisible A flag indicating if the lobby is visible in the public list.
     */
    public Lobby(String name, String adminUsername, boolean isPrivate, String password, boolean isVisible) {
        this.id = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 8);
        this.name = name;
        this.adminUsername = adminUsername;
        this.isPrivate = isPrivate;
        this.password = password;
        this.isVisible = isVisible;
        this.players.add(adminUsername);
        this.lastActivityTime = System.currentTimeMillis();
    }

    /**
     * Adds a player to the lobby.
     * * @param username The username of the player to add.
     * @return true if the player was added, false otherwise (e.g., lobby is full).
     */
    public boolean addPlayer(String username) {
        if (players.size() < 4) {
            players.add(username);
            updateLastActivityTime();
            return true;
        }
        return false;
    }
    
    /**
     * Removes a player from the lobby.
     * * @param username The username of the player to remove.
     */
    public void removePlayer(String username) {
        players.remove(username);
        if (username.equals(adminUsername) && !players.isEmpty()) {
            adminUsername = players.get(0);
        }
    }

    /**
     * Starts the game.
     * * @return true if the game can be started, false otherwise.
     */
    public boolean startGame() {
        return players.size() >= 2;
    }

    /**
     * Checks if a password is correct for a private lobby.
     * * @param password The password to check.
     * @return true if the password is correct, false otherwise.
     */
    public boolean checkPassword(String password) {
        return this.isPrivate && this.password.equals(password);
    }

    /**
     * Checks if the lobby is empty.
     *
     * @return true if the lobby is empty, false otherwise.
     */
    public boolean isEmpty() {
        return players.isEmpty();
    }
    
    /**
     * Checks if the lobby is full.
     * * @return true if the lobby is full, false otherwise.
     */
    public boolean isLobbyFull() {
        return players.size() >= 4;
    }

    /**
     * Updates the last activity time of the lobby.
     */
    public void updateLastActivityTime() {
        this.lastActivityTime = System.currentTimeMillis();
    }
    
    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public List<String> getPlayers() {
        return players;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public long getLastActivityTime() {
        return lastActivityTime;
    }
}