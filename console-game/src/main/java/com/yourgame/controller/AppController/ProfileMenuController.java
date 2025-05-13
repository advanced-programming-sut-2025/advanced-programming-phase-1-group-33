package com.yourgame.controller.AppController;

import java.sql.SQLException;

import com.yourgame.model.IO.*;
import com.yourgame.model.App;
import com.yourgame.model.UserInfo.User;

public class ProfileMenuController {
    public static Response showUserInfo() {
        User currentUser = App.getCurrentUser();
        if (currentUser == null) {
            return new Response(false, "No user logged in!");
        }
        return new Response(true, currentUser.toString());
    }

    public static Response changeUsername(String newUsername) {
        User currentUser = App.getCurrentUser();
        if (currentUser == null) {
            return new Response(false, "No user logged in!");
        }
        if (newUsername == null || newUsername.trim().isEmpty()) {
            return new Response(false, "Invalid username provided!");
        }
        String oldUsername = currentUser.getUsername();
        try {
            // using a dedicated update for username
            App.getUserDAO().updateUsername(oldUsername, newUsername);
            currentUser.setUsername(newUsername);
            return new Response(true, "Username updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(false, "Error updating username.");
        }
    }

    public static Response changeNickname(String newNickname) {
        User currentUser = App.getCurrentUser();
        if (currentUser == null) {
            return new Response(false, "No user logged in!");
        }
        if (newNickname == null || newNickname.trim().isEmpty()) {
            return new Response(false, "Invalid nickname provided!");
        }
        try {
            currentUser.setNickname(newNickname);
            // update the user record using the generic update method
            App.getUserDAO().updateUserById(currentUser.getUsername(), currentUser);
            return new Response(true, "Nickname updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(false, "Error updating nickname.");
        }
    }

    public static Response changeEmail(String newEmail) {
        User currentUser = App.getCurrentUser();
        if (currentUser == null) {
            return new Response(false, "No user logged in!");
        }
        if (newEmail == null || newEmail.trim().isEmpty()) {
            return new Response(false, "Invalid email provided!");
        }
        try {
            currentUser.setEmail(newEmail);
            App.getUserDAO().updateUserById(currentUser.getUsername(), currentUser);
            return new Response(true, "Email updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(false, "Error updating email.");
        }
    }

    public static Response changePassword(String newPassword, String oldPassword) {
        User currentUser = App.getCurrentUser();
        if (currentUser == null) {
            return new Response(false, "No user logged in!");
        }
        if (newPassword == null || newPassword.trim().isEmpty() ||
            oldPassword == null || oldPassword.trim().isEmpty()) {
            return new Response(false, "Invalid password provided!");
        }
        // check if old password matches
        if (!currentUser.getPassword().equals(LoginMenuController.hashPassword(oldPassword))) {
            return new Response(false, "Old password is incorrect!");
        }
        try {
            currentUser.setPassword(LoginMenuController.hashPassword(newPassword));
            App.getUserDAO().updateUserById(currentUser.getUsername(), currentUser);
            return new Response(true, "Password updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(false, "Error updating password.");
        }
    }
}
