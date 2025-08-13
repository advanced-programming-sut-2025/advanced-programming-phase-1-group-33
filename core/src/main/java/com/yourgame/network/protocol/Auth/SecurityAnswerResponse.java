package com.yourgame.network.protocol.Auth;

public class SecurityAnswerResponse {
    private final boolean success;
    private final String message; 

    
    public SecurityAnswerResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}