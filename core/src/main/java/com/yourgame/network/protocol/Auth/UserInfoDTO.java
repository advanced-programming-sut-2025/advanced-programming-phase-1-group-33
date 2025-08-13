package com.yourgame.network.protocol.Auth;

// A "safe" object to send user data without exposing sensitive info like the password or security answer.
public class UserInfoDTO {
    private final String username;
    private final String password;
    private final String nickname;
    private final String email;
    private final String gender;
    private final String question;
    private final String answer;

    private final String avatarName;

    public UserInfoDTO(String username, String password, String nickname, String email, String gender,
            String avatarName, String question, String answer) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.avatarName = avatarName;
        this.question = question;
        this.answer = answer;
    }

    // Getters for all fields...
    public String getPassword() {
        return password;
    }

    public String getQuestion() {
        return question;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public String getAnswer() {
        return answer;
    }

}
