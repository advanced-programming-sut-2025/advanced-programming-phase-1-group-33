package com.yourgame.view.AppViews;

import java.util.Scanner;

import com.yourgame.controller.AppController.MainMenuController;
import com.yourgame.model.App;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.MainMenuCommands;

public class MainMenu implements AppMenu {

    private final MainMenuController controller = new MainMenuController();

    @Override
    public String getPreview() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------\n");
        sb.append("1. Login\n");
        sb.append("2. Register\n");
        sb.append("3. ProfileMenu\n");
        sb.append("4. PreGameMenu\n");
        sb.append("5. Exit\n");
        sb.append("Usage: enter <menuname>\n");

        sb.append("----------------------------");
        return sb.toString();

    }
    @Override
    public Response handleMenu(String command, Scanner scanner) {
        MainMenuCommands mainCommand = MainMenuCommands.parse(command);
        if (mainCommand == null) {
            return new Response(true, "Invalid command!");
        }

        switch (mainCommand) {
            case ENTER_MENU:
                return controller.handleMenuEnter(command);
            case SHOW_CURRENT_MENU:
                return  new Response(true, ""+ App.getCurrentMenu());
            case EXIT_MENU:
                return controller.handleExit();
            case USER_LOGOUT:
                return MainMenuController.handleLogout();
            default:
                return getInvalidCommand();
                // return new Response(false, "Command not implemented yet.");
        }
    }

    }


