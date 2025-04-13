package com.yourgame.view.AppViews;

import java.util.Scanner;

import com.yourgame.controller.AppController.LoginController;

public class LoginMenuView implements AppMenu{
        public static void show(Scanner scanner) {
        System.out.println("\n--- LOGIN ---");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();


        boolean result = LoginController.handleLogin(username, password);

        if (result) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("User registration failed. Please check your input.");
        }

    }

    
    @Override
    public void check(Scanner scanner) {
        show(scanner);
        
    }
}
