package com.yourgame.view;

import com.yourgame.controller.RegistrationController;

import java.util.Scanner;

public class RegistrationView extends AppMenu{
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

    public void check(Scanner scanner) {
        show();
    }
}
