package com.yourgame.network.protocol.Auth;

// A "safe" object to send user data without exposing sensitive info like the password or security answer.
public class UserInfoDTO {
    private final String username;
    private final String nickname;
    private final String email;
    private final String gender; // String is simpler for transfer
    private final String avatarName;

    public UserInfoDTO(String username, String nickname, String email, String gender, String avatarName) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.avatarName = avatarName;
    }

    // Getters for all fields...
    public String getUsername() { return username; }
    public String getNickname() { return nickname; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public String getAvatarName() { return avatarName; }
}
