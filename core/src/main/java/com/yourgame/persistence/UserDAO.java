package com.yourgame.persistence;

import com.yourgame.model.UserInfo.User;
import com.yourgame.model.enums.Gender;
import com.yourgame.model.enums.SecurityQuestion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
    private Connection connection;

    public UserDAO(String dbUrl) throws SQLException {
        this.connection = DriverManager.getConnection(dbUrl);
        initializeDatabase();
    }

    public void initializeDatabase() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                username TEXT PRIMARY KEY,
                password TEXT NOT NULL,
                email TEXT NOT NULL,
                nickname TEXT NOT NULL,
                gender TEXT NOT NULL,
                securityQuestion TEXT NOT NULL,
                answer TEXT NOT NULL
            )
        """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void updateUsername(String oldUsername, String newUsername) throws SQLException {
        String sql = "UPDATE users SET username = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
            stmt.executeUpdate();
        }
    }

    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users(username, password, email, nickname, gender, securityQuestion, answer) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // ensure the password is already hashed
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getNickname());
            stmt.setString(5, user.getGender());
            stmt.setString(6,user.getSecurityQuestion().getQuestion());
            stmt.setString(7,user.getAnswer());
            stmt.executeUpdate();
        }
    }

    public User loadUser(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        Gender.valueOf(rs.getString("gender")),
                        SecurityQuestion.getQuestion(rs.getString("securityQuestion")),
                        rs.getString("answer"));
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        Gender.valueOf(rs.getString("gender")),
                        SecurityQuestion.valueOf(rs.getString("security question")),
                        rs.getString("answer")));
            }
        }
        return users;
    }

    public User findUserById(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        Gender.valueOf(rs.getString("gender")),
                        SecurityQuestion.valueOf(rs.getString("security question")),
                        rs.getString("answer"));
            }
        }
        return null;
    }

    public void updateUserById(String username, User updatedUser) throws SQLException {
        String sql = "UPDATE users SET password = ?, email = ?, nickname = ?, gender = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedUser.getPassword());
            stmt.setString(2, updatedUser.getEmail());
            stmt.setString(3, updatedUser.getNickname());
            stmt.setString(4, updatedUser.getGender());
            stmt.setString(5, username);
            stmt.executeUpdate();
        }
    }
}
