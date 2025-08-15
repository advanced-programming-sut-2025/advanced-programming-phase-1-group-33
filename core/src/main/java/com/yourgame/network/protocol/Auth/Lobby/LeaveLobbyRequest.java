package com.yourgame.network.protocol.Auth.Lobby;

public class LeaveLobbyRequest {
    private String lobbyId;

    // Constructors, Getters, and Setters
    public LeaveLobbyRequest(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public String getLobbyId() {
        return lobbyId;
    }

}
