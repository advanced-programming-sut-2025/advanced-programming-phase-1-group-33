package com.yourgame.controller.AppController;

import com.yourgame.model.IO.*;
import com.yourgame.model.App;
import com.yourgame.model.enums.Commands.MenuTypes;

public class Controller {
    public static Response handleEnterMenu(Request request) {
        String targetMenu = request.body.get("menuName");

        if (App.getCurrentMenu() == MenuTypes.LoginMenu) {
            return new Response(false, "Sign in first to navigate menus.");
        } else if (App.getCurrentMenu() == MenuTypes.MainMenu) {
            if (targetMenu.compareToIgnoreCase("GameMenu") == 0) {
                App.setCurrentMenu(MenuTypes.PreGameMenu);
                return new Response(true, "Going to game menu...");
            } else if (targetMenu.compareToIgnoreCase("ProfileMenu") == 0) {
                App.setCurrentMenu(MenuTypes.ProfileMenu);
                return new Response(true, "Going to profile menu...");
            } else {
                return new Response(false, "Invalid target menu.");
            }
        } else if (App.getCurrentMenu() == MenuTypes.PreGameMenu) {
            if (targetMenu.compareToIgnoreCase("MainMenu") == 0) {
                App.setCurrentMenu(MenuTypes.MainMenu);
                return new Response(true, "Going to main menu...");
            } else {
                return new Response(false, "Invalid target menu.");
            }
        } else if (App.getCurrentMenu() == MenuTypes.ProfileMenu) {
            if (targetMenu.compareToIgnoreCase("MainMenu") == 0) {
                App.setCurrentMenu(MenuTypes.MainMenu);
                return new Response(true, "Going to main menu...");
            } else {
                return new Response(false, "Invalid target menu.");
            }
        } else {
            return new Response(false, "Invalid Operation.");
        }
    }

    public static Response handleExitMenu(Request request) {
        if (App.getCurrentMenu() == MenuTypes.ProfileMenu) {
            App.setCurrentMenu(MenuTypes.MainMenu);
            return new Response(true, "Exiting to Main Menu...");
        } else if (App.getCurrentMenu() == MenuTypes.MainMenu) {
            App.setCurrentMenu(MenuTypes.ExitMenu);
            return new Response(true, "Exiting app...");
        } else if (App.getCurrentMenu() == MenuTypes.PreGameMenu) {
            App.setCurrentMenu(MenuTypes.MainMenu);
            return new Response(true, "Exiting to Main Menu...");
        } else if (App.getCurrentMenu() == MenuTypes.LoginMenu) {
            App.setCurrentMenu(MenuTypes.ExitMenu);
            return new Response(true, "Exiting app...");
        } else {
            return new Response(false, "Invalid Operation.");
        }

    }

    public static Response handleShowMenu(Request request) {
        return new Response(true, App.getCurrentMenu().toString());
    }
}
