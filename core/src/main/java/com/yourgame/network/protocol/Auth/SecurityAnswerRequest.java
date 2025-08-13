package com.yourgame.network.protocol.Auth;

public class SecurityAnswerRequest {
    private final String username;
    private final String answer;

    public SecurityAnswerRequest(String username, String answer) {
        this.username = username;
        this.answer = answer;
    }
    
    public String getUsername() { return username; }
    public String getAnswer() { return answer; }
}