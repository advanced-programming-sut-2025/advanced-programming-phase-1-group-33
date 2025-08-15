package com.yourgame.network.protocol.Auth.Lobby;


import java.util.List;

import com.yourgame.network.lobby.Lobby;

public class RefreshLobbiesResponse {
    private List<Lobby> visibleLobbies;

    public RefreshLobbiesResponse(List<Lobby> visibleLobbies) {
        this.visibleLobbies = visibleLobbies;
    }


    public List<Lobby> getVisibleLobbies() {
        System.out.println("working");
        return visibleLobbies;
    }
}