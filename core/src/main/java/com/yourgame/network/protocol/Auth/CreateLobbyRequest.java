package com.yourgame.network.protocol.Auth;

public class CreateLobbyRequest {
    private String name;
    private boolean isPrivate;
    private String password;
    private boolean isVisible;

    public CreateLobbyRequest(String name, boolean isPrivate, String password, boolean isVisible) {
        this.name = name;
        this.isPrivate = isPrivate;
        this.password = password;
        this.isVisible = isVisible;
    }

    // متدهای Getter برای دسترسی به فیلدها
    public String getName() {
        return name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getPassword() {
        return password;
    }

    public boolean isVisible() {
        return isVisible;
    }
}