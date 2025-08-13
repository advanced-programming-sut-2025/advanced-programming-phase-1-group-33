// server/src/main/java/com/yourgame/persistence/UserDAO.java
package com.yourgame.persistence;

import com.yourgame.model.UserInfo.User;
import com.yourgame.model.enums.Avatar;
import com.yourgame.model.enums.Gender;
import com.yourgame.model.enums.SecurityQuestion;
import com.yourgame.persistence.DatabaseManager;

import java.sql.*;

public class UserDAO {

    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users(username, password, email, nickname, gender, securityQuestion, answer, avatar) VALUES (?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // ensure the password is already hashed
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getNickname());
            stmt.setString(5, user.getGender());
            stmt.setString(6,user.getSecurityQuestion().getQuestion());
            stmt.setString(7,user.getAnswer());
            stmt.setString(8,user.getAvatar().getName());
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
                        Avatar.fromString(rs.getString("avatar"))
                    );
                }
            }
        }
        return null;
    }
    
    public boolean userExists(String username) throws SQLException {
        return loadUser(username) != null;
    }
}