package com.yourgame.model.App;

import com.yourgame.model.enums.Gender;

public class User {
    private String Username;
    private String Password;
    private String Email;
    private String Nickname;
    private Gender Gender;

    public User(String Username, String Password, String Email, String Nickname, Gender Gender) {
        this.Username = Username;
        this.Password = Password;
        this.Email = Email;
        this.Nickname = Nickname;
        this.Gender = Gender;
    }
    public User(String Username, String Password, String Email, String Nickname) {
        this.Username = Username;
        this.Password = Password;
        this.Email = Email;
        this.Nickname = Nickname;
        this.Gender = Gender.Female;
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
    public Gender getGender() {
        return Gender;
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

}

