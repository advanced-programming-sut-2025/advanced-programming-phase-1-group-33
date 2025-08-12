package com.yourgame.network.protocol.Auth;

// A "safe" object to send user data without exposing sensitive info like the password or security answer.
public class UserInfoDTO {
    private final String username;
    private final String nickname;
    private final String avatarName;

    public UserInfoDTO(String username, String nickname, String avatarName) {
        this.username = username;
        this.nickname = nickname;
        this.avatarName = avatarName;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAvatarName() {
        return avatarName;
    }
}