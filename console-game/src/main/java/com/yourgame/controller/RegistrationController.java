package com.yourgame.controller;

import com.yourgame.model.GameState;
import com.yourgame.model.Player;
import com.yourgame.model.GameState;

public class RegistrationController {
    public static boolean registerUser(String username, String password, String email) {
        if (!validateUsername(username)) {
            System.out.println("Username must be less than or equal to 8 characters.");
            return false;
        }

        if (!validatePassword(password)) {
            System.out.println("Password must be at least 8 characters and contain uppercase, lowercase, digit, and a special character.");
            return false;
        }

        if (!validateEmail(email)) {
            System.out.println("Invalid email format.");
            return false;
        }

//        GameState gameState = new GameState();
//        Player mamad = new Player();
//        gameState.getPlayers().add(mamad);
        return true;
    }

    private static boolean validateUsername(String username) {
        return username.length() <= 8;
    }

    private static boolean validatePassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

    private static boolean validateEmail(String email) {
        return email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}
