// server/src/main/java/com/yourgame/persistence/UserDAO.java
package com.yourgame.persistence;

import com.yourgame.model.UserInfo.User;
import com.yourgame.model.enums.Avatar;
import com.yourgame.model.enums.Gender;
import com.yourgame.model.enums.SecurityQuestion;
import com.yourgame.persistence.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public void updateUsername(String oldUsername, String newUsername) throws SQLException {
        String sql = "UPDATE users SET username = ? WHERE username = ?";
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
            stmt.executeUpdate();
        }
    }

    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users(username, password, email, nickname, gender, securityQuestion, answer, avatar) VALUES (?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // ensure the password is already hashed
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getNickname());
            stmt.setString(5, user.getGender());
            stmt.setString(6, user.getSecurityQuestion().getQuestion());
            stmt.setString(7, user.getAnswer());
            stmt.setString(8, user.getAvatar().getName());
            stmt.executeUpdate();
        }
    }

    public User loadUser(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getString("nickname"),
                            Gender.valueOf(rs.getString("gender")),
                            SecurityQuestion.getQuestion(rs.getString("securityQuestion")),
                            rs.getString("answer"),
                            Avatar.fromString(rs.getString("avatar")));
                }
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = DatabaseManager.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        Gender.valueOf(rs.getString("gender")),
                        SecurityQuestion.valueOf(rs.getString("security question")),
                        rs.getString("answer"),
                        Avatar.fromString(rs.getString("avatar"))));
            }
        }
        return users;
    }

    public void updateUserById(String username, User updatedUser) throws SQLException {
        String sql = """
                    UPDATE users
                    SET password = ?, email = ?, nickname = ?, gender = ?, securityQuestion = ?, answer = ?, avatar = ?
                    WHERE username = ?
                """;
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, updatedUser.getPassword());
            stmt.setString(2, updatedUser.getEmail());
            stmt.setString(3, updatedUser.getNickname());
            stmt.setString(4, updatedUser.getGender());
            stmt.setString(5, updatedUser.getSecurityQuestion().getQuestion());
            stmt.setString(6, updatedUser.getAnswer());
            stmt.setString(7, updatedUser.getAvatar().getName());
            stmt.setString(8, username);
            stmt.executeUpdate();
        }
    }

    public boolean userExists(String username) throws SQLException {
        return loadUser(username) != null;
    }
}