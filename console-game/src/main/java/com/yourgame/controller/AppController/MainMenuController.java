package com.yourgame.controller.AppController;

import com.yourgame.view.AppViews.ExitMenu;
import com.yourgame.view.AppViews.MenuView;

import java.util.Scanner;
import com.yourgame.model.AppModel.App;
import com.yourgame.model.GameState;
import com.yourgame.model.AppModel.enums.MainMenuCommands;
import com.yourgame.model.AppModel.enums.Menu;
import com.yourgame.model.AppModel.Result;

public class MainMenuController {

    public Result handleCommand(String command, Scanner scanner) {
        MainMenuCommands mainCommand = MainMenuCommands.parse(command);
        if (mainCommand == null) {
            return Result.failure("Invalid command!");
        }

        switch (mainCommand) {
            case ENTER_MENU:
                return handleMenuEnter(command);
            case EXIT_MENU:
                return handleExit();
            default:
                return Result.failure("Command not implemented yet.");
        }
    }

    private Result handleExit() {
        App.setCurrentMenu(Menu.ExitMenu);
        return Result.success("");
    }

    // Constructor
    public MainMenuController() {

    }

    // // Constructor
    // public MainMenuController(GameState gameState, MenuView menuView) {
    // this.gameState = gameState;
    // this.menuView = menuView;
    // }

    // Handles the "enter menu" command.
    public Result handleMenuEnter(String menuName) {
        switch (menuName.toLowerCase()) {
            case "login":
                App.setCurrentMenu(Menu.LoginMenuView);
                return Result.success("");
            case "signin":
                App.setCurrentMenu(Menu.RegisterationView);
                return Result.success("");
            case "exit":
                App.setCurrentMenu(Menu.ExitMenu);
                return Result.success("");
            // Add more cases for other menus as necessary
        }
        return Result.success("");
    }

}
