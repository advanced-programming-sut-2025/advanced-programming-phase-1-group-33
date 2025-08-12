package com.yourgame.network.protocol.Auth;

// Server sends this back to confirm if registration worked.
public class SignupResponse {
    private final boolean success;
    private final String message;

    public SignupResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}