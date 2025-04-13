package com.yourgame.view.AppViews;

import java.util.Scanner;

import com.yourgame.controller.AppController.ProfileMenuController;

public class ProfileMenuView implements AppMenu {
    ProfileMenuController controller = new ProfileMenuController(); 

    public static void show(Scanner scanner) {
        System.out.println("\n--- ProfileController ---");
        System.out.print("change username");
        System.out.print("change password");
        System.out.print("change nickname");


    }

    @Override
    public void check(Scanner scanner) {
        show(scanner);
        String command = scanner.nextLine().trim();
        controller.handleCommand(command, scanner);

    }
}
