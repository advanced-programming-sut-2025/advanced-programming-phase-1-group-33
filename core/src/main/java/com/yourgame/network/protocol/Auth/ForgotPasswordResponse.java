package com.yourgame.network.protocol.Auth;


public class ForgotPasswordResponse {
    private final boolean success;
    private final String message; 
    
    public ForgotPasswordResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}