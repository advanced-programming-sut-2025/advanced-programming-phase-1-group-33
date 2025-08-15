package com.yourgame.network.protocol.Auth.Lobby;

public class SearchLobbyRequest {
    private String lobbyId;

    public SearchLobbyRequest(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public String getLobbyId() {
        return lobbyId;
    }
}