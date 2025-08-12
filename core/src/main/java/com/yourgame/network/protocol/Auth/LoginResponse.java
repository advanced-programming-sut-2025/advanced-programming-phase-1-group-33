package com.yourgame.network.protocol.Auth;

// The server sends this back to the client after a login attempt
public class LoginResponse {
    private final boolean success;
    private final String message;
    private final UserInfoDTO user; // User data is sent only on success

    public LoginResponse(boolean success, String message, UserInfoDTO user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public UserInfoDTO getUser() {
        return user;
    }
}