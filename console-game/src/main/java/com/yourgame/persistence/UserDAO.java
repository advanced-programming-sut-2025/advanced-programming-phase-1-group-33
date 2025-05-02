package com.yourgame.persistence;

import com.yourgame.model.AppModel.User;
import com.yourgame.model.enums.Gender;
import java.sql.*;

public class UserDAO {
    private Connection connection;

    public UserDAO(String dbUrl) throws SQLException {
        this.connection = DriverManager.getConnection(dbUrl);
    }

    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users(username, password, email, nickname, gender) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // ensure the password is already hashed
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getNickname());
            stmt.setString(5, user.getGender().toString());
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
                        Gender.valueOf(rs.getString("gender"))
                );
            }
        }
        return null;
    }
}