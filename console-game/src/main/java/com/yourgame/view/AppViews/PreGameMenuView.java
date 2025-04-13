package com.yourgame.view.AppViews;

import java.util.Scanner;

import com.yourgame.controller.AppController.PreGameController;

public class PreGameMenuView implements AppMenu {
    PreGameController controller = new PreGameController(); 

    public static void show(Scanner scanner) {
        System.out.println("\n--- PreGameMenu ---");
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
