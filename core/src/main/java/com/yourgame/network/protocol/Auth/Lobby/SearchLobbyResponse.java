package com.yourgame.network.protocol.Auth.Lobby;

import com.yourgame.network.lobby.Lobby;

public class SearchLobbyResponse {
    private Lobby foundLobby;
    private String message;

    public SearchLobbyResponse(Lobby foundLobby, String message) {
        this.foundLobby = foundLobby;
        this.message = message;
    }

    // Getters
    public Lobby getFoundLobby() {
        return foundLobby;
    }

    public String getMessage() {
        return message;
    }
}
