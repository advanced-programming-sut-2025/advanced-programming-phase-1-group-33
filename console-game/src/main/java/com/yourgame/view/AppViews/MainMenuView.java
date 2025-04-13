package com.yourgame.view.AppViews;

import com.yourgame.controller.AppController.MainMenuController;
import java.util.Scanner;
import com.yourgame.model.AppModel.Result;

public class MainMenuView implements AppMenu {

    private final MainMenuController controller = new MainMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Result result = controller.handleCommand(input, scanner);

        // If the result indicates an exit, do not print it.
        if (!result.getMessage().isEmpty()) {
            System.out.println(result);
        }
    }
}
