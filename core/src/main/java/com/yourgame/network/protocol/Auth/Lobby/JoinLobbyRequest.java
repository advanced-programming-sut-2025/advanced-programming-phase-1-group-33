package com.yourgame.network.protocol.Auth.Lobby;

public class JoinLobbyRequest {
    private String lobbyId;
    private String password;

    // Constructors, Getters, and Setters
    public JoinLobbyRequest(String lobbyId, String password) {
        this.lobbyId = lobbyId;
        this.password = password;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public String getPassword() {
        return password;
    }
}