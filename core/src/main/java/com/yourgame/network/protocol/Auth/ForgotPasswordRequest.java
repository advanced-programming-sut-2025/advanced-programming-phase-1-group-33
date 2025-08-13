package com.yourgame.network.protocol.Auth;

public class ForgotPasswordRequest {
    private final String username;

    public ForgotPasswordRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}