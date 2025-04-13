package com.yourgame.view.AppViews;

import com.yourgame.controller.AppController.RegistrationController;

import java.util.Scanner;
// this is the login and signup menu and is going to be controlled by login and signup
public class RegistrationView implements AppMenu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void show() {
        System.out.println("\n--- REGISTER ---");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        boolean result = RegistrationController.registerUser(username, password, email);

        if (result) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("User registration failed. Please check your input.");
        }


    }
    @Override
    public void check(Scanner scanner) {
        show();
    }
}
