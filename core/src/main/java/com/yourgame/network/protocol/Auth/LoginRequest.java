package com.yourgame.network.protocol.Auth;

// The client sends this to the server when the user clicks "Login"
public class LoginRequest {
    private final String username;
    private final String password; // Plain text password

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}