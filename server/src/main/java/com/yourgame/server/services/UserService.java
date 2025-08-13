
// server/src/main/java/com/yourgame/server/services/UserService.java
package com.yourgame.server.services;

import com.yourgame.model.UserInfo.User;
import com.yourgame.persistence.UserDAO;
import java.sql.SQLException;

public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }


    public String registerUser(User user) {
        try {
            if (userDAO.userExists(user.getUsername())) {
                return "Error: Username already exists.";
            }

            userDAO.saveUser(user);
            return "Success: User registered successfully!";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: A database error occurred.";
        }
    }


    public User loginUser(String username, String plainTextPassword) {
        try {
            User user = userDAO.loadUser(username);

            if (user != null && plainTextPassword.equals(user.getPassword())) {
                return user; // Login successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    public User findUserForPasswordRecovery(String username) {
        try {
            return userDAO.loadUser(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean verifySecurityAnswer(String username, String answer) {
        try {
            User user = userDAO.loadUser(username);

            if (user != null) {
                return user.getAnswer().equals(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}