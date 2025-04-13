package com.yourgame.view.AppViews;

import com.yourgame.controller.AppController.MainMenuController;
import com.yourgame.controller.AppController.RegistrationController;

import java.util.Scanner;
import com.yourgame.model.AppModel.Result;

public class MainMenuView implements AppMenu {

    private final MainMenuController controller = new MainMenuController();

    public static void show() {
        System.out.println("\n--- Select in Between these MENUS---");

        System.out.println("1. Regist");
        System.out.println("2. Login");
        System.out.println("3. Exit");

    }


    @Override
    public void check(Scanner scanner) {
        show();
        String input = scanner.nextLine().trim();
        Result result = controller.handleCommand(input, scanner);

        // If the result indicates an exit, do not print it.
        if (!result.getMessage().isEmpty()) {
            System.out.println(result);
        }
    }
}
