package com.yourgame.model.UserInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yourgame.model.enums.Gender;
import com.yourgame.model.enums.SecurityQuestion;

public class User {
    private String Username;
    private String Password;
    private String Email;
    private String Nickname;
    private Gender Gender;

    @JsonProperty("securityQuestion")
    private SecurityQuestion question;

    private String answer;

    public User(){}

    public User(String Username, String Password, String Email, String Nickname, Gender Gender, SecurityQuestion question, String answer) {
        this.Username = Username;
        this.Password = Password;
        this.Email = Email;
        this.Nickname = Nickname;
        this.Gender = Gender;
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "User{" +
                "Username='" + Username + '\'' +
                ", Email='" + Email + '\'' +
                ", Nickname='" + Nickname + '\'' +
                ", Gender='" + Gender + '\'' +
                (question != null ? ", Question='" + question + '\'' : "") +
                (answer != null ? ", Answer='" + answer + '\'' : "") +
                '}';
    }

    @JsonProperty("securityQuestion")
    public SecurityQuestion getSecurityQuestion() {
        return question;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getEmail() {
        return Email;
    }

    public String getNickname() {
        return Nickname;
    }

    public String getGender() {
        return Gender.getGenderString();
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setNickname(String nickname) {
        this.Nickname = nickname;
    }

    public void setGender(Gender gender) {
        this.Gender = gender;
    }

    @JsonProperty("securityQuestion")
    public void setQuestion(SecurityQuestion question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
