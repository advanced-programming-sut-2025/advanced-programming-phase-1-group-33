package com.yourgame.controller.AppController;


public class LoginController {
    
    public static boolean handleLogin(String username, String password) {
        System.out.println("your logged in");
        return true; 
    }

    public void handleLogout(String cmd) {
        // Logic for handling user logout
    }

    public void handleForgotPassword(String cmd) {
        // Logic for handling forgot password
    }

    public void handlePickQuestion(String cmd) {
        // Logic for handling security question selection
    }

    public void handleAnswerQuestion(String cmd) {
        // Logic for handling answer to security question
    }
}