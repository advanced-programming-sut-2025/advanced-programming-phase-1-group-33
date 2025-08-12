package com.yourgame.network.protocol.Auth;

// Sent when a user submits the signup form.
// It contains all the fields needed to create a new user.
public class SignupRequest {
    private final String username;
    private final String password;
    private final String email;
    private final String nickname;
    private final String gender; // Send as String to keep it simple
    private final String securityQuestion;
    private final String securityAnswer;
    private final String avatarName;


    public SignupRequest(String username, String password, String email, String nickname, String gender, String securityQuestion, String securityAnswer, String avatarName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.avatarName = avatarName;
    }

    // Add getters for all fields...
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
    public String getGender() { return gender; }
    public String getSecurityQuestion() { return securityQuestion; }
    public String getSecurityAnswer() { return securityAnswer; }
    public String getAvatarName() { return avatarName; }
}