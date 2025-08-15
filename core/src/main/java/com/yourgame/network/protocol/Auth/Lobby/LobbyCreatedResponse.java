package com.yourgame.network.protocol.Auth.Lobby;

public class LobbyCreatedResponse {
    private final String lobbyId;

    public LobbyCreatedResponse(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public String getlobbyId() {
        return lobbyId;
    }
}
