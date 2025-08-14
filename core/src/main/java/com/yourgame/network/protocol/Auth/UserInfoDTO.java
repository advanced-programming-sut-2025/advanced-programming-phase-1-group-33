package com.yourgame.network.protocol.Auth;

import com.yourgame.model.UserInfo.User;
import com.yourgame.model.enums.Avatar;

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

    public UserInfoDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.avatarName = user.getAvatar().getName();
        this.question = user.getSecurityQuestion().getQuestion();
        this.answer = user.getAnswer();
    }

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

    public Avatar getAvatarENUM() {
        return Avatar.fromString(this.avatarName);
    }

    public String getAnswer() {
        return answer;
    }

}
