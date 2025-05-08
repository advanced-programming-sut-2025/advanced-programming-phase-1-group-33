package com.yourgame.controller.AppController;

import com.yourgame.model.App;
import com.yourgame.model.IO.Response;
import com.yourgame.model.enums.Commands.MenuTypes;
import com.yourgame.model.enums.Commands.MainMenuCommands;

public class MainMenuController {

    public Response handleMenuEnter(String command) {
        String[] menuName = MainMenuCommands.ENTER_MENU.getArguments(command);
        switch (menuName[0].toLowerCase()) {
            case "login":
                App.setCurrentMenu(MenuTypes.LoginMenu);
                return new Response(true, "You are now in " + menuName[0]);
            case "register":
                App.setCurrentMenu(MenuTypes.RegisterMenu);
                return new Response(true, "You are now in " + menuName[0]);
            case "profile":
                App.setCurrentMenu(MenuTypes.RegisterMenu);
                return new Response(true, "You are now in " + menuName[0]);
            case "pregame":
                App.setCurrentMenu(MenuTypes.RegisterMenu);
                return new Response(true, "You are now in " + menuName[0]);

            case "exit":
                App.setCurrentMenu(MenuTypes.ExitMenu);
                return handleExit();
            // Add more cases for other menus as necessary
        }
        return new Response(true, "No such menu!");
    }

    public Response handleExit() {
        App.setCurrentMenu(MenuTypes.ExitMenu);
        return new Response(true, "Exiting from the game bye bye!");
    }
}
