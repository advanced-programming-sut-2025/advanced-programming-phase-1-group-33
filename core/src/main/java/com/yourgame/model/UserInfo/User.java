package com.yourgame.model.UserInfo;

public class User {
    private String Username;
    private String Password;
    private String Email;
    private String Nickname;
    private String Gender;
    private SecurityQuestion question;
    private String answer;

    public User(String Username, String Password, String Email, String Nickname, String Gender) {
        this.Username = Username;
        this.Password = Password;
        this.Email = Email;
        this.Nickname = Nickname;
        this.Gender = Gender;
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

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public SecurityQuestion getQuestion() {
        return question;
    }

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
