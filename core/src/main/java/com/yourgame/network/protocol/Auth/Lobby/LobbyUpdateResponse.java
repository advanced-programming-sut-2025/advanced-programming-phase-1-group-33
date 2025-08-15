package com.yourgame.network.protocol.Auth.Lobby;


import java.util.List;

public class LobbyUpdateResponse {
    private String lobbyId;
    private List<String> players;
    private String adminUsername;
    private String message;

    public LobbyUpdateResponse(String lobbyId, List<String> players, String adminUsername, String message) {
        this.lobbyId = lobbyId;
        this.players = players;
        this.adminUsername = adminUsername;
        this.message = message;
    }

    // Getters
    public String getLobbyId() {
        return lobbyId;
    }

    public List<String> getPlayers() {
        return players;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getMessage() {
        return message;
    }
}